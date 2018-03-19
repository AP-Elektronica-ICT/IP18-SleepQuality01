package be.eaict.sleepqualitymeter;

/**
 * Created by sande on 15/03/2018.
 */

public class Data {
    int heartbeat;
    int humidity;
    int luminosity;
    int movement;
    int noise;
    int temperature;

    public Data(){}

    public Data(int heartbeat, int humidity, int luminosity, int movement, int noise, int temperature){
        this.heartbeat = heartbeat;
        this.humidity = humidity;
        this.luminosity = luminosity;
        this.movement = movement;
        this.noise = noise;
        this.temperature = temperature;
    }

    public int getHeartbeat() { return heartbeat; }

    public int getHumidity() { return humidity; }

    public int getLuminosity() { return luminosity; }

    public int getMovement() { return movement; }

    public int getNoise() { return noise; }

    public int getTemperature() { return temperature; }
}
