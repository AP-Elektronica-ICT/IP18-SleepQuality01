package be.eaict.sleepqualitymeter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by jonas on 01/03/2018.
 */

public class DockNavigation {

    public DockNavigation(BottomNavigationView bottomNavigationView, Context context){
        final Context finalContext = context;

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                Intent intentHome = new Intent(finalContext, HomeScreen.class);
                                finalContext.startActivity(intentHome);
                                break;

                            case R.id.navigation_records:
                                break;

                            case R.id.navigation_overall:
                                Intent intentSummary = new Intent(finalContext, SleepSummary.class);
                                finalContext.startActivity(intentSummary);
                                break;

                            case R.id.navigation_profile:
                                Intent intentProfile = new Intent(finalContext, profile.class);
                                finalContext.startActivity(intentProfile);
                                break;

                        }
                        return true;
                    }
                });
    }
}
