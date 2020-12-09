package com.example.yard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.yard.fragments_registration.RegisterFragment1;
import com.example.yard.fragments_registration.RegisterFragment2;
import com.example.yard.fragments_registration.RegisterFragment3;

public class Register extends AppCompatActivity {

    public static Button mBack, mNext;
    private ProgressBar mProgressBar;
    private Integer flag;
    private FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mProgressBar = findViewById(R.id.progressBar);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, new RegisterFragment1());
        fragmentTransaction.commit();
        flag = 0;

        mBack = findViewById(R.id.backButton);
        mNext = findViewById(R.id.nextButton);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag==0){
                    startActivity(new Intent(Register.this, Login.class));
                } else{
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    if (flag==1){
                        fragmentTransaction.replace(R.id.container, new RegisterFragment1());
                        flag = 0;
                    } else{
                        fragmentTransaction.replace(R.id.container, new RegisterFragment2());
                        flag = 1;
                        mNext.setText("Далее →");
                    }
                    mProgressBar.setProgress(flag+1);
                    fragmentTransaction.commit();
                }
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                if (flag==0){
                    fragmentTransaction.replace(R.id.container, new RegisterFragment2());
                    flag = 1;
                } else{
                    if (flag==1){
                        fragmentTransaction.replace(R.id.container, new RegisterFragment3());
                        flag = 2;
                        mNext.setText("Завершить");
                    } else{
                        if (flag==2){
                            Toast.makeText(Register.this, "", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                mProgressBar.setProgress(flag+1);
                fragmentTransaction.commit();
            }
        });

    }

    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

}