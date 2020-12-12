package com.example.yard.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yard.R;
import com.example.yard.data.Covid;

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

    public void addData(Covid covid) {
        this.items.add(covid);
        this.notifyItemChanged(this.items.size() - 1);
    }

    public ArrayList<Covid> getItems() {
        return items;
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
            ((TextView) itemView.findViewById(R.id.covid_header)).setText(covid.getName());
            ((TextView) itemView.findViewById(R.id.covid_city)).setText(covid.getAddress());
            ((TextView) itemView.findViewById(R.id.covid_desc)).setText(covid.getDescription());
        }
    }
}
