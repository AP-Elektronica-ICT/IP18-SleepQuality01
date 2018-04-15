package be.eaict.sleepqualitymeter;

/**
 * Created by sande on 15/03/2018.
 */

public class Data {
    private String TimeStamp;
    private float Heartbeat;
    private float Humidity;
    private float Luminosity;
    private float Movement;
    private float Noise;
    private float Temperature;

    public Data(){}

    public Data(String TimeStamp, float Heartbeat, float Humidity, float Luminosity, float Movement, float Noise, float Temperature){
        this.TimeStamp = TimeStamp;
        this.Heartbeat = Heartbeat;
        this.Humidity = Humidity;
        this.Luminosity = Luminosity;
        this.Movement = Movement;
        this.Noise = Noise;
        this.Temperature = Temperature;
    }

    public String getTimeStamp() { return TimeStamp; }

    public float getHeartbeat() { return Heartbeat; }

    public float getHumidity() { return Humidity; }

    public float getLuminosity() { return Luminosity; }

    public float getMovement() { return Movement; }

    public float getNoise() { return Noise; }

    public float getTemperature() { return Temperature; }
}
