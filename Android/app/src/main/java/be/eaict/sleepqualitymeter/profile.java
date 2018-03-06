package be.eaict.sleepqualitymeter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by CarlV on 2/27/2018.
 */

public class profile extends AppCompatActivity {
    String firstName, lastName, email, country, avgSleepTime, userid, birthdate;
    Integer weight, rawdata_weight;
    Boolean measurement;
    TextView txtName, txtAge, txtEmail, txtNationality, txtWeight, txtAvgSleepTime;

    //Firebase
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

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

        mAuth = FirebaseAuth.getInstance();
        email = mAuth.getCurrentUser().getEmail();

        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    User user = snapshot.getValue(User.class);

                    if(user.getEmail().equals(email)){
                        userid = user.getId();
                        email = user.getEmail();
                        firstName = user.getFirstname();
                        lastName = user.getLastname();
                        country = user.getCountry();
                        rawdata_weight = user.getWeight();
                        birthdate = user.getBirthdate();
                    }
                }
                txtName.setText(firstName + " " + lastName);
                txtEmail.setText(email);
                txtNationality.setText(country);
                txtAge.setText(birthdate);
                txtWeight.setText(rawdata_weight.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
            Double lbstokg = rawdata_weight / 0.45359237;
            weight = lbstokg.intValue();
        }
        else {
            weight = rawdata_weight;
        }

    }
    private void Load() {
        SharedPreferences sp = getSharedPreferences("DATA", MODE_PRIVATE);
        measurement = sp.getBoolean("measurement", false);
    }
}

