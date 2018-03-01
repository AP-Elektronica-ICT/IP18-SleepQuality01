package be.eaict.sleepqualitymeter;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by CarlV on 2/28/2018.
 */

public class records extends AppCompatActivity {
    DockNavigation dock;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        ListView listView = findViewById(R.id.recListv);

        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_records);
        dock = new DockNavigation(bottomNavigationView, getBaseContext());
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
                LinearLayout layout = findViewById(R.id.recListLayout);
                TextView listDate = view.findViewById(R.id.recListDate);
                TextView listSleepTime = view.findViewById(R.id.recListTime);
                TextView listSummary = view.findViewById(R.id.recListSummary);
                listDate.setText("12/01/1993");
                listSleepTime.setText("8:21");
                listSummary.setText("Good!");
                listDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_SHORT).show();
                    }
                });
                listSleepTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_SHORT).show();
                    }
                });
                listSummary.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_SHORT).show();
                    }
                });
                //    listSummary.setBackgroundColor();
                return view;
            }
        }
        public void intentDetail() {



        }

    }
