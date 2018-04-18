from __future__ import division
from RF24 import *
from firebase import firebase
from math import log10
import glob, os, Adafruit_DHT, smbus
import RPi.GPIO as GPIO
import time
import datetime
import csv, timeit
import shutil
import multiprocessing

#soundSensor
import Adafruit_GPIO.SPI as SPI
import Adafruit_MCP3008

radio = RF24(RPI_BPLUS_GPIO_J8_15, RPI_BPLUS_GPIO_J8_24, BCM2835_SPI_SPEED_8MHZ)
firebase = firebase.FirebaseApplication('https://sleep-quality-monitor.firebaseio.com', None)

inFile = '19-03-2018-sensor.csv'
outFile = '19-03-2018'

#mainLoop
sleepCount = 0
pvm = 0
sleepCounterPVM = 0
vanhapvm = 0 
oldTime = 0 #dataSavePeriod
oldTime2 = 0 #dataMeasurePeriod
oldTime3 = 0 #soundSensorPeriod
currentTime = 0

#import simpletest2

aikavali = 2

#noiseSensor
noise = 0
mcp = Adafruit_MCP3008.MCP3008(clk=18, cs=25, miso=23, mosi=24)
tiem = 0
maxNoise = 0

#sensorMeasure
pin = 4
bus = smbus.SMBus(1)
temp_array = []
humi_array = []
lumi_array = []
timestamp = 0

movement_array = []
heart_array = []

# NRF code included in this script, so we can get data to arrays

pipes = [0xF0F0F0F0E1, 0xF0F0F0F0D2]
min_payload_size = 4
max_payload_size = 32
millis = lambda: int(round(time.time() * 1000))

radio.begin()
radio.enableDynamicPayloads()
radio.setRetries(5,15)
radio.printDetails()

radio.openWritingPipe(pipes[1])
radio.openReadingPipe(1,pipes[0])
radio.startListening()

mainLoopMode = 0 # 1

def try_read_data():
	
	if radio.available():
		while radio.available():
			len = radio.getDynamicPayloadSize()
			receive_payload = radio.read(len)
			radio.stopListening() # First, stop listening so we can talk
			radio.write(receive_payload) # Send the final one back.
			radio.startListening() # Now, resume listening so we catch the next packets.
			return receive_payload

def sensorMeasure(): #GATEWAY SENSOR MEASURE

	timestamp = datetime.datetime.now().strftime("%H:%M")
	humidity, temperature = Adafruit_DHT.read_retry(Adafruit_DHT.AM2302, 4)
	
	bus.write_byte_data(0x39, 0x00 | 0x80, 0x03)
	bus.write_byte_data(0x39, 0x01 | 0x80, 0x02)

	#time.sleep(0.5)
	data = bus.read_i2c_block_data(0x39, 0x0C | 0x80, 2)
	data1 = bus.read_i2c_block_data(0x39, 0x0E | 0x80, 2)

	luminosity = data[1] *256 + data[0]
	ch1 = data[1] *256 + data1[0]	#ch1 is infrared light only
		
	temp_array.append(temperature)
	humi_array.append(humidity)
	lumi_array.append(luminosity)
	
def soundSensor(asd):

	global noise
	global tiem
	
	while True:
		time.sleep(0.01)
		value = mcp.read_adc(1)
		if value <= 0:
			value = 0.000001

		if (value > noise):
			noise = value

		tiem += 1
		if (tiem > 50):
			valdb = 20.0 * log10(noise/112)+60
			noise = valdb+6
			tiem = 0
			if (noise > asd.value):
				asd.value = noise
			noise = 0

def sensorSave():
	global timestamp2
	global aikavali
	global noise
		
	temp_array.sort()
	center = len(temp_array)/2
	humi_array.sort()
	center_humi = len(humi_array)/2
	lumi_array.sort()
	center_lumi = len(lumi_array)/2
	
	if len(temp_array) % 2 == 0:
		median_temperature = temp_array[int(center)]
	else:
		median_temperature = (temp_array[int(center-0.5)] + temp_array[int(center-0.5)])/2.0
		
	if len(humi_array) % 2 == 0:
		median_humidity = humi_array[int(center_humi)]
	else:
		median_humidity = (humi_array[int(center_humi-1)] + humi_array[int(center_humi)])/2.0
	
	if len(lumi_array) % 2 == 0:
		median_luminosity = lumi_array[int(center_lumi)]
	else:
		median_luminosity = (lumi_array[int(center_lumi-1)] + lumi_array[int(center_lumi)])/2.0
			
	with open(str(outFile), 'a') as file:
		timestamp2 = pvm + datetime.timedelta(minutes = aikavali)
		file.write(str(timestamp2.strftime("%H:%M")) + "," + "%.1f" % (median_temperature) + "," + "%.1f" % (median_humidity) + "," + "%.0f" % (median_luminosity) + "," + "%.1f" % (asd.value) + "\n")
		print("MAX NOISE: %1.0f") % (asd.value) + "dB"
		asd.value = 0
		aikavali += 2
		print(timestamp2.strftime("%H:%M"))

