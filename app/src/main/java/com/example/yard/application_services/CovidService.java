package com.example.yard.application_services;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yard.R;
import com.example.yard.Register;
import com.example.yard.adapters.CovidsAdapter;
import com.example.yard.adapters.PollsAdapter;
import com.example.yard.data.Covid;
import com.example.yard.data.DataObject;
import com.example.yard.utils.JSONInteractor;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CovidService extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_service);

        TextView mRegion = findViewById(R.id.textView_region);
        TextView mInfected = findViewById(R.id.textView_infected_number);
        TextView mDeceased = findViewById(R.id.textView_deceased_number);
        TextView mRecovered = findViewById(R.id.textView_recovered_number);

        mRegion.setText(Register.getDefaults("region_name", CovidService.this));
        mInfected.setText(Register.getDefaults("region_infected", CovidService.this));
        mDeceased.setText(Register.getDefaults("region_deceased", CovidService.this));
        mRecovered.setText(Register.getDefaults("region_recovered", CovidService.this));

        try {
            RecyclerView mCovidHelpView = findViewById(R.id.covid_new_view);
            mCovidHelpView.setItemAnimator(null);
            JSONInteractor jsonInteractor = new JSONInteractor(this, "data.json");
            ArrayList<Covid> covids = jsonInteractor.readJSON(DataObject.class).getCovids();
            CovidsAdapter covidsAdapter = new CovidsAdapter(this);
            covidsAdapter.updateData(covids);

            RecyclerView covidsView = findViewById(R.id.covid_new_view);
            covidsView.setAdapter(covidsAdapter);
            covidsView.setLayoutManager(new LinearLayoutManager(this));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}