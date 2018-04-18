from __future__ import division
import time
from math import log10
import Adafruit_GPIO.SPI as SPI
import Adafruit_MCP3008

# Software SPI configuration:
mcp = Adafruit_MCP3008.MCP3008(clk=18, cs=25, miso=23, mosi=24)

tiem = 0
val = 0
while True:
	value = mcp.read_adc(1)
	if value <= 0:
		value = 0.000001

	if (value > val):
		val = value
	
	tiem += 1
	if (tiem > 400):
#		print(val)
		#val = 20 * log10(val/116)
		valdb = 20.0 * log10(val/112)+60
		val = valdb+6
		tiem = 0
		print (val)
		val = 0
	print (val)
	time.sleep(0.0001)
