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
    String firstName, lastName, email, country, avgSleepTime, userid, birthdate, weight, rawdata_weight;
    Boolean measurement;
    TextView txtName, txtAge, txtEmail, txtNationality, txtWeight, txtAvgSleepTime;
    User user;

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
        
        mAuth = FirebaseAuth.getInstance();
        email = mAuth.getCurrentUser().getEmail().toLowerCase();

        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    user = snapshot.getValue(User.class);

                    System.out.println(user.getEmail());

                    if(user.getEmail().equals(email)){
                        System.out.println("WOOOOHOOO");
                        userid = user.getId();
                        email = user.getEmail();
                        firstName = user.getFirstname();
                        lastName = user.getLastname();
                        country = user.getCountry();
                        rawdata_weight = user.getWeight();
                        birthdate = user.getBirthdate();
                    }
                }

                //Convert KG to pound
                if(measurement == true) {
                    Double lbstokg = Double.parseDouble(rawdata_weight) / 0.45359237;
                    int t = lbstokg.intValue();
                    weight = Integer.toString(t);
                    txtWeight.setText(weight + " lbs");
                }
                else {
                    weight = rawdata_weight;
                    txtWeight.setText(weight + " kg");
                }

                txtName.setText(firstName + " " + lastName);
                txtEmail.setText(email);
                txtNationality.setText(country);
                txtAge.setText(birthdate);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
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


    }
    private void Load() {
        SharedPreferences sp = getSharedPreferences("DATA", MODE_PRIVATE);
        measurement = sp.getBoolean("measurement", false);
    }
}

