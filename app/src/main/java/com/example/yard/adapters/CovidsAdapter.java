package com.example.yard.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yard.R;
import com.example.yard.data.Covid;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CovidsAdapter extends RecyclerView.Adapter<CovidsAdapter.CovidViewHolder> {

    private ArrayList<Covid> items;
    private final Activity activity;

    public CovidsAdapter(ArrayList<Covid> items, Activity activity) {
        this.items = items;
        this.activity = activity;
    }

    public CovidsAdapter(Activity activity) {
        this.items = new ArrayList<>();
        this.activity = activity;
    }

    @NonNull
    @Override
    public CovidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CovidViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.view_covid, parent, false)
        );
    }

    public void updateData(ArrayList<Covid> covids) {
        this.items = covids;
        this.notifyDataSetChanged();
    }

    public void updateData(Covid covid, int pos) {
        this.items.set(pos, covid);
        this.notifyItemChanged(pos);
    }

    @Override
    public void onBindViewHolder(@NonNull CovidViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class CovidViewHolder extends RecyclerView.ViewHolder {
        public CovidViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(int itemPosition) {
            Covid covid = items.get(itemPosition);
            Log.d("COVID", "" + itemPosition + " " + items.size());
            ((TextView) itemView.findViewById(R.id.covid_header)).setText(covid.getName());
            ((TextView) itemView.findViewById(R.id.covid_city)).setText(covid.getAddress());
            ((TextView) itemView.findViewById(R.id.covid_desc)).setText(covid.getDescription());
        }
    }
}
