package com.example.yard.fragments_registration;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.yard.R;
import com.example.yard.Register;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class RegisterFragment2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register2, container, false);

        Button mRegion = v.findViewById(R.id.buttonRegion);
        EditText mCity = v.findViewById(R.id.editTextPersonCity);
        EditText mStreet = v.findViewById(R.id.editTextPersonStreet);

        mRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                String url = "https://api.apify.com/v2/key-value-stores/1brJ0NLbQaJKPTWMO/records/LATEST?disableRedirect=true";

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            final String[] items = new String[85];

                            final JSONArray array = response.getJSONArray("infectedByRegion");
                            for (int i = 0; i < 85; i++) {
                                JSONObject jsonObject = array.getJSONObject(i);
                                items[i] = jsonObject.getString("region");
                            }

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Выбор региона").setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });

                            builder.setItems(items, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    try {
                                        JSONObject jsonObject = array.getJSONObject(i);
                                        Register.setDefaults("region_index", String.valueOf(i), getActivity());
                                        Register.setDefaults("region_name", jsonObject.getString("region"), getActivity());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            builder.show();

                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), "Something really REALLY wrong", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Something wrong. Try again", Toast.LENGTH_SHORT).show();
                    }
                });

                requestQueue.add(jsonObjectRequest);

            }
        });

        Register.mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Register.getDefaults("region_name", getActivity()) != null) {
                    //SET DEFAULTS
                    Register.setDefaults("user-city", mCity.getText().toString().trim(), getActivity());
                    Register.setDefaults("user-street", mStreet.getText().toString().trim(), getActivity());
                    //CHANGE FRAGMENT

                    if (mCity.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity(), "Название города не должно быть пустым", Toast.LENGTH_SHORT).show();
                    } else if (mStreet.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity(), "Название улицы не должно быть пустым", Toast.LENGTH_SHORT).show();
                    } else {
                        Register.mProgressBar.setProgress(Register.mProgressBar.getProgress() + 1);
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container, new RegisterFragment3());
                        fragmentTransaction.commit();
                    }
                } else {
                    Toast.makeText(getActivity(), "Необходимо выбрать регион", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Register.mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register.mProgressBar.setProgress(Register.mProgressBar.getProgress() - 1);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, new RegisterFragment1());
                fragmentTransaction.commit();
            }
        });

        return v;
    }
}