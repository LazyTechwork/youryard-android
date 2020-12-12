package com.example.yard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.yard.fragments_registration.RegisterFragment1;

public class Register extends AppCompatActivity {

    public static Button mBack, mNext;
    public static ProgressBar mProgressBar;
    private FragmentTransaction fragmentTransaction;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mProgressBar = findViewById(R.id.progressBar);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, new RegisterFragment1());
        fragmentTransaction.commit();

        mBack = findViewById(R.id.backButton);
        mNext = findViewById(R.id.nextButton);

    }

}