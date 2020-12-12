package com.example.yard.application_services;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yard.R;
import com.example.yard.adapters.MoneyAdapter;
import com.example.yard.data.DataObject;
import com.example.yard.data.Money;
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

        //SET JSON DATA INTO RECYCLERVIEW
        MoneyAdapter moneyAdapter = new MoneyAdapter(this);
        try {
            DataObject data = new JSONInteractor(this, "data.json").readJSON(DataObject.class);
            moneyAdapter.updateData(data.getMoney());
            RecyclerView moneyView = findViewById(R.id.money_view);
            moneyView.setAdapter(moneyAdapter);
            moneyView.setLayoutManager(new LinearLayoutManager(this));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //CREATING NEW APPLICATION FOR COLLECTING MONEY
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

                //ADD DATA ABOUT NEW APPLICATIONS
                builder.setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    if (formOverallSum.getText().toString().length() == 0 || formTitle.getText().toString().length() == 0 || formText.getText().toString().length() == 0 || formAddress.getText().toString().length() == 0 || formPerPerson.getText().toString().length() == 0) {
                        Toast.makeText(MoneyService.this, "Нельзя оставлять поля заявки пустыми", Toast.LENGTH_SHORT).show();
                    } else {
                        JSONInteractor jsonInteractor = new JSONInteractor(getApplicationContext(), "data.json");
                        try {
                            DataObject data = null;
                            data = jsonInteractor.readJSON(DataObject.class);
                            ArrayList<Money> money = data.getMoney();
                            int newId = money.get(money.size() - 1).getId() + 1;
                            money.add(new Money(newId, 0, Integer.parseInt(formOverallSum.getText().toString()), formTitle.getText().toString(), formText.getText().toString(), formAddress.getText().toString(), formPerPerson.getText().toString(), false));
                            data.setMoney(money);
                            jsonInteractor.writeJSON(data);
                            moneyAdapter.updateData(money);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                builder.setNegativeButton(android.R.string.no, null).show();
            }
        });
    }
}