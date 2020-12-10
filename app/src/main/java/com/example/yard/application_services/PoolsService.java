package com.example.yard.application_services;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yard.R;
import com.example.yard.adapters.PollsAdapter;
import com.example.yard.data.DataObject;
import com.example.yard.data.Poll;
import com.example.yard.utils.JSONInteractor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class PoolsService extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pools_service);
        ArrayList<Poll> polls = new ArrayList<>();

        try {
            /*JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("polls");

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                String mPollName = jo_inside.getString("name");
                String mPollDesc = jo_inside.getString("description");
                String mPollCity = jo_inside.getString("address");
                String mImage = jo_inside.getString("image");
                Integer mId = jo_inside.getInt("id");
                Integer mPros = jo_inside.getInt("pros");
                Integer mCons = jo_inside.getInt("cons");
                polls.add(new Poll(mId, mPros, mCons, mPollName, mPollDesc, mPollCity, mImage));
            }*/
            JSONInteractor jsonInteractor = new JSONInteractor(this, "data.json");
            polls = jsonInteractor.readJSON(DataObject.class).getPolls();
            PollsAdapter pollsAdapter = new PollsAdapter(this);
            pollsAdapter.updateData(polls);

            RecyclerView pollsView = findViewById(R.id.polls_view);
            pollsView.setAdapter(pollsAdapter);
            pollsView.setLayoutManager(new LinearLayoutManager(this));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}