#ADDS VALUES FROM ARDUINO TO SAME FILE (heartrate + movement)
def file_append():
		read = open(outFile, 'r')
		write = open("temp", 'w')
		
		reader = csv.reader(read)
		writer = csv.writer(write, dialect='excel')
		
		i = 0
		for row in reader:
			#print("II ON: %f") % (i)
			row.append(movement_array[i])#wearableSensors[i][0])
			row.append(heart_array[i])#wearableSensors[i][1])
			writer.writerow(row)
			i += 1
			if i >= len(temp_array) or i >= len(movement_array):
				break;
		read.close()
		write.close()
		shutil.copyfile("temp", outFile)

def post():
	
	timestamp = datetime.datetime.now().strftime("%d-%m-20%y") ###pois
	#SEND DATA TO CURRENT DATE ?DIRECTORY?
	
	with open(outFile, 'rb') as f:
		reeder = csv.reader(f)
		headerTime = list(reeder) #outFile to list, so we can get the first timestamp to header with date	
	
	csvFile = csv.reader(open(outFile, "rb"))

	for row in csvFile:
		#row[0] = Timestamp
		#row[1] = Noise
		#row[2] = Humidity
		#row[3] = Temperature
		#row[4] = Luminosity
		#row[5] = Movement
		#row[6] = Heartbeat
		#headerTime[]0[0] = first timestamp for the header (time of the day you start to sleep)
		
		result = firebase.put('/Data/-L7tslQrXCXZFZZVfBMM/'+ timestamp + "_" + headerTime[0][0] + ' ', row[0],{'Temperature': float(row[1]), 'Humidity':float(row[2]), 'Luminosity':float(row[3]), 'Noise': float(row[4]), 'Movement': float(row[5]), 'Heartbeat': float(row[6])})

#mainLoopMode = 0, Nothing happens, person is not sleeping
#mainLoopMode = 1, Person starts to sleep, sensors activate.
#mainLoopMode = 2, Person wakes up, data is being transferred.
#mainLoopMode = 3, Data has been transferred, begin sending data to database.

while True:
	try:
		data = try_read_data()#always on, listens what happens at arduino.
		if (data !=  None ):
		# None: # When something is send from arduino.
			if (data.find('aloita') !=-1):
				soundi.terminate()
				print("")
				print("alodedaan")
				ii = 0
				mainLoopMode = 2
			elif (data.find('lopeta') != -1):
				print("lopetetaan")
				file_append() # start meanwhile data from arduino is gathered.

				mainLoopMode = 3
			elif (data.find('viestimiddaile') != -1):
				pvmfile = datetime.datetime.now().strftime("%y%m%d") 
				oldTime = time.time()
				asd = multiprocessing.Value('d', 0)				
				soundi = multiprocessing.Process(target=soundSensor, args = (asd,))
				soundi.start()

				pvm = datetime.datetime.now() #adds 2minutes to timestamp
				sleeCounterPVM = datetime.datetime.now().strftime("%y%m%d") 
				sleepCount = 0
				for file in glob.glob(pvmfile + '*'):
					val = int(file.split('-')[-1])
					if val > sleepCount:
						sleepCount = val
				sleepCount += 1
				outFile = pvmfile+'-'+str(sleepCount) #outFile where sensordata is
				mainLoopMode = 1
		
			elif (mainLoopMode == 2):
					a = data
					a = a.split(",")
					M = float(a[0]) / 100.0
					H = a[1]
					print(M)
					print(H)
					movement_array.append(M)#] = M #wearableSensors[ii][0] = M
					heart_array.append(H) #[ii] = H #wearableSensors[ii][1] = H
					ii += 1
			if (mainLoopMode == 3):
				if(vanhapvm != sleepCounterPVM): #new day, new ID-value
					print("New day, zero sleepCounter")
					post()
					vanhapvm = sleepCounterPVM
					mainLoopMode = 0
				else:	
					print("Same day, new measure")
					post() # Posts data to firebase
					print("=========Everything has been sent=========")
					mainLoopMode = 0

		if (mainLoopMode == 1):
			currentTime = time.time()
			dataSavePeriod = currentTime - oldTime
			dataMeasurePeriod = currentTime - oldTime2

			if(dataSavePeriod >= 1): #Saving 2min values to file
				oldTime = currentTime
				print("")
				print("erotus: %f" %dataSavePeriod)
				print("Analyzing and saving data..")				
				sensorSave()
				
			if(dataMeasurePeriod >= 0.25): #How often is data measured
				oldTime2 = currentTime
				sensorMeasure() # Measure room-data
								
	except KeyboardInterrupt:
		break
