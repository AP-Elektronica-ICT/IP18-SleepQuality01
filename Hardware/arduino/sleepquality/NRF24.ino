// Radio pipe addresses for the 2 nodes to communicate
const uint64_t pipes[2] = { 0xF0F0F0F0E1LL, 0xF0F0F0F0D2LL };

// Dynamic payload, so our start, ending messages and sensor values can be how ever they want to be
const int min_payload_size = 4; 
const int max_payload_size = 32;
const int payload_size_increments_by = 1;
int next_payload_size = min_payload_size;

char receive_payload[max_payload_size+1]; // +1 to allow room for a terminating NULL char

void NRFReInit() {        
  // NRF24 will be initialized again when power is shut and booted again
  digitalWrite (7, LOW); 
  digitalWrite (8, HIGH);

  radio.begin(); // Setup and configure rf radio

  radio.enableDynamicPayloads(); // enable dynamic payloads
  radio.setAutoAck(1);                     // Ensure autoACK is enabled
  radio.setRetries(5,20); // optionally, increase the delay between retries & # of retries

  radio.openWritingPipe(pipes[0]);
  radio.openReadingPipe(1,pipes[1]);

  radio.startListening();
}

void NRFInit() {
  // Initializes the nRF, by giving it the matching register values as in gateway
  //printf_begin(); // Used for printing the registers and such
  radio.begin(); // Setup and configure rf radio

  radio.enableDynamicPayloads(); // enable dynamic payloads
  radio.setAutoAck(1);                     // Ensure autoACK is enabled
  radio.setRetries(5,20); // optionally, increase the delay between retries & # of retries

  radio.openWritingPipe(pipes[0]);
  radio.openReadingPipe(1,pipes[1]);

  radio.startListening();

  radio.printDetails();
}

const char * NRFSend(const char * send_payload){
  // Sends data to gateways using the nRF
  retry:  // Quick and messy way for retrying to send data
    radio.stopListening();  // Stop listening so we can talk
    
    Serial.print(F("Now sending length "));
    Serial.println(next_payload_size);
    radio.write( send_payload, strlen(send_payload)); // Send payload size
  
    radio.startListening(); // Continue listening
  
    // Wait for response or timeout
    unsigned long startedWaitingAt = millis();
    bool timeout = false;
    while ( ! radio.available() && ! timeout ) {
      if (millis() - startedWaitingAt > 200 ) { // Timed out
        timeout = true;
        delay(10);  // Small delay before retrying
        goto retry; // Back to beginning of trying to send data
        }
    }

    if (timeout) // Show results
    {
      Serial.println(F("Failed, response timed out."));
      delay(10);  // Small delay before retrying
      goto retry; // Back to beginning of trying to send data
    }
    else
    {
      uint8_t len = radio.getDynamicPayloadSize(); // Get response
      
      if(!len){ // If received wrong payload, so it's corrupted
        Serial.println("Corrupted payload");
        delay(10);  // Small delay before retrying
        goto retry; // Back to beginning of trying to send data
      }
      
      radio.read( receive_payload, len ); // Read the actual data from NRF24

      receive_payload[len] = 0;      // Put a zero at the end for easy printing

      Serial.print(F("Got response size="));
      Serial.print(len);
      Serial.print(F(" value="));
      Serial.println(receive_payload);
      
      return; // Finally leave the messy function
    }
}
