package be.eaict.sleepqualitymeter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LandingPage extends AppCompatActivity {

    TextView landingTxt;
    ProgressBar progressBar;

    private DatabaseReference mDatabaseData;
    private DatabaseReference mDatabaseDataTimes;
    private DatabaseReference mDatabaseUser;
    private FirebaseAuth mAuth;
    private User user;
    private LogicandCalc Calculator;

    private List<String> dates = new ArrayList<>();
    private List<Data> datas = new ArrayList<>();

    private int counter;

    public static List<DataRepo> Repository;
    private String email, userid, country, rawdata_weight, firstName, lastName, birthdate;
    static User DefUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        landingTxt = findViewById(R.id.lnd_loading);
        progressBar = findViewById(R.id.progressbar);

        Calculator = new LogicandCalc();
        landingTxt.setText("Loading");
        progressBar.setVisibility(View.VISIBLE);
        mAuth = FirebaseAuth.getInstance();
        email = mAuth.getCurrentUser().getEmail().toLowerCase();

        Repository = new ArrayList<>();

        counter = 0;

        GetUserData();
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

    private void FetchSleepData(final OnGetDataListener listener){
        mDatabaseDataTimes = FirebaseDatabase.getInstance().getReference("Data").child("-L75G-qGHaNEBznfXHVs").child(dates.get(counter));
        mDatabaseDataTimes.addListenerForSingleValueEvent(new ValueEventListener() {
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
                        DefUser = new User(userid, firstName, lastName, email, country, rawdata_weight, birthdate);
                    }
                }

                GetSleepDataDate();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                System.out.println("UserDataPullError : " + databaseError.getMessage());
            }
        });
    }

    private void GetSleepDataDate(){
        FetchSleepDataDate(new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    final String date = postSnapshot.getKey();
                    dates.add(date);
                }
                GetSleepData();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                System.out.println("SleepDataDatePullError : " + databaseError.getMessage());
            }
        });
    }

    private void GetSleepData(){
        FetchSleepData(new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                datas = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String time = snapshot.getKey();
                    float heartbeat = Float.parseFloat(snapshot.child("Heartbeat").getValue().toString());
                    float humidity = Float.parseFloat(snapshot.child("Humidity").getValue().toString());
                    float luminosity = Float.parseFloat(snapshot.child("Luminosity").getValue().toString());
                    float movement = Float.parseFloat(snapshot.child("Movement").getValue().toString());
                    float noise = Float.parseFloat(snapshot.child("Noise").getValue().toString());
                    float temperature = Float.parseFloat(snapshot.child("Temperature").getValue().toString());
                    datas.add(new Data(time, heartbeat, humidity, luminosity, movement, noise, temperature));
                }
                Repository.add(new DataRepo(dates.get(counter), datas));

                counter++;
                Check();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                System.out.println("SleepDataPullError : " + databaseError.getMessage());
            }
        });
    }

    private void Check(){
        if(counter >= dates.size()){
            Collections.reverse(Repository);
            Finished();
        }
        else{
            GetSleepData();
        }
    }

    private void Finished(){
        progressBar.setVisibility(View.GONE);
        Log.d("Kneusjes", "Finished loading");
        Repository = SortUserData(Repository);

        Intent intent = new Intent(LandingPage.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public interface OnGetDataListener {
        public void onSuccess(DataSnapshot dataSnapshot);
        public void onFailed(DatabaseError databaseError);
    }

    private List<DataRepo> SortUserData(List<DataRepo> Repo){
        for (int k = 0; k < Repo.size(); k++){
            //Sort all data using init timestamp in DataRepo name
            DataRepo org = Repo.get(k);
            List<Data> temp = new ArrayList<>();

            String Date = org.Date;
            String Start = Date.substring(11, 16);
            int StartInt = Calculator.timeStamptoInt(Start);

            int dataStart = 0;

            for (int j = 0; j < org.Repo.size(); j++){
                int TestInt = Calculator.timeStamptoInt(org.Repo.get(j).getTimeStamp());
                if (StartInt == TestInt){
                    dataStart = j;
                    j = org.Repo.size();

                }
            }

            if (dataStart != 0){
                temp.addAll(org.Repo.subList(dataStart, org.Repo.size()));
                temp.addAll(org.Repo.subList(0, dataStart));
                org.Repo = temp;
            }

            Repo.set(k, org);
        }
        return Repo;
    }
}
