package be.eaict.sleepqualitymeter;

public class IssueChecker {
    DataRepo repo;
    String sleepQuality, heartRateQuality;
    int issuecounter;
    LogicandCalc logicandCalc;
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
}

