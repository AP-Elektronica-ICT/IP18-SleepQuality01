package be.eaict.sleepqualitymeter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
    implements FragmentHome.OnFragmentInteractionListener, FragmentOverall.OnFragmentInteractionListener, FragmentProfile.OnFragmentInteractionListener, FragmentRecords.OnFragmentInteractionListener{

    BottomNavigationView bottomNavigationView;
    BottomBarAdapter pagerAdapter;
    NoSwipePager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        viewPager = findViewById(R.id.pager);

        viewPager.setPagingEnabled(false);

        pagerAdapter = new BottomBarAdapter(getSupportFragmentManager());
        pagerAdapter.addFragments(new FragmentHome());
        pagerAdapter.addFragments(new FragmentRecords());
        pagerAdapter.addFragments(new FragmentOverall());
        pagerAdapter.addFragments(new FragmentProfile());
        viewPager.setAdapter(pagerAdapter);

        viewPager.setCurrentItem(0);

        bottomNavigationView.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            viewPager.setCurrentItem(0);
                            break;

                        case R.id.navigation_records:
                            viewPager.setCurrentItem(1);
                            break;

                        case R.id.navigation_overall:
                            viewPager.setCurrentItem(2);
                            break;

                        case R.id.navigation_profile:
                            viewPager.setCurrentItem(3);
                            break;

                        }
                return true;
                }
            });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    protected void onDestroy() {
        super.onDestroy();

        finish();

    }
}
