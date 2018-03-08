package be.eaict.sleepqualitymeter;

import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            Fragment fragment;
                            switch (item.getItemId()) {
                                case R.id.navigation_home:
                                    fragment = new FragmentHome();
                                    loadFragment(fragment);
                                    break;

                                case R.id.navigation_records:
                                    fragment = new FragmentRecords();
                                    loadFragment(fragment);
                                    break;

                                case R.id.navigation_overall:
                                    fragment = new FragmentOverall();
                                    loadFragment(fragment);
                                    break;

                                case R.id.navigation_profile:
                                    fragment = new FragmentProfile();
                                    loadFragment(fragment);
                                    break;

                            }
            return true;
            }
                       });
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
