package be.eaict.sleepqualitymeter;

import android.icu.text.SymbolTable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sande on 15/03/2018.
 */

public class SleepDataRepo {
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    private boolean status;

    private View view;

    private String userid = "0";
    private String email;
    private User user;
    private Data data;
    private String date;

    private List<Data> dataList = new ArrayList<>();
    private List<Float> movementList = new ArrayList<>();
    public Float[] movementArray;

    /*private int[] heartbeat;
    private int[] humidity;
    private int[] luminosity;
    private int[] movement;
    private int[] noise;
    private int[] temperature;*/

    public interface OnGetDataListener {
        public void onSuccess(DataSnapshot dataSnapshot);
        public void onFailed(DatabaseError databaseError);
    }

    public void SleepData(View view){
        this.view = view;
        movementArray = null;
        GetUserId();
    }

    private void FetchUserId(final OnGetDataListener listener){
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
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

    private void FetchData(String userid, final OnGetDataListener listener){

        databaseReference = FirebaseDatabase.getInstance().getReference("Data").child("-L75G-qGHaNEBznfXHVs").child("12-03-2018").child("0");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
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

    private void GetUserId(){
        FetchUserId(new SleepDataRepo.OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String email = mAuth.getCurrentUser().getEmail();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);

                    if(user.getEmail().equals(email)){
                        System.out.println("UserId Found!");
                        userid = user.getId();
                    }
                }

                GetData();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                System.out.println("The data read failed: " + databaseError.getCode());
            }
        });
    }

    private void GetData( ){
        FetchData(userid, new SleepDataRepo.OnGetDataListener() {

            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    data = snapshot.getValue(Data.class);
                    dataList.add(data);

                    /*for (DataSnapshot snapshot2 : dataSnapshot.getChildren()){
                        data = snapshot2.getValue(Data.class);
                        dataList.add(data);
                    }*/
                }

                for (Data x : dataList){
                    System.out.println(x.getMovement());
                    movementList.add(x.getMovement());
                }

                movementArray = new Float[movementList.size()];
                movementArray = movementList.toArray(movementArray);

                status = true;

                System.out.println(status);

                FragmentOverall.SetLayout(view);
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                System.out.println("The userid read failed: " + databaseError.getCode());
            }
        });
    }

    public boolean GetStatus(){ return status; }
}
