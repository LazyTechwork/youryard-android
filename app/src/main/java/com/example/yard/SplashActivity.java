package com.example.yard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int milliseconds_delayed = 1200;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Register.getDefaults("visited", SplashActivity.this)==null){
                    startActivity(new Intent(SplashActivity.this, Login.class));
                } else{
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
                finish();
            }
        }, milliseconds_delayed);

    }
}