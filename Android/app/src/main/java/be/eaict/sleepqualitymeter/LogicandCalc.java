package be.eaict.sleepqualitymeter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonas on 22/02/2018.
 */

public class LogicandCalc {

    public  String SleepLengthString(SleepLength sleepLength){
        return sleepLength.getSleeptime()[0] + " hrs and " + sleepLength.getSleeptime()[1] + " mins";
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

    public int timeStamptoInt(String TimeStamp){
        String Time = TimeStamp;
        String Hour = Time.substring(0,2);
        String minute = Time.substring(3,5);
        Time = Hour + minute;
        int SleepyTime = Integer.parseInt(Time);
        return SleepyTime;
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
    
    public double getAverageOfWeek(String dataType, List<DataRepo> Repo){
        double Total = 0;
        int amount = 0;
        if (Repo.size() < 7){
            for (int i = 0; i < Repo.size(); i++) {
                amount += Repo.get(i).Repo.size();
                for (int j = 0; j < Repo.get(i).Repo.size() ; j++) {
                    switch (dataType){
                        case "heartbeat":
                            Total += Repo.get(i).Repo.get(j).getHeartbeat();
                            break;

                        case "humidity":
                            Total += Repo.get(i).Repo.get(j).getHumidity();
                            break;

                        case "luminosity":
                            Total += Repo.get(i).Repo.get(j).getLuminosity();
                            break;

                        case "movement":
                            Total += Repo.get(i).Repo.get(j).getMovement();
                            break;

                        case "noise":
                            Total += Repo.get(i).Repo.get(j).getNoise();
                            break;

                        case "temperature":
                            Total += Repo.get(i).Repo.get(j).getTemperature();
                            break;
                    }
                }
            return Total/amount;
            }
        } else{
            for (int i = 0; i < 7; i++) {
                amount += Repo.get(i).Repo.size();
                for (int j = 0; j < Repo.get(i).Repo.size() ; j++) {
                    switch (dataType){
                        case "heartbeat":
                            Total += Repo.get(i).Repo.get(j).getHeartbeat();
                            break;

                        case "humidity":
                            Total += Repo.get(i).Repo.get(j).getHumidity();
                            break;

                        case "luminosity":
                            Total += Repo.get(i).Repo.get(j).getLuminosity();
                            break;

                        case "movement":
                            Total += Repo.get(i).Repo.get(j).getMovement();
                            break;

                        case "noise":
                            Total += Repo.get(i).Repo.get(j).getNoise();
                            break;

                        case "temperature":
                            Total += Repo.get(i).Repo.get(j).getTemperature();
                            break;
                    }
                }
            }
        }
        return Total/7;
    }
}
