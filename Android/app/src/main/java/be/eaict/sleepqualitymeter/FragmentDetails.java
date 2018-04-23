package be.eaict.sleepqualitymeter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentDetails.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDetails extends Fragment {
    private OnFragmentInteractionListener mListener;
    List<DataRepo> repo;
    TextView header, txtMinBeat, txtMaxBeat, txtAvgBeat, txtAvgTemp, txtAvgHum, txtAvgLum, txtSleepTime;
    float maxBeat = 0, minBeat = 999, avgBeat = 0, avgTemp = 0, avgHum = 0, avgLum = 0;
    CharStyler charStyler;
    public FragmentDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FragmentDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDetails newInstance(String param1, String param2) {
        FragmentDetails fragment = new FragmentDetails();
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
        View view = inflater.inflate(R.layout.fragment_fragment_details, container, false);
        header = view.findViewById(R.id.fragDtlHeader);
        txtMaxBeat = view.findViewById(R.id.fragDtlMaxBeat);
        txtMinBeat = view.findViewById(R.id.fragDtlMinBeat);
        txtAvgBeat = view.findViewById(R.id.fragDtlAvgBeat);
        txtAvgTemp = view.findViewById(R.id.fragDtlAvgTemp);
        txtAvgHum = view.findViewById(R.id.fragDtlAvgHum);
        txtAvgLum = view.findViewById(R.id.fragDtlAvgLight);
        txtSleepTime = view.findViewById(R.id.fragDtlSleepTime);
        int Date = getActivity().getIntent().getExtras().getInt("date");
        repo = LandingPage.Repository;
        SleepLength sleepLength = new SleepLength(repo.get(Date).Repo.size());
        LogicandCalc logicandCalc = new LogicandCalc();
        String date = repo.get(Date).Date.substring(0,10);
        String time = repo.get(Date).Date.substring(11,16);
        header.setText("Date: " + date + System.getProperty("line.separator") + " Start time:  " + time);
        float tempTemp = 0, tempHum = 0, tempNoise = 0, tempBeat = 0, tempLum = 0;
        for(int i = 0; i < repo.get(Date).Repo.size(); i++) {
            if(repo.get(Date).Repo.get(i).getHeartbeat() > maxBeat) maxBeat = repo.get(Date).Repo.get(i).getHeartbeat();
            if(repo.get(Date).Repo.get(i).getHeartbeat() < minBeat) minBeat = repo.get(Date).Repo.get(i).getHeartbeat();
            tempTemp = tempTemp + repo.get(Date).Repo.get(i).getTemperature();
            tempHum = tempHum + repo.get(Date).Repo.get(i).getHumidity();
            tempNoise = tempNoise + repo.get(Date).Repo.get(i).getNoise();
            tempLum = tempLum + repo.get(Date).Repo.get(i).getLuminosity();
            tempBeat = tempBeat + repo.get(Date).Repo.get(i).getHeartbeat();
        }
        avgBeat = tempBeat / repo.get(Date).Repo.size();
        avgHum = tempHum / repo.get(Date).Repo.size();
        avgLum = tempLum / repo.get(Date).Repo.size();
        avgTemp = tempTemp / repo.get(Date).Repo.size();
        txtMaxBeat.setText(String.format("%.2f", maxBeat) + " bpm");
        txtAvgBeat.setText(String.format("%.2f", avgBeat) + " bpm");
        txtMinBeat.setText(String.format("%.2f", minBeat) + " bpm");
        txtAvgLum.setText(String.format("%.2f", avgLum) + " lx");
        txtAvgTemp.setText(String.format("%.2f", avgTemp) + " °C");
        txtAvgHum.setText(String.format("%.2f", avgHum) + " %");
        txtSleepTime.setText(logicandCalc.SleepLengthString(sleepLength));
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
