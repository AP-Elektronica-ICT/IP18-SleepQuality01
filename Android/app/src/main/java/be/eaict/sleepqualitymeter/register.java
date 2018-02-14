package be.eaict.sleepqualitymeter;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.CountryPickerListener;

import org.w3c.dom.Text;

/**
 * Created by CarlV on 2/13/2018.
 */

public class register extends AppCompatActivity {
    //Initialization
    String country;
    TextView selectedCountry;
    CountryPicker picker;
    EditText editEmail, editPass, editName;

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
        selectedCountry = findViewById(R.id.regTxtSelectedCountry);
        Button btnCountrySelector = findViewById(R.id.regBtnCountry);

        mAuth = FirebaseAuth.getInstance();

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

    private void RegisterUser(){
        //Username ??
        final String name = editName.getText().toString().trim();
        final String email = editEmail.getText().toString().trim();
        String password = editPass.getText().toString().trim();

        if(name.isEmpty()){
            editName.setError("Please enter a name");
            editName.requestFocus();
            return;
        }

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
}
