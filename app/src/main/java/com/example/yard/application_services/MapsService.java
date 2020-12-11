package com.example.yard.application_services;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yard.R;
import com.example.yard.data.DataObject;
import com.example.yard.data.Poll;
import com.example.yard.utils.JSONInteractor;

import java.io.IOException;
import java.util.ArrayList;

public class MapsService extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_service);

        Button mButton = findViewById(R.id.nextButton);

        Context context = this;

        mButton.setOnClickListener(v -> {
            //CREATE ALERT
            AlertDialog.Builder builder = new AlertDialog.Builder(MapsService.this);
            View view = getLayoutInflater().inflate(R.layout.alert_create_statement, null);
            builder.setView(view);

            EditText formTitle = view.findViewById(R.id.title);
            EditText formText = view.findViewById(R.id.message);
            EditText formAddress = view.findViewById(R.id.address);

            builder.setPositiveButton(android.R.string.yes, (dialog, which) -> {
                JSONInteractor jsonInteractor = new JSONInteractor(context, "data.json");
                try {
                    DataObject data = jsonInteractor.readJSON(DataObject.class);
                    ArrayList<Poll> polls = data.getPolls();
                    int newId = polls.get(polls.size() - 1).getId() + 1;
                    polls.add(new Poll(newId, 1, 0, formTitle.getText().toString(), formText.getText().toString(), formAddress.getText().toString(), "https://newtambov.ru/storage/taisia/2018/05/DSC02076.jpg"));
                    data.setPolls(polls);
                    ArrayList<Integer> my_polls = data.getMypolls();
                    my_polls.add(newId);
                    data.setMypolls(my_polls);
                    jsonInteractor.writeJSON(data);

                    startActivity(new Intent(MapsService.this, MapsServiceFilled.class));

                } catch (IOException e) {
                    Log.e("Maps Service", "Error occurred while reading/writing data", e);
                }
            });

            builder.setNegativeButton(android.R.string.no, null).show();
        });
    }
}