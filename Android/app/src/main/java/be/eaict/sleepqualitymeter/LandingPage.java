package be.eaict.sleepqualitymeter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LandingPage extends AppCompatActivity {

    TextView landingTxt;
    Button landingBtn;

    private DatabaseReference mDatabaseData;
    private DatabaseReference mDatabaseDataTimes;
    private DatabaseReference mDatabaseUser;
    private FirebaseAuth mAuth;
    private User user;

    private List<String> dates = new ArrayList<>();
    private List<Data> datas = new ArrayList<>();

    public static List<DataRepo> Repository = new ArrayList<>();
    private String email, userid, country, rawdata_weight, firstName, lastName, birthdate;
    static User DefUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        landingTxt = findViewById(R.id.lnd_loading);
        landingBtn = findViewById(R.id.lnd_btn);
        landingTxt.setText("Loading");
        mAuth = FirebaseAuth.getInstance();
        email = mAuth.getCurrentUser().getEmail().toLowerCase();


        GetUserData();

        //Button to continue after datafetch
        landingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingPage.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                for (int i = 0; i < Repository.size(); i++){
                    Log.d("Repo", String.valueOf(Repository.get(i).Repo.size()));
                    Log.d("RepoDate", Repository.get(i).Date);
                }
                Log.d("RepoStringLength", String.valueOf(dates.size()));
                startActivity(intent);
            }
        });


    }

    private void FetchUserData(final OnGetDataListener listener){
        mDatabaseUser = FirebaseDatabase.getInstance().getReference("User");
        mDatabaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }

    private void FetchSleepDataDate(final OnGetDataListener listener){
        mDatabaseData = FirebaseDatabase.getInstance().getReference("Data").child("-L75G-qGHaNEBznfXHVs");
        mDatabaseData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }

    private void GetUserData(){
        FetchUserData(new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                System.out.println("GetUserData OnSucces");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    user = snapshot.getValue(User.class);
                    if (user.getEmail().equals(email)) {
                        userid = user.getId();
                        email = user.getEmail();
                        firstName = user.getFirstname();
                        lastName = user.getLastname();
                        country = user.getCountry();
                        rawdata_weight = user.getWeight();
                        birthdate = user.getBirthdate();
                        Log.d("USERDATA", userid + " " + email + " " + firstName + " " + lastName + " " + country + " " + birthdate + " " + rawdata_weight);
                        DefUser = new User(userid, firstName, lastName, email, country, rawdata_weight, birthdate);
                    }
                }

                GetSleepDataDate();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Log.d("UserDataPullError", databaseError.getMessage());
                System.out.println("UserDataPullError : " + databaseError.getMessage());
            }
        });
    }

    private void GetSleepDataDate(){
        FetchSleepDataDate(new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                System.out.println("GetSleepDataDate OnSucces");

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    final String date = postSnapshot.getKey();
                    dates.add(date);
                    Log.d("Date", date);
                    //Date is hier de datum van de collectie opgeslagen als bv. string "12-03-2018"

                    mDatabaseDataTimes = FirebaseDatabase.getInstance().getReference("Data").child("-L75G-qGHaNEBznfXHVs").child(date);
                    mDatabaseDataTimes.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            System.out.println("GetSleepData OnSucces");

                            datas = new ArrayList<>();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String time = snapshot.getKey();
                                //Time is hier de tijdstip van de collectie, 22:04
                                float heartbeat = Float.parseFloat(snapshot.child("Heartbeat").getValue().toString());
                                float humidity = Float.parseFloat(snapshot.child("Humidity").getValue().toString());
                                float luminosity = Float.parseFloat(snapshot.child("Luminosity").getValue().toString());
                                float movement = Float.parseFloat(snapshot.child("Movement").getValue().toString());
                                float noise = Float.parseFloat(snapshot.child("Noise").getValue().toString());
                                float temperature = Float.parseFloat(snapshot.child("Temperature").getValue().toString());
                                Log.d("Heartbeat", Float.toString(heartbeat));
                                Log.d("Temp", Float.toString(temperature));
                                Log.d("Hum", Float.toString(humidity));
                                Log.d("Noise", Float.toString(noise));
                                Log.d("Lum", Float.toString(luminosity));
                                Log.d("Mov", Float.toString(movement));
                                datas.add(new Data(time, heartbeat, humidity, luminosity, movement, noise, temperature));
                            }
                            Log.d("RepoDataSize", String.valueOf(datas.size()));
                            Log.d("RepoDate", date);
                           /* for (int i = 0; i < dates.size(); i++) {
                                Repository.add(new DataRepo(dates.get(i), datas));
                            }*/
                            Repository.add(new DataRepo(date, datas));
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("SleepDataPullError", databaseError.getMessage());
                            System.out.println("SleepDataPullError : " + databaseError.getMessage());
                        }
                    });
                }

                Finished();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Log.d("SleepDataDatePullError", databaseError.getMessage());
                System.out.println("UserDataDatePullError : " + databaseError.getMessage());
            }
        });
    }

    private void Finished(){
        System.out.println("Finished");
        Intent intent = new Intent(LandingPage.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        for (int i = 0; i < Repository.size(); i++){
            Log.d("Repo", String.valueOf(Repository.get(i).Repo.size()));
            Log.d("RepoDate", Repository.get(i).Date);
        }
        Log.d("RepoStringLength", String.valueOf(dates.size()));
     //   startActivity(intent);
    }

    public interface OnGetDataListener {
        public void onSuccess(DataSnapshot dataSnapshot);
        public void onFailed(DatabaseError databaseError);
    }
}
