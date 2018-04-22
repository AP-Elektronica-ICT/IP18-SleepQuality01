package be.eaict.sleepqualitymeter;

import android.util.Log;
import android.graphics.Color;
import android.media.MediaExtractor;
import android.util.Log;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class IssueChecker {
    DataRepo repo;
    String sleepQuality, heartRateQuality, luxQuality, tempQuality, noiseQuality, humidityQuality;
    float maxheartbeat = 0, maxtemp = 0, maxhumidity = 0, maxLum = 0, maxNoise = 0;
    List<String> issuestringlist = new ArrayList<>();
    List<Tips> tips = new ArrayList<>();
    TipRepo tipRepo = new TipRepo();
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
        tipRepo.totalsleeptime = repo.Repo.size();
        if(time > 360 && time < 540) {
            sleepQuality = "Good!";
        }
        else if(time <= 420) {
            sleepQuality = "You've only slept " + logicandCalc.SleepLengthString(sleepLength);
            issuecounter++;
            issuestringlist.add(sleepQuality);
            tips.add(new Tips(tipRepo.getMoreSleepTips()));
        }
        else if(time >= 540) {
            sleepQuality = "You've slept " + logicandCalc.SleepLengthString(sleepLength);
            issuecounter++;
            issuestringlist.add(sleepQuality);
            tips.add(new Tips(tipRepo.getTooMuchSleepTips()));
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
            if(repo.Repo.get(i).getHeartbeat() > maxheartbeat) maxheartbeat = repo.Repo.get(i).getHeartbeat();
            if(repo.Repo.get(i).getTemperature() > maxtemp) maxtemp = repo.Repo.get(i).getTemperature();
            if(repo.Repo.get(i).getLuminosity() > maxLum) maxLum = repo.Repo.get(i).getLuminosity();
            if(repo.Repo.get(i).getHumidity() > maxhumidity) maxhumidity = repo.Repo.get(i).getHumidity();
            if(repo.Repo.get(i).getNoise() > maxNoise) maxNoise = repo.Repo.get(i).getNoise();
            if(repo.Repo.get(i).getHeartbeat() > 100 && heartRateHit == false) {
                issuecounter++;
                heartRateHit = true;
                heartRateQuality = "Heartbeat went over 110!";
                Log.d("CHECKheartbeat", heartRateQuality);
            }
            else {
                heartRateQuality = "Good";
                Log.d("CHECKheartbeat", heartRateQuality);
            }
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
                luxQuality = "It was too bright in your room";
            }
            if(noisevalues.get(i) >= 40 && noiseHit == false) {
                noiseHit = true;
                issuecounter++;
                noiseQuality = "It was too loud in your room";
            }
            if(tempvalues.get(i) >= 19 && temperatureHit == false) {
                temperatureHit = true;
                issuecounter++;
                tempQuality = "It was too hot in your room";
            }
            if(humidityvalues.get(i) >= 60 && humidityHit == false) {
                humidityHit = true;
                issuecounter++;
                humidityQuality = "It was too humid in your room";
            }
        }

        if(heartRateHit == true){
            issuestringlist.add(heartRateQuality);
            tipRepo.maxHeartbeat = maxheartbeat;
            tips.add(new Tips(tipRepo.getHeartbeatTips()));
        }
        if(luxHit == true) {
            issuestringlist.add(luxQuality);
            tipRepo.maxLuminosity = maxLum;
            tips.add(new Tips(tipRepo.getLuminosityTips()));
        }
        if(noiseHit == true) {
            tipRepo.maxNoise = maxNoise;
            issuestringlist.add(noiseQuality);
            tips.add(new Tips(tipRepo.getNoiseTips()));
        }
        if(temperatureHit == true) {
            issuestringlist.add(tempQuality);
            tipRepo.maxTemp = maxtemp;
            tips.add(new Tips(tipRepo.getTempTips()));
        }
        if(humidityHit == true) {
            issuestringlist.add(humidityQuality);
            tipRepo.maxHumidity = maxhumidity;
            tips.add(new Tips(tipRepo.getHumidityTips()));
        }
        Log.d("REPO",Integer.toString(issuestringlist.size()));
        Log.d("REPOT", Float.toString(maxtemp));
        Log.d("Repotemp", Float.toString(maxhumidity));
        Log.d("Repotemp", Float.toString(maxheartbeat));
        Log.d("Repotemp", Float.toString(maxLum));
        Log.d("Repotemp", Float.toString(maxheartbeat));


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

