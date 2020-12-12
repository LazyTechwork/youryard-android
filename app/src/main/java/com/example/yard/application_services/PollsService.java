package com.example.yard.application_services;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yard.R;
import com.example.yard.adapters.PollsAdapter;
import com.example.yard.data.DataObject;
import com.example.yard.utils.JSONInteractor;

import java.io.FileNotFoundException;

public class PollsService extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polls_service);

        //ADD NEW POLLS TO RECYCLERVIEW
        try {
            DataObject data = new JSONInteractor(this, "data.json").readJSON(DataObject.class);
            PollsAdapter pollsAdapter = new PollsAdapter(this);
            pollsAdapter.setLockedPolls(data.getMypolls());
            pollsAdapter.updateData(data.getPolls());

            RecyclerView pollsView = findViewById(R.id.polls_view);
            pollsView.setAdapter(pollsAdapter);
            pollsView.setLayoutManager(new LinearLayoutManager(this));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}