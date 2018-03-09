package be.eaict.sleepqualitymeter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentProfile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProfile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String firstName, lastName, email, country, avgSleepTime, userid, birthdate, weight, rawdata_weight;
    Boolean measurement;
    TextView txtName, txtAge, txtEmail, txtNationality, txtWeight, txtAvgSleepTime;
    User user;

    //Firebase
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    private OnFragmentInteractionListener mListener;

    public FragmentProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentProfile newInstance(String param1, String param2) {
        FragmentProfile fragment = new FragmentProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fragment_profile, container, false);

        Load();

        txtAge = view.findViewById(R.id.profTxtAge);
        txtEmail = view.findViewById(R.id.profTxtEmail);
        txtName = view.findViewById(R.id.profTxtName);
        txtNationality = view.findViewById(R.id.profTxtNationality);
        txtWeight = view.findViewById(R.id.profTxtWeight);
        txtAvgSleepTime = view.findViewById(R.id.profTxtAvgSleepTime);
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
                if(measurement ==true) {
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

        Button btnSettings = view.findViewById(R.id.profBtnSettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), settings.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void Load() {
        SharedPreferences sp = getContext().getSharedPreferences("DATA", MODE_PRIVATE);
        measurement = sp.getBoolean("measurement", false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
