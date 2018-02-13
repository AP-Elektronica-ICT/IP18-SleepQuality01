package be.eaict.sleepqualitymeter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class login extends AppCompatActivity {
   //DECLARATIONS
   Boolean rememberMe;
   CheckBox chbRemember;
   EditText editusername;
   EditText editpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //BINDINGS
        Button goToSleepSummary = findViewById(R.id.sleepSummary);
       ImageView ImageHeader = findViewById(R.id.lgnImg);
       TextView txtusername = findViewById(R.id.lgnTxtUsername);
       TextView txtpassword = findViewById(R.id.lgnTxtPassw);
       editusername = findViewById(R.id.lgnEditUsername);
       editpassword = findViewById(R.id.lgnEditPassword);
       Button btnLogin = findViewById(R.id.lgnBtnLogin);
       Button btnRegister = findViewById(R.id.lgnBtnRegister);
       chbRemember = findViewById(R.id.lgnChbRemember);
        Load();
        txtpassword.setText("Password: ");
        txtusername.setText("Email: ");
        chbRemember.setText("Remember me");
        btnLogin.setText("Login");
        btnRegister.setText("Register");
        goToSleepSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), SleepSummary.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Save();
                /*If successful
                Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT);
                Intent intent = new Intent(getBaseContext(), SleepSummary.class);
                startActivity(intent);
                */
                /*
                Toast.makeText(this, "Incorrect login", Toast.LENGTH_SHORT);
                */
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), register.class);
                startActivity(intent);
            }
        });
        chbRemember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chbRemember.isChecked()) {
                    rememberMe = true;
                }
                else {
                    rememberMe = false;
                }
            }
        });
    }
    public void Save() {
        SharedPreferences sp = getSharedPreferences("DATA", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("email", editusername.getText().toString());
        editor.putString("pass", editusername.getText().toString());
        editor.putBoolean("checkbox", rememberMe);
        editor.apply();
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT);
    }
    public void Load() {
        SharedPreferences sp = getSharedPreferences("DATA", MODE_PRIVATE);
        String txtuser = sp.getString("email", null);
        String txtpass = sp.getString("pass", null);
        Boolean remembered = sp.getBoolean("checkbox", false);
        editusername.setText(txtuser);
        editpassword.setText(txtpass);
        chbRemember.setChecked(remembered);
    }
}
