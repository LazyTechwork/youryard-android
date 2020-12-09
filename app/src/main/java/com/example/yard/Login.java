package com.example.yard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    TextView toRegistration;
    Button mLogin;
    FirebaseAuth mAuth;
    String email, password;
    EditText mTextLogin, mTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mLogin = findViewById(R.id.loginButton);
        mTextLogin = findViewById(R.id.editTextLogin);
        mTextPassword = findViewById(R.id.editTextPassword);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mTextLogin.getText().toString().trim();
                password = mTextPassword.getText().toString().trim();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(Login.this, MainActivity.class));
                        } else{
                            Toast.makeText(Login.this, "Try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        toRegistration = findViewById(R.id.textToRegistration);
        toRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

    }
}