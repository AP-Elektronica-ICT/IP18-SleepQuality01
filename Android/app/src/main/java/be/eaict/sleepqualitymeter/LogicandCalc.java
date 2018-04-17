package be.eaict.sleepqualitymeter;

import java.util.ArrayList;
import java.util.List;

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

    public float[] MinMax(float[] data){
        float min = data[4], max = data[4];
        for (int i = 4; i < data.length - 4; i++){
            if (data[i] > max){
                max = data[i];
            }
            if (data[i] < min){
                min = data[i];
            }
        }
        return new float[]{min, max};
    }

    public List<Float> getDataType(String dataType, List<Data> Repo){
        List<Float> end = new ArrayList<>();
        for (int i = 0; i < Repo.size(); i++){
            switch (dataType){
                case "heartbeat":
                    end.add(Repo.get(i).getHeartbeat());
                    break;

                case "humidity":
                    end.add(Repo.get(i).getHumidity());
                    break;

                case "luminosity":
                    end.add(Repo.get(i).getLuminosity());
                    break;

                case "movement":
                    end.add(Repo.get(i).getMovement());
                    break;

                case "noise":
                    end.add(Repo.get(i).getNoise());
                    break;

                case "temperature":
                    end.add(Repo.get(i).getTemperature());
                    break;
            }
        }
        return end;
    }
}
