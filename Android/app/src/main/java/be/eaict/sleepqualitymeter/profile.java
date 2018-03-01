package be.eaict.sleepqualitymeter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by CarlV on 2/27/2018.
 */

public class profile extends AppCompatActivity {
    String firstName, lastName, email, nationality, avgSleepTime;
    Integer age, weight;
    Boolean measurement;
    TextView txtName, txtAge, txtEmail, txtNationality, txtWeight, txtAvgSleepTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Load();

        txtAge = findViewById(R.id.profTxtAge);
        txtEmail = findViewById(R.id.profTxtEmail);
        txtName = findViewById(R.id.profTxtName);
        txtNationality = findViewById(R.id.profTxtNationality);
        txtWeight = findViewById(R.id.profTxtWeight);
        txtAvgSleepTime = findViewById(R.id.profTxtAvgSleepTime);
        txtAvgSleepTime.setText("N/A");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_profile);
        DockNavigation dockNavigation = new DockNavigation(bottomNavigationView, getBaseContext());

        Button btnSettings = findViewById(R.id.profBtnSettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), settings.class);
                startActivity(intent);
            }
        });
        int value = 0; // Value = Kg from DB
        //Convert KG to pound
        if(measurement == true) {
            Double lbstokg = value / 0.45359237;
            weight = lbstokg.intValue();
        }
        else {
            weight = value;
        }
    }
    private void Load() {
        SharedPreferences sp = getSharedPreferences("DATA", MODE_PRIVATE);
        measurement = sp.getBoolean("measurement", false);
    }
}

