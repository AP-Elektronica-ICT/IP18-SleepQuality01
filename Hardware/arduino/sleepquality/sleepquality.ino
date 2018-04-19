#include <Wire.h>     // I2C library
//#include <printf.h> // NRF24, used for printing the registers and pipe addresses and such
#include <SPI.h>      // needed for NRF24

// http://tmrh20.github.io/RF24/
#include "nRF24L01.h"
#include "RF24.h"

// https://github.com/emilv/ArduinoSort
#include <ArduinoSort.h>

// Power saving stuff
#include <avr/sleep.h>
#include <avr/wdt.h>
#include <avr/power.h>

#define LED_PIN 6                 // Led pin is used for led that informs of good/bad heart beat and when data is being sent to gateway and when device is shut
#define PULSE_PIN A0              // Pulse Sensor purple wire connected to analog pin 0

#define ADXL_ADDR 0x53 // ADXL345 I2C address

#define TIMESTAMP_INTERVAL 2000 // Interval of timestamps in ms 2min = 120 000 ms
#define ADXL_INTERVAL 250       // How often acceleration is measured in ms 250
#define HEART_INTERVAL 1        // How often heart rate is measured in ms 1

uint8_t movementWithin10Sec = 0; // How much movement there has been in a while

// Informing led
bool ledState = 0;      // Current state of led ON/OFF
uint8_t ledMode = 0;    // Current mode for led blinking/constant light/ OFF
int blinkInterval = 0;  // How fast the led blinks

// Bpm
uint8_t BPM = 79;          // Holds raw Analog in 0. updated every 2mS
int Signal;                // Holds the incoming raw data
int IBI = 600;             // Time interval between beats
boolean Pulse = false;     // If heart beat is detected or not
boolean QS = false;        // Was heart beat found

int maxSignal = 0;

// Millis stuff // For measuring the intervals
unsigned long ADXLLastMillis = 0; 
unsigned long HeartLastMillis = 0;
unsigned long timestampLastMillis = 0;
unsigned long HeartAvgLastMillis = 0;
unsigned long blinkLastMillis = 0;

uint8_t movements[360];     // Each cell in array holds the amount of movement for specific timestamp
uint8_t heartMedian[360];   // Holds heart median. Large arrays, as it needs to hold data for the whole night, cant be int as int takes too much space
uint8_t mainLoopMode = 0;   // If we are in power down mode(0), sensor measuring mode(1) or transmitting data to gateway(2)
uint32_t timestampCount = 0; // Current timestamp count

uint8_t wakeUpCounter = 0;  // Detects if there has been no pulse for a moment, then the user might have woken up

uint8_t iHeart = 0;
uint8_t avgBpm[130];
uint8_t bpm;

RF24 radio(7, 8); // Set up nRF24L01 radio on SPI bus plus pins 7 & 8

void resetMovement() {
  // Deletes all values from movement array
  for (int i = 0; i < sizeof(movements); i++)
  {
    movements[i] = 0;
  }
}

void setup() {
  heartMedian[0] = 79;
  
  Wire.begin();
  Serial.begin(9600);
  
  ADXLSetup();                // Initializes the ADXL acceleration sensor with suitable register values

  interruptSetup();           // sets up to read Pulse Sensor signal every 2mS

  pinMode(4, INPUT); //
  pinMode(5, INPUT);

  NRFInit();                  // Initializes the nRF24 with same register values as in gateway's nRF24

  // Power saving stuff
  // All unused pins will be set as output and LOW state
  for (byte i = 0; i <= A3; i++) {
    if ((i >= 9 && i <= 13) || i == PULSE_PIN) // skip radio pins
      continue;
    pinMode (i, OUTPUT);
    digitalWrite (i, LOW);
  }

  power_timer1_disable(); // Unused timer1 will be disabled to save power
  radioPowerDown();       // Shuts the nRF24 when it's not being used
  
  pinMode(LED_PIN, OUTPUT);
  resetMovement();
  startMeasuring();       // Sleep has started, starting to measure movement 
}

void startMeasuring() {
  // Sleep has started, starting to measure movement 
  radioPowerUp();       // Powers up nRF24 for a moment, to tell gateway to start measuring room conditions
  char send_payload[32];
  sprintf(send_payload, "%s", "viestimiddaile"); // Create a message to tell gateway  to start measuring conditions
  NRFSend(send_payload);            // Send the message to gateway
  radioPowerDown();                 // Shuts the nRF24 when it's not being used
  timestampLastMillis = millis();   // For collecting data every 2 minutes
  ADXLLastMillis = millis();        // for collecting ADXL data every now and then
  mainLoopMode = 1;                 // Start looping measurements
}

void serial() {
  // Serial input stuff for debugging
  if ( Serial.available() )
  {
    char c = toupper(Serial.read());

    if ( c == 'A' ) {          // Force sleep to stop
      mainLoopMode = 2;
    }
    if ( c == 'S' ) {          // both devices start measuring with sensors
      startMeasuring();       
    }
    if ( c == 'E' ) {          // erase
      timestampCount = 0;
      Serial.println("Life has been erased");

    }
    if ( c == 'D' ) {          // power down
      Serial.println("Power down");
      power_twi_disable();
      //ADCSRA = 0;  // disable ADC
      //power_all_disable();

    }
    if ( c == 'F' ) {          // RADIO power down
      radioPowerDown();
    }

    if ( c == 'U' ) {          // power up
      Serial.println("Power up");
      power_twi_enable();
    }

    if ( c == 'I' ) {          // RADIO power up
      radioPowerUp();
    }
  }
}

void radioPowerUp() {
  // Powers up nRF24 radio
  Serial.println("Radio Power up");
  NRFReInit();  // To make sure all is fine, nRF24 registers will re initialized

  radio.powerUp();
}

