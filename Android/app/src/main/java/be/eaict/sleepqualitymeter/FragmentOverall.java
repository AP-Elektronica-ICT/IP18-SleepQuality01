package be.eaict.sleepqualitymeter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentOverall.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentOverall#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentOverall extends Fragment {

    private String userid;

    private LineChart movement;
    private LogicandCalc calculator;
    private SleepLength LastNight;

    private DummyRepo dummyRepo = new DummyRepo();
    private SleepDataRepo sleepDataRepo = new SleepDataRepo();

    private OnFragmentInteractionListener mListener;
    private SleepDataRepo.OnGetDataListener dataListener;

    public FragmentOverall() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FragmentOverall.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentOverall newInstance(String param1, String param2) {
        FragmentOverall fragment = new FragmentOverall();
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
        View view =  inflater.inflate(R.layout.fragment_fragment_overall, container, false);

        boolean status = sleepDataRepo.GetStatus();
        if(status){
            SetLayout(view);
        }
        else {
            sleepDataRepo.SleepData(view);
        }

        return view;
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

    public View SetLayout(View view){

        calculator = new LogicandCalc();
        LastNight = new SleepLength(dummyRepo.length());

        movement = view.findViewById(R.id.movement);
        List<Entry> movementEntries = new ArrayList<>();
        for(int i = 0; i < dummyRepo.length(); i++){
            movementEntries.add(new Entry(i * 2, dummyRepo.dummyRepo[i]));
        }

        LineDataSet movementDataSet = new LineDataSet(movementEntries, "Movement");
        LineData movementData = new LineData(movementDataSet);
        movement.setData(movementData);
        movement.invalidate();

        TextView averageMovement = new TextView(getContext());
        averageMovement.setText("Your average movement this night was " + calculator.calculateAverage(dummyRepo.dummyRepo) + ".");

        TextView sleepTime = new TextView(getContext());
        sleepTime.setText("You slept " + calculator.SleepLengthString(LastNight));

        LinearLayout layout = view.findViewById(R.id.layout);
        layout.addView(averageMovement);
        layout.addView(sleepTime);

        return view;
    }
}
