package be.eaict.sleepqualitymeter;

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
    private List<Integer> movementList = new ArrayList<>();
    private int[] movementArray;

    private int[] heartbeat;
    private int[] humidity;
    private int[] luminosity;
    private int[] movement;
    private int[] noise;
    private int[] temperature;

    public void FetchData(){
        GetUserId();
        System.out.println("Lol2");

        databaseReference = FirebaseDatabase.getInstance().getReference("Data").child(userid);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    System.out.println(snapshot.getChildren());
                    for (DataSnapshot snapshot2 : dataSnapshot.getChildren()){
                        data = snapshot2.getValue(Data.class);
                        dataList.add(data);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        for (Data x : dataList){
            System.out.println("Lol");
            System.out.println(x.getMovement());
            //movementList.add(x.getMovement());
        }
    }

    private void GetUserId(){
        mAuth = FirebaseAuth.getInstance();
        email = mAuth.getCurrentUser().getEmail();

        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    user = snapshot.getValue(User.class);

                    if(user.getEmail().equals(email)){
                        //System.out.println("WOOOOHOOO");
                        userid = user.getId();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    //get movement
}
