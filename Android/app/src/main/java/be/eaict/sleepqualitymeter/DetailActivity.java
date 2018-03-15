package be.eaict.sleepqualitymeter;

import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity
    implements FragmentDetails.OnFragmentInteractionListener, FragmentHeartRate.OnFragmentInteractionListener,
        FragmentMovement.OnFragmentInteractionListener, FragmentRoom.OnFragmentInteractionListener{


    BottomBarAdapter pagerAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        viewPager = findViewById(R.id.pager);

        pagerAdapter = new BottomBarAdapter(getSupportFragmentManager());
        pagerAdapter.addFragments(new FragmentDetails());
        pagerAdapter.addFragments(new FragmentHeartRate());
        pagerAdapter.addFragments(new FragmentMovement());
        pagerAdapter.addFragments(new FragmentRoom());
        viewPager.setAdapter(pagerAdapter);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
