package be.eaict.sleepqualitymeter;

/**
 * Created by sande on 15/03/2018.
 */

public class Data {
    private int Heartbeat;
    private int Humidity;
    private int Luminosity;
    private double Movement;
    private int Noise;
    private int Temperature;

    public Data(){}

    public Data(int Heartbeat, int Humidity, int Luminosity, double Movement, int Noise, int Temperature){
        this.Heartbeat = Heartbeat;
        this.Humidity = Humidity;
        this.Luminosity = Luminosity;
        this.Movement = Movement;
        this.Noise = Noise;
        this.Temperature = Temperature;
    }

    public int getHeartbeat() { return Heartbeat; }

    public int getHumidity() { return Humidity; }

    public int getLuminosity() { return Luminosity; }

    public double getMovement() { return Movement; }

    public int getNoise() { return Noise; }

    public int getTemperature() { return Temperature; }
}
