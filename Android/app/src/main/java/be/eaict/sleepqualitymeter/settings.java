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
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mukesh.countrypicker.Country;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.CountryPickerListener;

/**
 * Created by CarlV on 2/22/2018.
 */

public class settings extends AppCompatActivity {
    String firstName, lastName, password, newcountry, oldcountry, weight;
    Boolean switchTemperature , switchLight, switchMeasurement;
    Switch temperature, light, measurement;
    EditText editFirstName, editLastName, editPassword, editWeight;
    TextView selectCountry;
    CountryPicker picker;
    //Firebase
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    ImageView imgCountry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        switchTemperature = false;
        switchLight = false;
        switchMeasurement = false;

        Load();

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        selectCountry = findViewById(R.id.setTxtSelectCountry);
        Button savebutton = findViewById(R.id.setBtnSave);
        Button saveDiscard = findViewById(R.id.setBtnDiscard);
        temperature = findViewById(R.id.setSwitchTemp);
        light = findViewById(R.id.setSwitchLight);
        measurement = findViewById(R.id.setSwitchMeasurement);
        editFirstName = findViewById(R.id.setEditFirstName);
        editLastName = findViewById(R.id.setEditLastName);
        editPassword = findViewById(R.id.setEditPassw);
        imgCountry = findViewById(R.id.setImgCountry);
        editWeight.setText(weight);
        selectCountry.setText(oldcountry);
        Country tempcountry = Country.getCountryByName(oldcountry);
        imgCountry.setImageResource(tempcountry.getFlag());
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                Save();

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        light.setChecked(switchLight);
        temperature.setChecked(switchTemperature);
        measurement.setChecked(switchMeasurement);
        selectCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker = CountryPicker.newInstance("Select Country");  // dialog title
                picker.setListener(new CountryPickerListener() {
                    @Override
                    public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                        newcountry = name;
                        selectCountry.setText(newcountry);
                        imgCountry.setImageResource(flagDrawableResID);
                        picker.dismiss();
                    }
                });
                picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
            }
        });
    saveDiscard.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    });
    }

    public void Save() {
        String newpassword = editPassword.getText().toString();
        String newfirstName = editFirstName.getText().toString();
        String newlastName = editLastName.getText().toString();
        String newweight = editWeight.getText().toString();
        SharedPreferences sp = getSharedPreferences("DATA", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("light", switchLight);
        editor.putBoolean("measurement", switchMeasurement);
        editor.putBoolean("temp", switchTemperature);

        //Set password Firebase
        if(!newpassword.isEmpty() && newpassword != password){
            if(password.length() < 6){
                editPassword.setError("Minimum lenght of password should be 6");
                editPassword.requestFocus();
                return;
            }
            firebaseUser.updatePassword(password);
        }
        if(newfirstName != firstName) {
            //zet newfirstName als nieuwe firstName in DB
        }
        if(newlastName != lastName) {
            //Zet newlastName als lastName in DB
        }
        if(newweight != weight) {
            if(Integer.getInteger(newweight) < 200) {
                editWeight.setError("Max weight is 200");
                editWeight.requestFocus();
                return;
            }
            if (measurement.isChecked() == false) {
                //Zet newweight als weight in DB
            }
            else {
                Double tempweight = Double.parseDouble(newweight) * 0.45359237;
                int Finalweight = tempweight.intValue();
                //Zet Finalweight als weight in DB
            }
        }
        if (newcountry != oldcountry) {
            //Zet newcountry als country in DB
        }
        editor.apply();
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT);
    }
    public void Load() {
        SharedPreferences sp = getSharedPreferences("DATA", MODE_PRIVATE);
        switchMeasurement  = sp.getBoolean("measurement", false);
        switchLight = sp.getBoolean("light", false);
        switchTemperature = sp.getBoolean("temp", false);
    }
    }