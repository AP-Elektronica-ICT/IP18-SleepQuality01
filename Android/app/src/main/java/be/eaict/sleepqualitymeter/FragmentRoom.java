package be.eaict.sleepqualitymeter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentRoom.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentRoom#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRoom extends Fragment {
    List<DataRepo> Repository = new ArrayList<>();
    int Date;
    List<Float> Noise;
    List<Float> Luminosity;
    List<Float> Humidity;
    List<Float> Temperature;
    LineChart noisechart;
    LineChart luminositychart;
    LineChart humiditychart;
    LineChart temperaturechart;
    LogicandCalc calculator;
    CharStyler charStyler;

    private OnFragmentInteractionListener mListener;

    public FragmentRoom() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FragmentRoom.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRoom newInstance(String param1, String param2) {
        FragmentRoom fragment = new FragmentRoom();
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
        View view = inflater.inflate(R.layout.fragment_fragment_room, container, false);
        Repository = LandingPage.Repository;
        calculator = new LogicandCalc();
        Date = getActivity().getIntent().getExtras().getInt("date");
        noisechart = view.findViewById(R.id.noise);
        luminositychart = view.findViewById(R.id.luminosity);
        temperaturechart = view.findViewById(R.id.temperature);
        humiditychart = view.findViewById(R.id.humidity);

        Noise = calculator.getDataType("noise", Repository.get(Date).Repo);
        Luminosity = calculator.getDataType("luminosity", Repository.get(Date).Repo);
        Humidity = calculator.getDataType("humidity", Repository.get(Date).Repo);
        Temperature = calculator.getDataType("temperature", Repository.get(Date).Repo);

        List<Entry> entriesNoise = new ArrayList<>();
        List<Entry> entriesLuminosity = new ArrayList<>();
        List<Entry> entriesHumidity = new ArrayList<>();
        List<Entry> entriesTemperature = new ArrayList<>();

        for (int i = 0; i < Noise.size(); i++){
            int time = i * 2;
            entriesNoise.add(new Entry(time, Noise.get(i)));
            entriesLuminosity.add(new Entry(time, Luminosity.get(i)));
            entriesHumidity.add(new Entry(time, Humidity.get(i)));
            entriesTemperature.add(new Entry(time, Temperature.get(i)));
        }

        LineDataSet dataSetNoise = new LineDataSet(entriesNoise, "Noise");
        LineDataSet dataSetLuminosity = new LineDataSet(entriesLuminosity, "Luminosity");
        LineDataSet dataSetHumidity = new LineDataSet(entriesHumidity, "Humidity");
        LineDataSet dataSetTemperature = new LineDataSet(entriesTemperature, "Temperature");

        charStyler.SetChartColor(dataSetNoise);
        charStyler.SetChartColor(dataSetLuminosity);
        charStyler.SetChartColor(dataSetHumidity);
        charStyler.SetChartColor(dataSetTemperature);

        LineData lineDataNoise = new LineData(dataSetNoise);
        noisechart.setData(lineDataNoise);
        charStyler.SetChartLegendColor(noisechart);
        noisechart.invalidate();

        LineData lineDataLuminosity = new LineData(dataSetLuminosity);
        luminositychart.setData(lineDataLuminosity);
        charStyler.SetChartLegendColor(luminositychart);
        luminositychart.invalidate();

        LineData lineDataHumidity = new LineData(dataSetHumidity);
        humiditychart.setData(lineDataHumidity);
        charStyler.SetChartLegendColor(humiditychart);
        humiditychart.invalidate();

        LineData lineDataTemperature = new LineData(dataSetTemperature);
        temperaturechart.setData(lineDataTemperature);
        charStyler.SetChartLegendColor(temperaturechart);
        temperaturechart.invalidate();

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
