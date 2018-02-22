package be.eaict.sleepqualitymeter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
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

public class register extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    //Initialization
    String country;
    TextView selectedCountry;
    CountryPicker picker;
    EditText editEmail, editPass, editName, editWeight;
    TextView ageSelector;
    Integer weight;
    //FireBase
    FirebaseAuth mAuth;
    //Database code??
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView title = findViewById(R.id.regTxtTitle);
        TextView txtemail = findViewById(R.id.regTxtEmail);
        TextView txtpass = findViewById(R.id.regTxtPass);
        editEmail = findViewById(R.id.regEditEmail);
        editPass = findViewById(R.id.regEditPass);
        TextView txtCountry = findViewById(R.id.regTxtCountry);
        TextView txtAge = findViewById(R.id.regTxtAge);
        selectedCountry = findViewById(R.id.regTxtSelectedCountry);
        ageSelector = findViewById(R.id.regTxtAgeSelector);
        TextView txtWeight = findViewById(R.id.regTxtWeight);
        Button register = findViewById(R.id.regBtnRegister);
        final Switch regswitch = findViewById(R.id.regSwitch);
        editWeight = findViewById(R.id.regEditWeight);
        country = "Select country";
        mAuth = FirebaseAuth.getInstance();
        selectedCountry.setText(country);
        title.setText("Registration");
        txtemail.setText("Email address:");
        txtpass.setText("Password:");
        txtCountry.setText("Country:");
        txtAge.setText("Birth Date:");
        txtWeight.setText("Weight:");
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
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterUser();
            }
        });
        regswitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = editWeight.getText().toString();
                int finalvalue = Integer.parseInt(value);
                if(regswitch.isChecked()) {
                    Double lbstokg = finalvalue *  0.45359237;
                    weight = lbstokg.intValue();
                    Log.d("weight", weight.toString());
                }
                else {
                    weight = finalvalue;
                }
            }
        });
    }

    private void RegisterUser(){
        final String email = editEmail.getText().toString().trim();
        String password = editPass.getText().toString().trim();

        if(email.isEmpty()){
            editEmail.setError("Email is required");
            editEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Please enter a valid email");
            editEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editPass.setError("Password is required");
            editPass.requestFocus();
            return;
        }

        if(password.length() < 6){
            editPass.setError("Minimum lenght of password should be 6");
            editPass.requestFocus();
            return;
        }

        //progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //progressBar.setVisibility(View.GONE);

                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "User Registered Succesful", Toast.LENGTH_SHORT).show();
                    //Volgende activity
                    SharedPreferences sp = getSharedPreferences("DATA", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("email", editEmail.getText().toString());
                    editor.putString("pass", editPass.getText().toString());
                    editor.apply();
                    Intent intent = new Intent(getBaseContext(), login.class);
                    startActivity(intent);
                }
                else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(), "This email is already in use", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
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
            return new DatePickerDialog(getActivity(), R.style.DatePickerDialogTheme, (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
        }
    }
    }
