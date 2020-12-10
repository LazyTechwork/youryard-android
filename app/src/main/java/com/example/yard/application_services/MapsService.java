package com.example.yard.application_services;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yard.R;
import com.example.yard.data.DataObject;
import com.example.yard.data.Poll;
import com.example.yard.utils.JSONInteractor;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MapsService extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_service);

        Button mButton = findViewById(R.id.nextButton);

        Context context = this;

        mButton.setOnClickListener((View.OnClickListener) v -> {
            //CREATE ALERT
            LayoutInflater inflater = getLayoutInflater();
            AlertDialog.Builder builder = new AlertDialog.Builder(MapsService.this);
            View view = getLayoutInflater().inflate(R.layout.alert_create_statement, null);
            builder.setView(view);

            EditText formTitle = (EditText) view.findViewById(R.id.title);
            EditText formText = (EditText) view.findViewById(R.id.message);
            EditText formAddress = (EditText) view.findViewById(R.id.address);

            builder.setPositiveButton(android.R.string.yes, (dialog, which) -> {
                JSONInteractor jsonInteractor = new JSONInteractor(context, "data.json");
                try {
                    DataObject data = jsonInteractor.readJSON(DataObject.class);
                    ArrayList<Poll> polls = data.getPolls();
                } catch (FileNotFoundException e) {
                    Log.e("Maps Service", "Error occurred while reading/writing data", e);
                }
            });

            builder.setNegativeButton(android.R.string.no, null).show();
        });
    }
}