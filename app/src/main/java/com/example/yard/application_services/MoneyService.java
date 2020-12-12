package com.example.yard.application_services;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.yard.R;
import com.example.yard.adapters.MoneyAdapter;
import com.example.yard.adapters.PollsAdapter;
import com.example.yard.data.DataObject;
import com.example.yard.data.Money;
import com.example.yard.data.Poll;
import com.example.yard.utils.JSONInteractor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MoneyService extends AppCompatActivity {

    Button mCreateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_service);

        mCreateButton = findViewById(R.id.createButton);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CREATE ALERT
                AlertDialog.Builder builder = new AlertDialog.Builder(MoneyService.this);
                View view = getLayoutInflater().inflate(R.layout.alert_money, null);
                builder.setView(view);

                EditText formTitle = view.findViewById(R.id.title);
                EditText formText = view.findViewById(R.id.message);
                EditText formAddress = view.findViewById(R.id.address);
                EditText formOverallSum = view.findViewById(R.id.overall_sum);
                EditText formPerPerson = view.findViewById(R.id.sum_per_person);

                builder.setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    //Write something that will make this alert work here
                });

                builder.setNegativeButton(android.R.string.no, null).show();
            }
        });

        try {
            DataObject data = new JSONInteractor(this, "data.json").readJSON(DataObject.class);
            MoneyAdapter moneyAdapter = new MoneyAdapter(this);
            moneyAdapter.updateData(data.getMoney());

            RecyclerView moneyView = findViewById(R.id.money_view);
            moneyView.setAdapter(moneyAdapter);
            moneyView.setLayoutManager(new LinearLayoutManager(this));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}