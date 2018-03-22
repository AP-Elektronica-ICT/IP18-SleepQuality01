package be.eaict.sleepqualitymeter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentRecords.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentRecords#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRecords extends Fragment {

    private OnFragmentInteractionListener mListener;

    public FragmentRecords() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FragmentRecords.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRecords newInstance(String param1, String param2) {
        FragmentRecords fragment = new FragmentRecords();
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
        View view = inflater.inflate(R.layout.fragment_fragment_records, container, false);

        ListView listView = view.findViewById(R.id.recListv);

        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

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


    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final int current = i;
            view = getLayoutInflater().inflate(R.layout.listview_records, null);
            LinearLayout layout = view.findViewById(R.id.recListLayout);
            TextView listDate = view.findViewById(R.id.recListDate);
            TextView listSleepTime = view.findViewById(R.id.recListTime);
            TextView listSummary = view.findViewById(R.id.recListSummary);
            listDate.setText("12/01/1993");
            listSleepTime.setText("8:21");
            listSummary.setText("Good!");
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), DetailActivity.class);
                    startActivity(intent);
                }
            });
            //    listSummary.setBackgroundColor();
            return view;
        }
    }
}
