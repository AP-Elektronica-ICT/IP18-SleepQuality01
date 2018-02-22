package be.eaict.sleepqualitymeter;

/**
 * Created by jonas on 22/02/2018.
 */

public class SleepLength {
    private int[] Sleeptime;

    public int[] getSleeptime(){
        return Sleeptime;
    }

    public SleepLength(int length){
        int total = length * 2;
        int hours = total/60;
        int minutes = total - (hours*60);
        int[] val = {hours, minutes, total};
        Sleeptime = val;
    }
}
