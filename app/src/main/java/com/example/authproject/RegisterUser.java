package com.example.authproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private TextView banner, registerUser;
    private EditText etFullName, etAge, etEmail, etPassword;
    private ProgressBar progressbar;
    private Object View;
    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.btReg);
        registerUser.setOnClickListener(this);

        etFullName = (EditText) findViewById(R.id.etFullName);
        etAge = (EditText) findViewById(R.id.etAge);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        dbref = FirebaseDatabase.getInstance().getReference("User");
        progressbar = (ProgressBar) findViewById(R.id.progressBar);



    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.btReg:
                btReg();
                break;
        }
    }

    private void btReg() {
        String fName = etFullName.getText().toString().trim();
        String age = etAge.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String pswd = etPassword.getText().toString().trim();

        if(fName.isEmpty()){
            etFullName.setError("Full Name is required");
            etFullName.requestFocus();
            return;
        }
        if(age.isEmpty()){
            etAge.setError("Age is required");
            etAge.requestFocus();
            return;
        }
        if(email.isEmpty()){
            etEmail.setError("Full Name is required");
            etEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Please provide valid Email");
            etEmail.requestFocus();
            return;
        }
        if(pswd.isEmpty()){
            etPassword.setError("Full Name is required");
            etPassword.requestFocus();
            return;
        }
        if(pswd.length()<6){
            etPassword.setError("Min pswd length should be 6 chars!");
            etPassword.requestFocus();
            return;
        }
        progressbar.setVisibility(android.view.View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, pswd).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String vid = dbref.push().getKey();
                    User user = new User(fName, age, email, vid);

                            dbref.child(vid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterUser.this, "User has been registered successfully!!", Toast.LENGTH_LONG).show();
                                progressbar.setVisibility(android.view.View.GONE);
                            }
                            else{
                                Toast.makeText(RegisterUser.this, "Failed to register", Toast.LENGTH_LONG).show();
                                progressbar.setVisibility(android.view.View.GONE);

                            }
                        }
                    });
                }
                else{
                    Toast.makeText(RegisterUser.this, "Failed to register. Try Again!", Toast.LENGTH_LONG).show();
                    progressbar.setVisibility(android.view.View.GONE);

                }
            }
        });
    }
}