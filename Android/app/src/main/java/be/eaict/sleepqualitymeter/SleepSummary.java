package be.eaict.sleepqualitymeter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.io.Console;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

public class SleepSummary extends AppCompatActivity {

    private LineChart movement;
    float[] dummyRepo = {7, 0, 3, 0, 0, 0, 0, 0, 0, (float) 1.75, 1, 0, (float) 1.75, (float) 1.25, 0, 0, 0, 0, 0, 0, (float) 0.25, 0, 0, 0, 0, (float) 0.5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, (float) 0.25, 0, 0, 0, 0, 0, 0, 0, (float) 0.25, (float) 1.5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, (float) 0.75, (float) 2.75};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_summary);

        movement = findViewById(R.id.movement);

        List<Entry> movementEntries = new ArrayList<>();
        for(int i = 0; i < dummyRepo.length; i++){
            movementEntries.add(new Entry(i * 2, dummyRepo[i]));
        }

        LineDataSet movementDataSet = new LineDataSet(movementEntries, "Movement");
        LineData movementData = new LineData(movementDataSet);
        movement.setData(movementData);
        movement.invalidate();

        Log.d("AverageCalc", "Average= "+ calculateAverage(dummyRepo));
        Log.d("TimeCalc", "Time= "+ calcutateSleepLength(dummyRepo.length)[0] +", "+calcutateSleepLength(dummyRepo.length)[1] +", "+calcutateSleepLength(dummyRepo.length)[2]);
    }

    public float calculateAverage(float[] data){
        float sum = 0;
        for (int i = 0; i < data.length; i++){
            sum += data[i];
        }
        return sum/data.length;
    }

    public  int[] calcutateSleepLength(int length){
        int total = length * 2;
        int hours = total/60;
        int minutes = total - (hours*60);
        int[] val = {hours, minutes, total};
        return val;
    }
}
