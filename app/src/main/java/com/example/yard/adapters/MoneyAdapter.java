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
import com.example.yard.data.Money;

import java.util.ArrayList;

public class MoneyAdapter extends RecyclerView.Adapter<MoneyAdapter.MoneyViewHolder> {

    private final Activity activity;
    private ArrayList<Money> items;

    public MoneyAdapter(ArrayList<Money> items, Activity activity) {
        this.items = items;
        this.activity = activity;
    }

    public MoneyAdapter(Activity activity) {
        this.items = new ArrayList<>();
        this.activity = activity;
    }

    public ArrayList<Money> getItems() {
        return items;
    }

    public void addData(Money money) {
        this.items.add(money);
        this.notifyItemChanged(this.items.size() - 1);
    }

    @NonNull
    @Override
    public MoneyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoneyViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.view_money, parent, false)
        );
    }

    public void updateData(ArrayList<Money> money) {
        this.items = money;
        this.notifyDataSetChanged();
    }

    public void updateData(Money money, int pos) {
        this.items.set(pos, money);
        this.notifyItemChanged(pos);
    }

    @Override
    public void onBindViewHolder(@NonNull MoneyViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class MoneyViewHolder extends RecyclerView.ViewHolder {
        public MoneyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(int itemPosition) {
            Money money = items.get(itemPosition);
            ((TextView) itemView.findViewById(R.id.money_header)).setText(money.getName());
            ((TextView) itemView.findViewById(R.id.money_city)).setText(money.getAddress());
            ((TextView) itemView.findViewById(R.id.money_desc)).setText(money.getDescription());
            ((TextView) itemView.findViewById(R.id.money_desc)).setText(money.getDescription());
            ((TextView) itemView.findViewById(R.id.money_progresstext)).setText("" + money.getCollected() + "/" + money.getNeed());
            ProgressBar progressBar = (ProgressBar) itemView.findViewById(R.id.money_progressbar);
            progressBar.setMax(money.getNeed());
            progressBar.setProgress(Math.min(money.getCollected(), money.getNeed()));
        }
    }
}
