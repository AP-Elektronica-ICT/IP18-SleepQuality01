int rate[10];                    // array to hold last ten IBI values
unsigned long sampleCounter = 0;          // used to determine pulse timing
unsigned long lastBeatTime = 0;           // used to find IBI
int P =512;                      // used to find peak in pulse wave, seeded
int T = 512;                     // used to find trough in pulse wave, seeded
int thresh = 530;                // used to find instant moment of heart beat, seeded
int amp = 0;                   // used to hold amplitude of pulse waveform, seeded
boolean firstBeat = true;        // used to seed rate array so we startup with reasonable BPM
boolean secondBeat = false;      // used to seed rate array so we startup with reasonable BPM


void interruptSetup(){  
  // Timer 2 interrupt for heart sensor, every 2ms
  TCCR2A = 0x02;     // Disable PWM on digitial 3 and 11 pins
  TCCR2B = 0x06;     // No compare, 256 prescaler
  OCR2A = 0X3E;      // Set count to 62 for 500Hz sample rate with the 8MHz arduino
  TIMSK2 = 0x02;     // Interrupt on match between timer2 andOCR2A
  sei();             // Enable interrupts
}

ISR(TIMER2_COMPA_vect){                       
  // Triggered when Timer2 counts to 62, so every 2ms
  // Calculates the heartrate of person, or atleast tries to
  cli();                                      // Disable interrupts
  Signal = analogRead(PULSE_PIN);             // Read the Pulse Sensor
  sampleCounter += 2;                         // Track time
  int N = sampleCounter - lastBeatTime;       // Monitor the time since the last beat to avoid noise
  
  // Find the peak and trough of the pulse wave
  if(Signal < thresh && N > (IBI/5)*3){       // avoid dichrotic noise by waiting 3/5 of last IBI
    if (Signal < T){                          // T is the trough
      T = Signal;                             // keep track of lowest point in pulse wave
    }
  }

  if(Signal > thresh && Signal > P){          // thresh condition helps avoid noise
    P = Signal;                               // P is the peak
  }                                           // keep track of highest point in pulse wave


  if (N > 250){                                   // Avoid high frequency noise
    if ( (Signal > thresh) && (Pulse == false) && (N > (IBI/5)*3) ){
      Pulse = true;                               // Set the Pulse flag when HOPEFULLY there is a pulse
      IBI = sampleCounter - lastBeatTime;         // Measure time between beats in mS
      lastBeatTime = sampleCounter;               // Keep track of time for next pulse

      if(secondBeat){                        // if this is the second beat, if secondBeat == TRUE
        secondBeat = false;                  // clear secondBeat flag
        for(int i=0; i<=9; i++){             // seed the running total to get a realisitic BPM at startup
          rate[i] = IBI;
        }
      }

      if(firstBeat){                         // if it's the first time we found a beat, if firstBeat == TRUE
        firstBeat = false;                   // clear firstBeat flag
        secondBeat = true;                   // set the second beat flag
        sei();                               // Enable interrupts 
        return;                              // IBI value is unreliable so discard it
      }


      // keep a running total of the last 10 IBI values
      word runningTotal = 0;                  // clear the runningTotal variable

      for(int i=0; i<=8; i++){                // shift data in the rate array
        rate[i] = rate[i+1];                  // and drop the oldest IBI value
        runningTotal += rate[i];              // add up the 9 oldest IBI values
      }

      rate[9] = IBI;                          // add the latest IBI to the rate array
      runningTotal += rate[9];                // add the latest IBI to runningTotal
      runningTotal /= 10;                     // average the last 10 IBI values
      BPM = 60000/runningTotal;               // how many beats can fit into a minute? that's BPM!
      QS = true;                              // set Quantified Self flag
    }
  }

  if (Signal < thresh && Pulse == true){   // when the values are going down, the beat is over
    Pulse = false;                         // reset the Pulse flag so we can do it again
    amp = P - T;                           // get amplitude of the pulse wave
    thresh = amp/2 + T;                    // set thresh at 50% of the amplitude
    P = thresh;                            // reset these for next time
    T = thresh;
  }

  if (N > 2500){                           // if 2.5 seconds go by without a beat
    thresh = 530;                          // set thresh default
    P = 512;                               // set P default
    T = 512;                               // set T default
    lastBeatTime = sampleCounter;          // bring the lastBeatTime up to date
    firstBeat = true;                      // set these to avoid noise
    secondBeat = false;                    // when we get the heartbeat back
  }
    sei();                                   // enable interrupts when youre done!
}


