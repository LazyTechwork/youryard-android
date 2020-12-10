package com.example.yard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoadingActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    String email, password, name, surname, city, street;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        email = Register.getDefaults("user-email", LoadingActivity.this);
        password = Register.getDefaults("user-password", LoadingActivity.this);
        name = Register.getDefaults("user-name", LoadingActivity.this);
        surname = Register.getDefaults("user-surname", LoadingActivity.this);
        city = Register.getDefaults("user-city", LoadingActivity.this);
        street = Register.getDefaults("user-street", LoadingActivity.this);

        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Register.setDefaults("visited", "visited", LoadingActivity.this);
                            int milliseconds_delayed = 1200;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(LoadingActivity.this, MainActivity.class));
                                    finish();
                                }
                            }, milliseconds_delayed);
                        } else {
                            Toast.makeText(LoadingActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoadingActivity.this, Login.class));
                        }
                    }
                });

    }
}