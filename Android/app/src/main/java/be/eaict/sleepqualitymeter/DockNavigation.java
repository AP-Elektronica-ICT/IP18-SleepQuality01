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
        final Context finalcontext = context;

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                Intent intentHome = new Intent(finalcontext, HomeScreen.class);
                                finalcontext.startActivity(intentHome);
                                break;

                            case R.id.navigation_records:
                                break;

                            case R.id.navigation_overall:
                                Intent intentSummary = new Intent(finalcontext, SleepSummary.class);
                                finalcontext.startActivity(intentSummary);
                                break;

                            case R.id.navigation_profile:
                                break;

                        }
                        return true;
                    }
                });
    }
}
