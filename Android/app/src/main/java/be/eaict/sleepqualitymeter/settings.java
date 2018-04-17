package be.eaict.sleepqualitymeter;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mukesh.countrypicker.Country;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.CountryPickerListener;

/**
 * Created by CarlV on 2/22/2018.
 */

public class settings extends AppCompatActivity {
    String firstName, lastName, password, newcountry, oldcountry, weight, email, userID;
    Boolean switchTemperature, switchLight, switchMeasurement;
    Switch temperature, light, measurement;
    EditText editFirstName, editLastName, editPassword, editWeight;
    TextView selectCountry;
    CountryPicker picker;

    //Firebase
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    ImageView imgCountry;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        switchTemperature = false;
        switchLight = false;
        switchMeasurement = false;
        user = LandingPage.DefUser;
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        selectCountry = findViewById(R.id.setTxtSelectCountry);
        Button savebutton = findViewById(R.id.setBtnSave);
        Button saveDiscard = findViewById(R.id.setBtnDiscard);
        Button deleteUser = findViewById(R.id.setBtnDelUser);
        temperature = findViewById(R.id.setSwitchTemp);
        light = findViewById(R.id.setSwitchLight);
        measurement = findViewById(R.id.setSwitchMeasurement);
        editFirstName = findViewById(R.id.setEditFirstName);
        editLastName = findViewById(R.id.setEditLastName);
        editPassword = findViewById(R.id.setEditPassw);
        editWeight = findViewById(R.id.setEditWeight);
        imgCountry = findViewById(R.id.setImgCountry);
        Load();
        //    Country tempcountry = Country.getCountryByName(oldcountry);
        //    imgCountry.setImageResource(tempcountry.getFlag());
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (temperature.isChecked()) {
                    switchTemperature = true;
                } else {
                    switchTemperature = false;
                }
                if (light.isChecked()) {
                    switchLight = true;
                } else {
                    switchLight = false;
                }
                if (measurement.isChecked()) {
                    switchMeasurement = true;
                } else {
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
        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(settings.this);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to delete your account and erase all the data?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        firebaseUser.delete();
                        databaseReference.child(userID).removeValue();
                        Intent intent = new Intent(getBaseContext(), login.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public void Save() {
        final String newpassword = editPassword.getText().toString();
        final String newfirstName = editFirstName.getText().toString();
        final String newlastName = editLastName.getText().toString();
        String newweight = editWeight.getText().toString();
        SharedPreferences sp = getSharedPreferences("DATA", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("light", switchLight);
        editor.putBoolean("measurement", switchMeasurement);
        editor.putBoolean("temp", switchTemperature);
        //Set password Firebase
        if (!newpassword.isEmpty()) {
            if (newpassword.length() < 6) {
                editPassword.setError("Minimum lenght of password should be 6");
                editPassword.requestFocus();
                return;
            }
            firebaseUser.updatePassword(newpassword);
        }
        if (newfirstName != firstName && !editFirstName.toString().isEmpty()) {
            databaseReference.child(userID).child("firstname").setValue(newfirstName);
            user.setFirstname(editFirstName.toString());
        }
        if (newlastName != lastName && !editLastName.toString().isEmpty()) {
            databaseReference.child(userID).child("lastname").setValue(newlastName);
            user.setLastname(editLastName.toString());
        }
        if (newweight != weight && !editWeight.toString().isEmpty()) {
            //   int t = Integer.getInteger(editWeight.getText().toString().);
          /*  if(t  > 200) {
                editWeight.setError("Max weight is 200");
                editWeight.requestFocus();
                return;
            } */
            if (measurement.isChecked() == false) {
                databaseReference.child(userID).child("weight").setValue(newweight);
                user.setWeight(newweight);
            } else {
                Double tempweight = Double.parseDouble(newweight) * 0.45359237;
                int tempt = tempweight.intValue();
                databaseReference.child(userID).child("weight").setValue(Integer.toString(tempt));
                user.setWeight(Integer.toString(tempt));
            }
        }
        if (newcountry != oldcountry && newcountry != null) {
            databaseReference.child(userID).child("country").setValue(newcountry);
            user.setCountry(newcountry);
        }
        editor.apply();
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT);
    }

    public void Load() {
        SharedPreferences sp = getSharedPreferences("DATA", MODE_PRIVATE);
        switchMeasurement = sp.getBoolean("measurement", false);
        switchLight = sp.getBoolean("light", false);
        switchTemperature = sp.getBoolean("temp", false);
        email = user.getEmail();
        oldcountry = user.getCountry();
        firstName = user.getFirstname();
        lastName = user.getLastname();
        userID = user.getId();
        editWeight.setText(weight);
        selectCountry.setText(oldcountry);
        editFirstName.setText(firstName);
        editLastName.setText(lastName);
        measurement.setChecked(switchMeasurement);
        light.setChecked(switchLight);
        temperature.setChecked(switchTemperature);
        String rawdata_weight = user.getWeight();
        //Convert KG to pound
        if (measurement.isChecked() == true) {
            Double lbstokg = Double.parseDouble(rawdata_weight) / 0.45359237;
            int t = lbstokg.intValue();
            weight = Integer.toString(t);
            editWeight.setText(weight);
        } else {
            weight = rawdata_weight;
            editWeight.setText(weight);
        }

        Country tempcountry = Country.getCountryByName(oldcountry);
        imgCountry.setImageResource(tempcountry.getFlag());
    }
}