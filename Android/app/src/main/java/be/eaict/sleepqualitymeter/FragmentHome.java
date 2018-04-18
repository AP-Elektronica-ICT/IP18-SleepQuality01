package be.eaict.sleepqualitymeter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentHome.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHome extends Fragment {


    LogicandCalc calculator = new LogicandCalc();
    SleepLength sleepLength;
    User user;
    TextView txtTimeOfDay;
    List<DataRepo> Repository;

    private OnFragmentInteractionListener mListener;

    public FragmentHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FragmentHome.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHome newInstance(String param1, String param2) {
        FragmentHome fragment = new FragmentHome();
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
        View view = inflater.inflate(R.layout.fragment_fragment_home, container, false);
        txtTimeOfDay = view.findViewById(R.id.fragTxtTimeOfDAy);
        Repository = LandingPage.Repository;
        user = LandingPage.DefUser;
        txtTimeOfDay.setText(timeOfDay());
        TextView sleepTime = view.findViewById(R.id.sleepTime);
        sleepLength = new SleepLength(Repository.get(Repository.size() - 1).Repo.size());
        sleepTime.setText(calculator.SleepLengthString(sleepLength));

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
    public String timeOfDay() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        String msg = "";
        if(timeOfDay >= 6 && timeOfDay < 12){
            msg = "Hope you had a good sleep, " + user.getFirstname() + " " + user.getLastname();
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            msg = "Good Afternoon " + user.getFirstname() + " " + user.getLastname();
        }else if(timeOfDay >= 16 && timeOfDay < 21){
           msg = "Good Evening " + user.getFirstname() + " " + user.getLastname();
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            msg = "Bedtime, " + user.getFirstname() + " " + user.getLastname() + "?";
        }
        else if(timeOfDay >= 0 && timeOfDay < 6) {
            msg = "Good Night " + user.getFirstname() + " " + user.getLastname();
        }
        return msg;
    }
}
