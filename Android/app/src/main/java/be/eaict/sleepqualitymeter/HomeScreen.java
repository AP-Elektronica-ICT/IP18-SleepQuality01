package be.eaict.sleepqualitymeter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        sleepTime.setText(calculator.SleepLengthString(sleepLength));
    }
}
