package be.eaict.sleepqualitymeter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        TextView name = findViewById(R.id.name);
        TextView sleeptime = findViewById(R.id.sleepTime);

        
    }
}
