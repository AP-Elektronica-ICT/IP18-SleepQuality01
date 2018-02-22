package be.eaict.sleepqualitymeter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private LogicandCalc calculator;
    private SleepLength LastNight;
    private DummyRepo dummyRepo = new DummyRepo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_summary);

        calculator = new LogicandCalc();
        LastNight = new SleepLength(dummyRepo.length());

        movement = findViewById(R.id.movement);
        List<Entry> movementEntries = new ArrayList<>();
        for(int i = 0; i < dummyRepo.length(); i++){
            movementEntries.add(new Entry(i * 2, dummyRepo.dummyRepo[i]));
        }

        LineDataSet movementDataSet = new LineDataSet(movementEntries, "Movement");
        LineData movementData = new LineData(movementDataSet);
        movement.setData(movementData);
        movement.invalidate();

        TextView averageMovement = new TextView(this);
        averageMovement.setText("Your average movement this night was " + calculator.calculateAverage(dummyRepo.dummyRepo) + ".");

        TextView sleepTime = new TextView(this);
        sleepTime.setText("You slept " + calculator.SleepLengthString(LastNight));

        LinearLayout layout = findViewById(R.id.layout);
        layout.addView(averageMovement);
        layout.addView(sleepTime);
    }


}
