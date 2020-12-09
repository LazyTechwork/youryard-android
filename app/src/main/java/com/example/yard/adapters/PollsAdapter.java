package com.example.yard.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yard.R;
import com.example.yard.data.Poll;

import java.util.ArrayList;

public class PollsAdapter extends RecyclerView.Adapter<PollsAdapter.PollViewHolder> {

    private ArrayList<Poll> items;
    private Activity activity;

    public PollsAdapter(ArrayList<Poll> items, Activity activity) {
        this.items = items;
        this.activity = activity;
    }

    public PollsAdapter(Activity activity) {
        this.items = new ArrayList<>();
        this.activity = activity;
    }

    @NonNull
    @Override
    public PollsAdapter.PollViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PollsAdapter.PollViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.view_poll, parent, false)
        );
    }

    public void updateData(ArrayList<Poll> polls) {
        this.items = polls;
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull PollsAdapter.PollViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class PollViewHolder extends RecyclerView.ViewHolder {
        public PollViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(int itemPosition) {
            Poll poll = items.get(itemPosition);
            ((TextView) itemView.findViewById(R.id.poll_header)).setText(poll.getName());
            ((TextView) itemView.findViewById(R.id.poll_desc)).setText(poll.getDescription());
            ProgressBar progressBar = (ProgressBar) itemView.findViewById(R.id.poll_progress);
            progressBar.setMax(poll.getCons() + poll.getPros());
            progressBar.setProgress(poll.getCons());
        }
    }
}
