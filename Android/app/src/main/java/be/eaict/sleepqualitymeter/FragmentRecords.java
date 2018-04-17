package be.eaict.sleepqualitymeter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;


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
    private DatabaseReference mDatabaseData;
    private DatabaseReference mDatabaseDataTotalTime;
    CustomAdapter customAdapter;
    private List<String> mDates = new ArrayList<>();
    private List<String> mMinutes = new ArrayList<>();

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
        View view = inflater.inflate(R.layout.fragment_fragment_records, container, false);
        final SwipeMenuListView listview = (SwipeMenuListView) view.findViewById(R.id.recListView);
        customAdapter = new CustomAdapter();
        listview.setAdapter(customAdapter);

        FetchData(view, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String date = postSnapshot.getKey();
                    mDates.add(date);
                    mDatabaseDataTotalTime = FirebaseDatabase.getInstance().getReference("Data").child("-L75G-qGHaNEBznfXHVs").child(date);
                    mDatabaseDataTotalTime.addListenerForSingleValueEvent(new ValueEventListener() {
                        int counter = 0;
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                counter +=2;
                                Log.d(TAG, Integer.toString(counter));
                            }
                            System.out.println("counter" + counter);
                            mMinutes.add(Integer.toString(counter));
                            customAdapter.notifyDataSetChanged();
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
                //customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                System.out.println("The Records read failed " + databaseError.getCode());
            }
        });

        ArrayList<String> templist = new ArrayList<>();
        for(int i = 0; i<=20; i++) {
            templist.add("Test123");
        }

      //  ArrayAdapter adapter2 = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_2, templist);
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setWidth(120);
                deleteItem.setTitle("Del");
                deleteItem.setTitleColor(Color.BLACK);
                deleteItem.setTitleSize(18);
                menu.addMenuItem(deleteItem);
            }
        };

        listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // delete
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                        builder.setTitle("Confirm");
                        builder.setMessage("Are you sure you want to delete this record?");

                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // Do nothing
                                dialog.dismiss();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                        break;
                    case 1:
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

// set creator
        listview.setMenuCreator(creator);
        return view;
    }
    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return LandingPage.Repository.size();
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
        public View getView(int i, View view, ViewGroup viewGroup) {
            final int current = i;
            final TextView listSleepTime;
            view = getLayoutInflater().inflate(R.layout.listview_records, null);
            final LinearLayout layout = view.findViewById(R.id.recListLayout);
            TextView listDate = view.findViewById(R.id.recListDate);
            listSleepTime = view.findViewById(R.id.recListTime);
            TextView listSummary = view.findViewById(R.id.recListSummary);
            listDate.setText(LandingPage.Repository.get(i).Date);
            listSleepTime.setText(Integer.toString(LandingPage.Repository.get(i).Repo.size() * 2) + "mins");
            listSummary.setText("Good!");
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Intent intent = new Intent(getContext(), DetailActivity.class);
                    intent.putExtra("date", current);
                    startActivity(intent);
                }
            });
            //    listSummary.setBackgroundColor();
            return view;
        }
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

    public void FetchData(View view, final OnGetDataListener listener){
        mDatabaseData = FirebaseDatabase.getInstance().getReference("Data").child("-L75G-qGHaNEBznfXHVs");
        mDatabaseData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }

    public interface OnGetDataListener {
        public void onSuccess(DataSnapshot dataSnapshot);
        public void onFailed(DatabaseError databaseError);
    }
}
