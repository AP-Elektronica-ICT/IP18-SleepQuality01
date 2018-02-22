package be.eaict.sleepqualitymeter;

/**
 * Created by jonas on 22/02/2018.
 */

public class LogicandCalc {

    public float calculateAverage(float[] data){
        float sum = 0;
        for (int i = 0; i < data.length; i++){
            sum += data[i];
        }
        return sum/data.length;
    }

    public  String SleepLengthString(SleepLength sleepLength){
        return sleepLength.getSleeptime()[0] + " hrs and " + sleepLength.getSleeptime()[1] + " mins.";
    }
}
