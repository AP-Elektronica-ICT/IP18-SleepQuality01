package be.eaict.sleepqualitymeter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mukesh.countrypicker.Country;

import java.util.List;

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

    String firstName, lastName, email, country, avgSleepTime, userid, birthdate, weight, rawdata_weight;
    Boolean measurement;
    TextView txtName, txtAge, txtEmail, txtNationality, txtWeight, txtAvgSleepTime;
    User user;
    ImageView imgCountry;
    List<DataRepo> Repository;

    private OnFragmentInteractionListener mListener;

    public FragmentProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentProfile newInstance(String param1, String param2) {
        FragmentProfile fragment = new FragmentProfile();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_profile, container, false);
        user = LandingPage.DefUser;
        Repository = LandingPage.Repository;
        Load();
        txtAge = view.findViewById(R.id.profTxtAge);
        txtEmail = view.findViewById(R.id.profTxtEmail);
        txtName = view.findViewById(R.id.profTxtName);
        txtNationality = view.findViewById(R.id.profTxtNationality);
        txtWeight = view.findViewById(R.id.profTxtWeight);
        txtAvgSleepTime = view.findViewById(R.id.profTxtAvgSleepTime);
        int totaltime = 0;
        for (int i = 0; i < Repository.size(); i++) {
            totaltime = totaltime + Repository.get(i).Repo.size() * 2;
        }
        SleepLength sleepLength;
        LogicandCalc logicandCalc = new LogicandCalc();
        sleepLength = new SleepLength(totaltime / Repository.size());
        txtAvgSleepTime.setText(logicandCalc.SleepLengthString(sleepLength));
        firstName = user.getFirstname();
        lastName = user.getLastname();
        email = user.getEmail();
        birthdate = user.getBirthdate();
        country = user.getCountry();
        rawdata_weight = user.getWeight();
        imgCountry = view.findViewById(R.id.profImgCountry);
        //Convert KG to pound
        if (measurement == true) {
            Double lbstokg = Double.parseDouble(rawdata_weight) / 0.45359237;
            int t = lbstokg.intValue();
            weight = Integer.toString(t);
            txtWeight.setText(weight + " lbs");
        } else {
            weight = rawdata_weight;
            txtWeight.setText(weight + " kg");
        }

        txtName.setText(firstName + " " + lastName + "'s profile");
        txtEmail.setText(email);
        txtNationality.setText(country);
        Country tempcountry = Country.getCountryByName(country);
        imgCountry.setImageResource(tempcountry.getFlag());
        txtAge.setText(birthdate);

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