void radioPowerDown() {
  // Cuts power from radio to preserve power
  radio.powerDown();
  for (byte i = 9; i <= 13; i++) // set nRf24 pins to OUTPUT and LOW
  {
    pinMode (i, OUTPUT);
    digitalWrite (i, LOW);
  }
  Serial.println("Radio Power down");

  radio.flush_tx(); // Might not be needed
  radio.flush_rx();
}

void loop() {
  serial();  // For sending commands via serial

  // mainLoopMode == 0 for sleep mode or smth
  if (mainLoopMode == 1) { 
    // Sensor measuring mode
    if (millis() > (ADXLLastMillis + ADXL_INTERVAL)) // 0.25 sec or so interval for ADXL
    {
      ADXLLastMillis = millis();
      ADXLRead();               // See if there is movement
    }

    if (millis() > (HeartAvgLastMillis + 1000)) // 0.5 sec or so interval for Heart rate
    {
      HeartAvgLastMillis = millis();
      
      if (BPM > 120) { // Heart rate is bad
        
        Serial.print(BPM); Serial.println("Heart rate is bad, skipping value");
        if (BPM > 200) {wakeUpCounter++;} else {wakeUpCounter = 0;} // If heart rate is too high, sleep might have been stopped...
        ledMode = 2; // Led blinks slowly when heart rate is bad
      }
      else if (BPM < 30) { // Heart rate is bad
        Serial.print(BPM); Serial.println("Heart rate is bad, skipping value");
        wakeUpCounter++;
        ledMode = 2;
        BPM = 30;
      }
      else { // Heart rate is good
        wakeUpCounter = 0;
        ledMode = 1;
        avgBpm[iHeart] = BPM; //    add heart rate to array that will be used for counting heart rate for current time stamp
        Serial.print("iHeart: ");
        Serial.print(avgBpm[iHeart]);
        Serial.print(" ");
        Serial.println(iHeart);
        iHeart++;             // counter for good heart rate 
      }
      
      if ((wakeUpCounter > 11) && (movementWithin10Sec < 4)) { 
        // no heart rate and no movement for a while
        // sleeping has most likely ended, starting to send data to gateway
        mainLoopMode = 2;
        Serial.println("Wake up");
      }
    }

    if (millis() > (timestampLastMillis + TIMESTAMP_INTERVAL)) { // 2 min or so interval for database saving
      maxSignal = 0;

      timestampLastMillis = millis();
      Serial.print("iHeart = "); Serial.println(iHeart);
      sortArray(avgBpm, iHeart);     //Sort values in array with quicksort
      Serial.println("array sorted");
      
      for (int iii = 0; iii < iHeart; iii++)
      { Serial.print("sortattu: ");
        Serial.println(avgBpm[iii]);
      }

      if (iHeart % 2 == 1)
      {
        heartMedian[timestampCount] = avgBpm[round(iHeart / 2)];
      }
      else
      {
        heartMedian[timestampCount] = avgBpm[iHeart / 2];
      }

      iHeart = 0;
      printDetails();
      timestampCount++;
    }
  }

  if (mainLoopMode == 2) {  // send data to gateway
    ledMode = 3;
    radioPowerUp();         // powers up nrf24 
    char send_payload[32];  // nrf24 payload
    sprintf(send_payload, "%s", "aloita"); // inform gateway about starting to send data
    NRFSend(send_payload);
    delay(10);

    for (int i = 0; i < timestampCount; i++) { // send all the data, will be looped through all the timestamps
      analogWrite(LED_PIN, 150);
      sprintf(send_payload, "%d,%d", (int)((movements[i] * 100) / (1000 / float(ADXL_INTERVAL))), heartMedian[i]);
      Serial.print(" Lahetetty: ");
      Serial.print(movements[i]); Serial.print("  ");
      Serial.println( (int)(movements[i] * 100) / (1000 / float(ADXL_INTERVAL)));
      NRFSend(send_payload);
      digitalWrite(LED_PIN, LOW);
      delay(10);
    }

    sprintf(send_payload, "%s", "lopeta");
    NRFSend(send_payload);
    delay(10);
    timestampCount = 0; 
    mainLoopMode = 0;   // Go to sleep mode
    resetMovement();    
    radioPowerDown();   // Cut power from nRF24 radio
    ledMode = 0;        // Led off
  }

  if ((ledMode == 2) || (ledMode == 3) || (ledMode == 1)) {
    if (millis() > (blinkLastMillis + blinkInterval)) { // 2 min or so interval for database
      blinkLastMillis = millis();
      switch (ledMode) {
        case 0: digitalWrite(LED_PIN, LOW); break;
        case 1: analogWrite(LED_PIN, 100); break; // Heart rate is good
        case 2: if (ledState == 0) {analogWrite(LED_PIN, 100); blinkInterval = 500; ledState = !ledState;} // Heart rate is bad
                else {digitalWrite(LED_PIN, LOW); ledState = !ledState;}
                break;
        case 3: if (ledState == 0) {analogWrite(LED_PIN, 100); blinkInterval = 200; ledState = !ledState;} // Sleep has stopped, data will be sent to gateway
                else {digitalWrite(LED_PIN, LOW); ledState = !ledState;}
                break;
        default: digitalWrite(LED_PIN, LOW); break;
      }
    }
  }
}

void printDetails()
{
  // Print some details of sensor values and timestamp
  Serial.print("\nTimestamp: ");
  Serial.print(timestampCount);
  Serial.print("  Movements: ");
  Serial.print((movements[timestampCount] / (1000 / float(ADXL_INTERVAL))));
  Serial.print("Heart rate: ");
  Serial.println(heartMedian[timestampCount]);
}
