package be.eaict.sleepqualitymeter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.CountryPickerListener;

/**
 * Created by CarlV on 2/22/2018.
 */

public class settings extends AppCompatActivity {
    String firstName, lastName, password;
    Boolean switchTemperature = false, switchLight = false, switchMeasurement = false;
    Switch temperature, light, measurement;
    EditText editFirstName, editLastName, editPassword, editWeight;
    TextView selectCountry;
    CountryPicker picker;
    String country;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Load();
        selectCountry = findViewById(R.id.setTxtSelectCountry);
        Button savebutton = findViewById(R.id.setBtnSave);
        temperature = findViewById(R.id.setSwitchTemp);
        light = findViewById(R.id.setSwitchLight);
        measurement = findViewById(R.id.setSwitchMeasurement);
        editFirstName = findViewById(R.id.setEditFirstName);
        editLastName = findViewById(R.id.setEditLastName);
        editPassword = findViewById(R.id.setEditPassw);
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Save();
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
                Intent intent = new Intent(getBaseContext(), HomeScreen.class);
                startActivity(intent);
            }
        });

        selectCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker = CountryPicker.newInstance("Select Country");  // dialog title
                picker.setListener(new CountryPickerListener() {
                    @Override
                    public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                        country = name;
                        selectCountry.setText(country);
                        picker.dismiss();
                    }
                });
                picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                Intent intentHome = new Intent(getBaseContext(), HomeScreen.class);
                                startActivity(intentHome);
                                break;

                            case R.id.navigation_overall:
                                break;

                            case R.id.navigation_profile:
                                break;

                            case R.id.navigation_records:
                                Intent intentSummary = new Intent(getBaseContext(), SleepSummary.class);
                                startActivity(intentSummary);
                                break;
                        }
                        return true;
                    }
                });
    }

    public void Save() {
        firstName = editFirstName.getText().toString();
        lastName = editLastName.getText().toString();
        password = editPassword.getText().toString();
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
/*        light.setChecked(switchLight);
        temperature.setChecked(switchTemperature);
        measurement.setChecked(switchMeasurement); */
    }
    }