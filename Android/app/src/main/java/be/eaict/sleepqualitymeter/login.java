package be.eaict.sleepqualitymeter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class login extends AppCompatActivity {
    //DECLARATIONS
    Boolean rememberMe;
    CheckBox chbRemember;
    EditText editemail;
    EditText editpassword;

    //FireBase
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //BINDINGS
        TextView txtusername = findViewById(R.id.lgnTxtUsername);
        TextView txtpassword = findViewById(R.id.lgnTxtPassw);
        editemail = findViewById(R.id.lgnEditUsername);
        editpassword = findViewById(R.id.lgnEditPassword);
        Button btnLogin = findViewById(R.id.lgnBtnLogin);
        Button btnRegister = findViewById(R.id.lgnBtnRegister);
        chbRemember = findViewById(R.id.lgnChbRemember);

        mAuth = FirebaseAuth.getInstance();

        Load();

        txtpassword.setText("Password: ");
        txtusername.setText("Email: ");
        chbRemember.setText("Remember me");
        btnLogin.setText("Login");
        btnRegister.setText("Register");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Save();
                userLogin();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), register.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        user = mAuth.getCurrentUser();

        if(user != null){
            //Open Main Activity
        }
    }

    public void Save() {
        SharedPreferences sp = getSharedPreferences("DATA", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (chbRemember.isChecked()) {
            rememberMe = true;
            editor.putString("email", editemail.getText().toString());
            editor.putString("pass", editpassword.getText().toString());
        }
        else {
            rememberMe = false;
            editor.putString("email", "");
            editor.putString("pass",  "");
        }
        editor.putBoolean("checkbox", rememberMe);
        editor.apply();
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT);
    }
    public void Load() {
        SharedPreferences sp = getSharedPreferences("DATA", MODE_PRIVATE);
        String txtuser = sp.getString("email", null);
        String txtpass = sp.getString("pass", null);
        Boolean remembered = sp.getBoolean("checkbox", false);
        editemail.setText(txtuser);
        editpassword.setText(txtpass);
        chbRemember.setChecked(remembered);
    }
    public void userLogin(){
        String email = editemail.getText().toString().trim();
        String password = editpassword.getText().toString().trim();

        if(email.isEmpty()){
            editemail.setError("Email is required");
            editemail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editemail.setError("Please enter a valid email");
            editemail.requestFocus();
            return;
        }


        if(password.isEmpty()){
            editpassword.setError("Password is required");
            editpassword.requestFocus();
            return;
        }

        //progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //progressBar.setVisibility(View.GONE);

                if(task.isSuccessful()){
                    Intent intent = new Intent(login.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
