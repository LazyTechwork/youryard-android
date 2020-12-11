package com.example.yard.application_services;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.yard.R;
import com.example.yard.adapters.MoneyAdapter;
import com.example.yard.adapters.PollsAdapter;
import com.example.yard.data.DataObject;
import com.example.yard.data.Money;
import com.example.yard.utils.JSONInteractor;

import java.io.FileNotFoundException;

public class MoneyService extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_service);

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