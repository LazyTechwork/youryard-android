package com.example.yard.application_services;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.yard.R;
import com.example.yard.adapters.PollsAdapter;
import com.example.yard.data.DataObject;
import com.example.yard.data.Poll;
import com.example.yard.utils.JSONInteractor;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class PoolsService extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pools_service);
        ArrayList<Poll> polls = new ArrayList<>();
        polls.add(new Poll(1, 12, 13, "Скамейка", "1234"));
        polls.add(new Poll(2, 16, 123, "Скамейка 2", "1234"));
        polls.add(new Poll(3, 22, 52, "Скамейка 3", "1234"));
        PollsAdapter pollsAdapter = new PollsAdapter(this);
        pollsAdapter.updateData(polls);

        RecyclerView pollsView = (RecyclerView) findViewById(R.id.polls_view);
        pollsView.setAdapter(pollsAdapter);
        pollsView.setLayoutManager(new LinearLayoutManager(this));
    }
}