package com.example.yard.application_services;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.yard.R;
import com.example.yard.adapters.CovidsAdapter;
import com.example.yard.data.Covid;
import com.example.yard.data.DataObject;
import com.example.yard.utils.JSONInteractor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class CovidService extends AppCompatActivity {

    String userID;
    FirebaseAuth mAuth;
    Integer region_index;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_service);

        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                region_index = Integer.valueOf(value.getString("index"));

                RequestQueue requestQueue = Volley.newRequestQueue(CovidService.this);
                String url = "https://api.apify.com/v2/key-value-stores/1brJ0NLbQaJKPTWMO/records/LATEST?disableRedirect=true";

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            final String[] items = new String[85];
                            final JSONArray array = response.getJSONArray("infectedByRegion");
                            JSONObject jsonObject = array.getJSONObject(region_index);
                            TextView mInfected = findViewById(R.id.textView_infected_number);
                            TextView mDeceased = findViewById(R.id.textView_deceased_number);
                            TextView mRecovered = findViewById(R.id.textView_recovered_number);

                            mInfected.setText(jsonObject.getString("infected"));
                            mDeceased.setText(jsonObject.getString("deceased"));
                            mRecovered.setText(jsonObject.getString("recovered"));

                        } catch (JSONException e) {
                            Toast.makeText(CovidService.this, "Something really REALLY wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CovidService.this, "Something wrong. Try again", Toast.LENGTH_SHORT).show();
                    }
                });

                requestQueue.add(jsonObjectRequest);

            }
        });


        JSONInteractor jsonInteractor = new JSONInteractor(this, "data.json");
        ArrayList<Covid> covids = new ArrayList<>();
        try {
            covids = jsonInteractor.readJSON(DataObject.class).getCovids();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Button mButton;
        mButton = findViewById(R.id.button);

        RecyclerView covidsView = findViewById(R.id.covid_new_view);
        CovidsAdapter covidsAdapter = new CovidsAdapter(this);
        covidsAdapter.updateData(covids);
        covidsView.setAdapter(covidsAdapter);
        covidsView.setItemAnimator(null);
        covidsView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Covid> finalCovids = covids;
        mButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(CovidService.this);
            View view = getLayoutInflater().inflate(R.layout.alert_create_statement_covid, null);
            builder.setView(view);

            builder.setPositiveButton(android.R.string.yes, (dialog, which) -> {
                if (((EditText) view.findViewById(R.id.title)).getText().toString().length()==0 || ((EditText) view.findViewById(R.id.message)).getText().toString().length()==0 || ((EditText) view.findViewById(R.id.address)).getText().toString().length()==0) {
                    Toast.makeText(this, "Нельзя оставлять поля заявки пустыми", Toast.LENGTH_SHORT).show();
                } else{
                    covidsAdapter.addData(new Covid(finalCovids.get(finalCovids.size() - 1).getId() + 1, ((EditText) view.findViewById(R.id.title)).getText().toString(), ((EditText) view.findViewById(R.id.message)).getText().toString(), ((EditText) view.findViewById(R.id.address)).getText().toString()));
                    try {
                        DataObject data = jsonInteractor.readJSON(DataObject.class);
                        data.setCovids(covidsAdapter.getItems());
                        jsonInteractor.writeJSON(data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            });

            builder.setNegativeButton(android.R.string.no, null).show();
        });

    }
}