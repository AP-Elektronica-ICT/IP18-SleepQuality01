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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentHeartRate.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentHeartRate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHeartRate extends Fragment {
    TextView hrtrate;
    LogicandCalc calculator;
    int Date;
    List<DataRepo> Repository = new ArrayList<>();
    List<Float> Heartrates;
    LineChart chart;

    private OnFragmentInteractionListener mListener;

    public FragmentHeartRate() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentHeartRate.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHeartRate newInstance(String param1, String param2) {
        FragmentHeartRate fragment = new FragmentHeartRate();
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
        View view = inflater.inflate(R.layout.fragment_fragment_heart_rate, container, false);
        hrtrate = view.findViewById(R.id.dtlHrtRate);
        chart = view.findViewById(R.id.heartRate);

        Repository = LandingPage.Repository;
        Date = getActivity().getIntent().getExtras().getInt("date");
        hrtrate.setText(Repository.get(Date).Date);
        calculator = new LogicandCalc();

        Heartrates = calculator.getDataType("heartbeat", Repository.get(Date).Repo);

        List<Entry> entries = new ArrayList<>();

        for (int i = 0; i < Heartrates.size(); i++){
            int time = i * 2;
            entries.add(new Entry(time, Heartrates.get(i)));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Heartrate");

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();

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
}
