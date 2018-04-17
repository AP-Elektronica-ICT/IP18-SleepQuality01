package be.eaict.sleepqualitymeter;

import android.util.Log;

public class IssueChecker {
    DataRepo repo;
    String sleepQuality, heartRateQuality;
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
        for(int i = 0; i < repo.Repo.size(); i++) {
            if(repo.Repo.get(i).getHeartbeat() > 110) {
                issuecounter++;
                heartRateQuality = "Heartbeat went too high!";
            }
            else heartRateQuality = "Good";
        }
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

