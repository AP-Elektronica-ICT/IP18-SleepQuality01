package be.eaict.sleepqualitymeter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class HomeScreen extends AppCompatActivity {

    LogicandCalc calculator = new LogicandCalc();
    SleepLength sleepLength;
    DummyRepo dummyRepo = new DummyRepo();
    DockNavigation dock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        sleepLength = new SleepLength(dummyRepo.length());

        TextView name = findViewById(R.id.name);
        TextView sleepTime = findViewById(R.id.sleepTime);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        dock = new DockNavigation(bottomNavigationView, getBaseContext());



        sleepTime.setText(calculator.SleepLengthString(sleepLength));
    }
}
