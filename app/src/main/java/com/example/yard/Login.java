package com.example.yard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    TextView toRegistration;
    Button mLogin;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String email, password, region, userID;
    EditText mTextLogin, mTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

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
                        if (task.isSuccessful()) {
                            Register.setDefaults("visited", "visited", Login.this);
                            startActivity(new Intent(Login.this, MainActivity.class));
                        } else {
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