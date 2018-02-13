package be.eaict.sleepqualitymeter;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.CountryPickerListener;

import org.w3c.dom.Text;

/**
 * Created by CarlV on 2/13/2018.
 */

public class register extends AppCompatActivity {
    String country;
    TextView selectedCountry;
    CountryPicker picker;
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
        selectedCountry = findViewById(R.id.regTxtSelectedCountry);
        Button btnCountrySelector = findViewById(R.id.regBtnCountry);
        country = "";
        selectedCountry.setText(country);
        title.setText("Registration");
        txtemail.setText("Email address:");
        txtpass.setText("Password:");
        txtCountry.setText("Country:");
        btnCountrySelector.setText("Select");
        btnCountrySelector.setOnClickListener(new View.OnClickListener() {
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




    }
}
