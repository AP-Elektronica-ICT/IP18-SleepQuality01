package be.eaict.sleepqualitymeter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;

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
    private LogicandCalc calculator;
    private SleepLength LastNight;

    private List<Float> HeartRateData;
    private List<Float> MovementData;
    private List<Integer> SleepLengthData;

    private List<DataRepo> Repository = LandingPage.Repository;

    private OnFragmentInteractionListener mListener;

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
        calculator = new LogicandCalc();
        double averageSleep = 0;

        if (Repository.size() < 7){
            for (int i = 0; i < Repository.size(); i++) {
                averageSleep += Repository.get(i).Repo.size()*2;
            }
        } else{
            for (int i = 0; i < 7; i++) {
                averageSleep += Repository.get(i).Repo.size()*2;
            }
        }

        averageSleep = averageSleep/7;
        double averageHeartrate = calculator.getAverageOfWeek("heartbeat", Repository);
        double averageMovement = calculator.getAverageOfWeek("movement", Repository);

        TextView HeartRateTxt = view.findViewById(R.id.averageHeartrate);
        TextView MovementTxt = view.findViewById(R.id.averageMovement);
        TextView AverageSleepTxt = view.findViewById(R.id.averageSleepLength);

        HeartRateTxt.setText("Your average heartrate is " + averageHeartrate);
        MovementTxt.setText("Your average movement is " + averageMovement);
        AverageSleepTxt.setText("Your average sleeplength is " + averageSleep);

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

    public static View SetLayout(View view){



        return view;
    }
}
