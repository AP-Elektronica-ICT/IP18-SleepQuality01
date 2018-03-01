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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        sleepLength = new SleepLength(dummyRepo.length());

        TextView name = findViewById(R.id.name);
        TextView sleepTime = findViewById(R.id.sleepTime);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                break;

                            case R.id.navigation_overall:

                            case R.id.navigation_profile:

                            case R.id.navigation_records:
                                Intent intent = new Intent(getBaseContext(), SleepSummary.class);
                                startActivity(intent);
                                break;

                            case R.id.navigation_settings:
                                Intent intentSettings = new Intent(getBaseContext(), settings.class);
                                startActivity(intentSettings);
                                break;
                        }
                        return true;
                    }
                });

        sleepTime.setText(calculator.SleepLengthString(sleepLength));
    }
}
