package be.eaict.sleepqualitymeter;

import android.icu.text.SymbolTable;

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

    private String userid = "0";
    private String email;
    private User user;
    private Data data;
    private String date;

    private List<Data> dataList = new ArrayList<>();
    private List<Double> movementList = new ArrayList<>();
    private Double[] movementArray;

    private int[] heartbeat;
    private int[] humidity;
    private int[] luminosity;
    private int[] movement;
    private int[] noise;
    private int[] temperature;

    public interface OnGetDataListener {
        public void onStart();
        public void onSuccess(DataSnapshot dataSnapshot);
        public void onFailed(DatabaseError databaseError);
    }

    public void FetchData(String userid, final OnGetDataListener listener){

        listener.onStart();

        databaseReference = FirebaseDatabase.getInstance().getReference("Data").child("-L75G-qGHaNEBznfXHVs").child("12-03-2018").child("0");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
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

                movementArray = new Double[movementList.size()];
                movementArray = movementList.toArray(movementArray);

                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The data read failed: " + databaseError.getCode());
                listener.onFailed(databaseError);
            }
        });
    }

    public void GetUserId(final OnGetDataListener listener){
        listener.onStart();



        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The userid read failed: " + databaseError.getCode());
                listener.onFailed(databaseError);
            }
        });
    }

    public void GetMovementLength(){

    }
}
