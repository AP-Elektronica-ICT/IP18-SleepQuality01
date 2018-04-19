int xAcclOld = 0;
int yAcclOld = 0;
int zAcclOld = 0;

float avgOld = 0;

float xAccl[10];
float yAccl[10];
float zAccl[10];

//float filtered[30]; // RMS method uses these
//float potenssi[30];

void ADXLSetup() {
  // Sets registers as wanted
  // https://www.sparkfun.com/datasheets/Sensors/Accelerometer/ADXL345.pdf
  ADXLRegisterWrite(0x2D, 0x00); // reset
  ADXLRegisterWrite(0x2D, 0x08); // 08 POWER_CTL register     // Measure     Auto-sleep disabled
  ADXLRegisterWrite(0x2C, 0x06); // 06Select bandwidth rate register  // Normal mode, Output data rate = 100 Hz  
  
  ADXLRegisterWrite(0x31, 0x08); // Select data format register  // Self test disabled, 4-wire interface, Full resolution, Range = +/-2g 
}

void ADXLRead() {
  // Reads the registers of ADXL and tries to calculate if any movement has been noticed
  unsigned int data[6];  
  
  for(int i = 0; i < 6; i++) {    
    Wire.beginTransmission(ADXL_ADDR);    // Start I2C Transmission    
    Wire.write((50 + i));    // Select data register    
    Wire.endTransmission();        // Stop I2C transmission    
    Wire.requestFrom(ADXL_ADDR, 1);        // Request 1 byte of data    

    // Read 6 bytes of data    xAccl lsb, xAccl msb, yAccl lsb, yAccl msb, zAccl lsb, zAccl msb    
    if(Wire.available() == 1) {      
      data[i] = Wire.read();    
    }  
  }    

  float xAccl1 = convertADXL(data[0], data[1])* 0.004 * 9.81;
  float yAccl1 = convertADXL(data[2], data[3])* 0.004 * 9.81;
  float zAccl1 = convertADXL(data[4], data[5])* 0.004 * 9.81;

  movementDetection(xAccl1, yAccl1, zAccl1);
}


void ADXLRegisterWrite( byte reg, byte val) {
  // Write to ADXL register
  Wire.beginTransmission(ADXL_ADDR); 
  Wire.write(reg);  // register
  Wire.write(val);  // value to be written
  Wire.endTransmission();  
}

void ADXLRegisterRead( byte reg) {
  // Read ADXL registers
  Wire.beginTransmission(ADXL_ADDR);
  Wire.requestFrom(ADXL_ADDR,1); 
  byte val = Wire.read();
  Wire.endTransmission();
  
  Serial.print("read: ");
  Serial.print(val, BIN);
  Serial.print("  ");
  Serial.println(val, HEX);
}

int convertADXL(unsigned int data1, unsigned int data2) {
  // Converts the data to 10-bits  
  int accl = (((data2 & 0x03) * 256) + data1);  
  
  if(accl > 511)  {    
  accl -= 1024;  
  }  
  return accl;
}

/*
void movementDetectionRMS(float xAccl1, float yAccl1, float zAccl1)
{
  // Using RMS, detects if there has been movement
  float akok = sqrt( pow(xAccl1, 2) + pow(yAccl1, 2) + pow(zAccl1, 2));
  filtered[9] = akok*.2 + filtered[8]*.8;
  //Serial.print(akok);
  //Serial.print("  ");
  //Serial.println(filtered[29]);
  float ulipaasto = akok - filtered[9];
  
  potenssi[9] = pow(ulipaasto, 2);
  float RMS = 0;
  for (int i = 0; i < 10; i++){
    RMS += potenssi[i];
  }
  RMS = sqrt(RMS / 10);

  for (int i = 0; i < 9; i++){
    potenssi[i] = potenssi[i+1];
    filtered[i] = filtered[i+1];
  }
  Serial.println(RMS);
}*/



void movementDetection(float xAccl1, float yAccl1, float zAccl1)
{
  // Detects if there has been movement
  bool movement = 0; // Has there been movement or not?
  float avg = 0;
  float diff = 0;

  /////////////// X
  
  for(int i=4; i>0; i--)
  {
    xAccl[i] = xAccl[i-1];
    avg += xAccl[i];
  }
  avg = avg/4;
  xAccl[0] = xAccl1;

  diff = abs(xAccl[0]-avg);
  if (diff > 0.16) {movement = 1;}

  ///////////////// Y

  avg = 0;
  for(int i=4; i>0; i--)
  {
    yAccl[i] = yAccl[i-1];
    avg += yAccl[i];
  }
  avg = avg/4;
  yAccl[0] = yAccl1;

  diff = abs(yAccl[0]-avg);
  if (diff > 0.16) {movement = 1;}

  ///////////////// Z 

  avg = 0;
  for(int i=4; i>0; i--)
  {
    zAccl[i] = zAccl[i-1];
    avg += zAccl[i];
  }
  avg = avg/4;
  zAccl[0] = zAccl1;

  diff = abs(zAccl[0]-avg);
  if (diff > 0.16) {movement = 1;}

  /////////////////////
  if (movementWithin10Sec > 0) { // Keeps track of how much movement there has been lately
    movementWithin10Sec -= 0.5;
    if (movementWithin10Sec > 10) { movementWithin10Sec = 10;}
  }

  if (movement) { // Movement has been detected
    movements[timestampCount] += 1; 
    movementWithin10Sec++; 
    Serial.println("movement");
  }
}

