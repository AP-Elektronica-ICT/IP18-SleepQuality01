package be.eaict.sleepqualitymeter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.CountryPickerListener;
import android.app.DatePickerDialog;
import org.w3c.dom.Text;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import android.support.v4.app.DialogFragment;

/**
 * Created by CarlV on 2/13/2018.
 */

public class register extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    String country;
    TextView selectedCountry;
    CountryPicker picker;
    TextView ageSelector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextView title = findViewById(R.id.regTxtTitle);
        TextView txtemail = findViewById(R.id.regTxtEmail);
        TextView txtpass = findViewById(R.id.regTxtPass);
        EditText editEmail = findViewById(R.id.regEditEmail);
        EditText editPass = findViewById(R.id.regEditPass);
        TextView txtCountry = findViewById(R.id.regTxtCountry);
        TextView txtAge = findViewById(R.id.regTxtAge);
        selectedCountry = findViewById(R.id.regTxtSelectedCountry);
        ageSelector = findViewById(R.id.regTxtAgeSelector);
        country = "Select country";
        selectedCountry.setText(country);
        title.setText("Registration");
        txtemail.setText("Email address:");
        txtpass.setText("Password:");
        txtCountry.setText("Country:");
        txtAge.setText("Birth Date:");
        selectedCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker = CountryPicker.newInstance("Select Country");  // dialog title
                picker.setListener(new CountryPickerListener() {
                    @Override
                    public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                       country = name;
                        selectedCountry.setText(country);
                        picker.dismiss();
                    }
                });
                picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
            }
        });
        ageSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  datePicker(view);
           }
        });



    }
    public void datePicker(View view) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(), "Date");
            }
    private void setDate(final Calendar calendar) {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        ageSelector.setText(dateFormat.format(calendar.getTime()));
    }
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year,month,day);
        setDate(cal);
    }
    public static class DatePickerFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(),R.style.DatePickerDialogTheme,(DatePickerDialog.OnDateSetListener)getActivity(), year,month,day);
        }

    }
}
