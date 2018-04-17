package be.eaict.sleepqualitymeter;

import android.util.Log;
import android.graphics.Color;
import android.media.MediaExtractor;
import android.util.Log;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;

public class IssueChecker {
    DataRepo repo;
    String sleepQuality, heartRateQuality, luxQuality, tempQuality, noiseQuality, humidityQuality;
    int issuecounter;
    LogicandCalc logicandCalc;
    int hour = 2200;
    public IssueChecker(DataRepo repo) {
        this.repo = repo;
        logicandCalc = new LogicandCalc();
    }


    public void sleepTimeChecker() {
        int time = repo.Repo.size() * 2;
        SleepLength sleepLength = new SleepLength(time);
        if(time > 360 && time < 540) {
            sleepQuality = "Good!";
        }
        else if(time <= 360) {
            sleepQuality = "You've only slept " + logicandCalc.SleepLengthString(sleepLength);
            issuecounter++;
        }
        else if(time >= 540) {
            sleepQuality = "You've slept " + logicandCalc.SleepLengthString(sleepLength);
            issuecounter++;
        }
    }

    public void heartRate() {
        List<Float> luxvalues = new ArrayList<>();
        List<Float> humidityvalues = new ArrayList<>();
        List<Float> noisevalues = new ArrayList<>();
        List<Float> tempvalues = new ArrayList<>();

        float luxvalue = 0, humidityvalue = 0, noisevalue = 0, tempvalue = 0;
        Boolean heartRateHit = false;
        Boolean temperatureHit = false;
        Boolean humidityHit = false;
        Boolean noiseHit = false;
        Boolean luxHit = false;
        int timer = 0;
        for(int i = 0; i < repo.Repo.size(); i++) {
            if(repo.Repo.get(i).getHeartbeat() > 110 && heartRateHit == false) {
                issuecounter++;
                heartRateHit = true;
                heartRateQuality = "Heartbeat went over 110!";
            }
            else heartRateQuality = "Good";
            luxvalue = luxvalue + repo.Repo.get(i).getLuminosity();
            humidityvalue =  humidityvalue + repo.Repo.get(i).getHumidity();
            noisevalue = noisevalue + repo.Repo.get(i).getNoise();
            tempvalue = tempvalue + repo.Repo.get(i).getTemperature();
            timer++;
            if(timer == 3) {
                luxvalues.add(luxvalue / 3);
                humidityvalues.add(humidityvalue / 3);
                noisevalues.add(noisevalue / 3);
                tempvalues.add(tempvalue / 3);
                luxvalue = 0;
                humidityvalue = 0;
                noisevalue = 0;
                tempvalue = 0;
                timer = 0;
            }
        }
        for(int i = 0; i <= luxvalues.size() - 1; i++) {
            Log.d("CheckerTemp", Float.toString(tempvalues.get(i)));
            if(luxvalues.get(i) >= 6 && luxHit == false) {
                luxHit = true;
                issuecounter++;
                luxQuality = "Lux went over 6!";
                Log.d("Checker", luxQuality);
            }
            if(noisevalues.get(i) >= 40 && noiseHit == false) {
                noiseHit = true;
                issuecounter++;
                noiseQuality = "Decibels went over 40";
                Log.d("Checker", noiseQuality);

            }
            if(tempvalues.get(i) >= 19 && temperatureHit == false) {
                temperatureHit = true;
                issuecounter++;
                tempQuality = "Room temperature went over 18.5 degrees celcius";
                Log.d("Checker", tempQuality);

            }
            if(humidityvalues.get(i) >= 60 && humidityHit == false) {
                humidityHit = true;
                issuecounter++;
                humidityQuality = "Humidty went over 60";
                Log.d("Checker", humidityQuality);
            }
        }
        Log.d("Luxlength", Integer.toString(luxvalues.size()));
        for(int i = 0; i <= luxvalues.size() - 1; i++) {
            Log.d("Luxvalues", Float.toString(luxvalues.get(i)));
        }

    }
    public int ColorPicker() {
        int color = 0;
        if (issuecounter != 0) {
            switch (issuecounter) {
                case 0:
                    color = Color.rgb(0, 255, 0);
                break;
                case 1:
                    color = Color.rgb(42, 213, 0);
                break;
                case 2:
                    color = Color.rgb(84, 171, 0);
                break;
                case 3:
                    color = Color.rgb(126, 129, 0);
                break;
                case 4:
                    color = Color.rgb(170, 87, 0);
                break;
                case 5:
                    color = Color.rgb(214, 45, 0);
                break;
                case 6:
                    color = Color.rgb(254, 3, 0);
                break;
            }
        }
        else color = Color.rgb(0,0,0);
        return color;
    }

    public void rhythm() {
        String Time = repo.Repo.get(0).getTimeStamp();
        String Hour = Time.substring(0,2);
        String minute = Time.substring(3,5);
        Time = Hour + minute;
        int SleepyTime = Integer.parseInt(Time);
        Log.d("Checker", Time);
        if(SleepyTime > hour + 45){
            issuecounter++;
            Log.d("Checker", "FUccct biological clock is behind now");
        }

        if (SleepyTime < hour - 45){
            issuecounter++;
            Log.d("Checker", "Sleeping early sounds like a good idea. But it is not.");
        }
    }
}

