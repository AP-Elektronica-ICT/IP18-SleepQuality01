package be.eaict.sleepqualitymeter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

/**
 * Created by CarlV on 2/22/2018.
 */

public class settings extends AppCompatActivity {
    String firstName, lastName, password;
    Boolean switchTemperature = false, switchLight = false, switchMeasurement = false;
    Switch temperature, light, measurement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button savebutton = findViewById(R.id.setBtnSave);
        temperature = findViewById(R.id.setSwitchTemp);
        light = findViewById(R.id.setSwitchLight);
        measurement = findViewById(R.id.setSwitchMeasurement);
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), HomeScreen.class);
                startActivity(intent);
                if(temperature.isChecked()) {
                    switchTemperature = true;
                }
                else {
                    switchTemperature = false;
                }
                if(light.isChecked()) {
                    switchLight = true;
                }
                else {
                    switchLight = false;
                }
                if(measurement.isChecked()) {
                    switchMeasurement = true;
                }
                else {
                    switchMeasurement = false;
                }
            }
        });
    }

    public void Save() {
        SharedPreferences sp = getSharedPreferences("DATA", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.apply();
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT);
    }
    public void Load() {
        SharedPreferences sp = getSharedPreferences("DATA", MODE_PRIVATE);
        switchMeasurement  = sp.getBoolean("measurement", false);
        switchLight = sp.getBoolean("light", false);
        switchTemperature = sp.getBoolean("temp", false);
        light.setChecked(switchLight);
        temperature.setChecked(switchTemperature);
        measurement.setChecked(switchMeasurement);
    }
    }