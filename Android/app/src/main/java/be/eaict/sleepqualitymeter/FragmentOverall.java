package be.eaict.sleepqualitymeter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;

import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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

    private BarChart SleeplengthChart;
    private BarChart HeartrateChart;
    private BarChart MovementChart;

    private List<DataRepo> Repository = LandingPage.Repository;

    private OnFragmentInteractionListener mListener;

    CharStyler charStyler;

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

        charStyler = new CharStyler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fragment_overall, container, false);
        calculator = new LogicandCalc();
        float averageSleep = 0;

        List<Integer> Sleeprepo = new ArrayList<>();
        List<Float> averageHeartraterepo = new ArrayList<>();
        List<Float> averageMovementrepo = new ArrayList<>();

        HeartrateChart = view.findViewById(R.id.Heartchart);
        MovementChart = view.findViewById(R.id.movementchart);
        SleeplengthChart = view.findViewById(R.id.Lengthchart);

        if (Repository.size() < 7){
            for (int i = 0; i < Repository.size(); i++) {
                float TotalMovement = 0;
                float TotalHeartrate = 0;
                averageSleep += Repository.get(i).Repo.size()*2;
                int sleepLength = Repository.get(i).Repo.size()*2;
                for (int j = 0; j < Repository.get(i).Repo.size(); j++) {
                    TotalHeartrate += Repository.get(i).Repo.get(j).getHeartbeat();
                    TotalMovement += Repository.get(i).Repo.get(j).getMovement();
                }
                averageHeartraterepo.add(TotalHeartrate/Repository.get(i).Repo.size());
                averageMovementrepo.add(TotalMovement/Repository.get(i).Repo.size());
                Sleeprepo.add(sleepLength);
            }
        } else{
            for (int i = 0; i < 7; i++) {
                averageSleep += Repository.get(i).Repo.size()*2;
                float TotalMovement = 0;
                float TotalHeartrate = 0;
                int sleepLength = Repository.get(i).Repo.size()*2;
                for (int j = 0; j < Repository.get(i).Repo.size(); j++) {
                    TotalHeartrate += Repository.get(i).Repo.get(j).getHeartbeat();
                    TotalMovement += Repository.get(i).Repo.get(j).getMovement();
                }
                averageHeartraterepo.add(TotalHeartrate/Repository.get(i).Repo.size());
                averageMovementrepo.add(TotalMovement/Repository.get(i).Repo.size());
                Sleeprepo.add(sleepLength);
            }

        }

        List<BarEntry> SleepEntries = new ArrayList<>();
        List<BarEntry> MovementEntries = new ArrayList<>();
        List<BarEntry> HeartEntries = new ArrayList<>();

        for (int i = 0; i < Sleeprepo.size(); i++) {
            SleepEntries.add(new BarEntry(i, Sleeprepo.get(i)));
            MovementEntries.add(new BarEntry(i, averageMovementrepo.get(i)));
            HeartEntries.add(new BarEntry(i, averageHeartraterepo.get(i)));
        }

        BarDataSet MovementSet = new BarDataSet(MovementEntries, "Movement");
        BarDataSet SleepSet = new BarDataSet(SleepEntries, "Sleep");
        BarDataSet HeartSet = new BarDataSet(HeartEntries, "Heartrate");

        charStyler.SetBarChartColor(MovementSet);
        charStyler.SetBarChartColor(SleepSet);
        charStyler.SetBarChartColor(HeartSet);

        BarData MovementData = new BarData(MovementSet);
        BarData SleepData = new BarData(SleepSet);
        BarData HeartData = new BarData(HeartSet);

        MovementChart.setData(MovementData);
        SleeplengthChart.setData(SleepData);
        HeartrateChart.setData(HeartData);

        charStyler.SetBarChartLegendColor(MovementChart);
        charStyler.SetBarChartLegendColor(SleeplengthChart);
        charStyler.SetBarChartLegendColor(HeartrateChart);

        MovementChart.invalidate();
        SleeplengthChart.invalidate();
        HeartrateChart.invalidate();

        averageSleep = averageSleep/7;
        double averageHeartrate = calculator.getAverageOfWeek("heartbeat", Repository);
        double averageMovement = calculator.getAverageOfWeek("movement", Repository);

        TextView HeartRateTxt = view.findViewById(R.id.averageHeartrate);
        TextView MovementTxt = view.findViewById(R.id.averageMovement);
        TextView AverageSleepTxt = view.findViewById(R.id.averageSleepLength);

        int averageheart = (int) averageHeartrate;
        int averagemovement = (int) averageMovement;
        int averagesleep = (int) averageSleep;

        HeartRateTxt.setText("Your average heartrate is " + averageheart + " bpm.");
        MovementTxt.setText("Your average movement is " + averagemovement);
        AverageSleepTxt.setText("Your average sleeplength is " + averagesleep + " mins.");



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
