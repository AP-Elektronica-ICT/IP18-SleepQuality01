package be.eaict.sleepqualitymeter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

public class SleepSummary extends AppCompatActivity {

    LineChart movement;
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
    }
}
