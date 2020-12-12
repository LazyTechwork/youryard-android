package com.example.yard.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yard.R;
import com.example.yard.data.Message;

import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {

    private final Activity activity;
    private ArrayList<Message> items;

    public MessagesAdapter(ArrayList<Message> items, Activity activity) {
        this.items = items;
        this.activity = activity;
    }

    public MessagesAdapter(Activity activity) {
        this.items = new ArrayList<>();
        this.activity = activity;
    }

    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessagesViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.view_message, parent, false)
        );
    }

    public void updateData(ArrayList<Message> messages) {
        this.items = messages;
        this.notifyDataSetChanged();
    }

    public void updateData(Message message, int pos) {
        this.items.set(pos, message);
        this.notifyItemChanged(pos);
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class MessagesViewHolder extends RecyclerView.ViewHolder {
        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(int itemPosition) {
            Message message = items.get(itemPosition);
            ((TextView) itemView.findViewById(R.id.message_sender)).setText(message.getSender());
            ((TextView) itemView.findViewById(R.id.message_text)).setText(message.getText());
            ((TextView) itemView.findViewById(R.id.message_date)).setText(message.getDate());
            ((TextView) itemView.findViewById(R.id.message_time)).setText(message.getTime());
        }
    }
}
