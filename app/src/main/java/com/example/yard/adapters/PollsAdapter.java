package com.example.yard.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yard.R;
import com.example.yard.data.DataObject;
import com.example.yard.data.Poll;
import com.example.yard.utils.JSONInteractor;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class PollsAdapter extends RecyclerView.Adapter<PollsAdapter.PollViewHolder> {

    private final Activity activity;
    private final JSONInteractor dataInteractor;
    private ArrayList<Poll> items;
    private ArrayList<Integer> lockedPolls;

    public PollsAdapter(ArrayList<Poll> items, Activity activity) {
        this.items = items;
        this.activity = activity;
        this.dataInteractor = new JSONInteractor(activity, "data.json");
    }

    public PollsAdapter(Activity activity) {
        this.items = new ArrayList<>();
        this.activity = activity;
        this.dataInteractor = new JSONInteractor(activity, "data.json");
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
        notifyDataSetChanged();
    }

    public void updateData(Poll poll, int pos) {
        this.items.set(pos, poll);
        notifyItemChanged(pos, poll);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull PollsAdapter.PollViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public ArrayList<Integer> getLockedPolls() {
        return lockedPolls;
    }

    public void setLockedPolls(ArrayList<Integer> lockedPolls) {
        this.lockedPolls = lockedPolls;
        notifyDataSetChanged();
    }

    class PollViewHolder extends RecyclerView.ViewHolder {
        public PollViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public Bitmap getBitmapFromURL(String src) {
            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void bind(int itemPosition) {
            Poll poll = items.get(itemPosition);
            TextView mVotesCount = itemView.findViewById(R.id.votes_count);
            ImageView mImageLike = itemView.findViewById(R.id.like);
            ImageView mImageDislike = itemView.findViewById(R.id.dislike);
            ImageView mImage = itemView.findViewById(R.id.imageView);
            new Thread(() -> {
                try {
                    Bitmap bitmap = getBitmapFromURL(poll.getImage());
                    activity.runOnUiThread(() -> mImage.setImageBitmap(bitmap));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

            int diff = poll.getPros() - poll.getCons();
            mVotesCount.setText(((diff >= 0 ? "+" : "") + diff));
            ((TextView) itemView.findViewById(R.id.poll_city)).setText(poll.getCity());
            ((TextView) itemView.findViewById(R.id.poll_header)).setText(poll.getName());
            ((TextView) itemView.findViewById(R.id.poll_desc)).setText(poll.getDescription());
            mImageLike.setImageResource(poll.isVoted() && poll.isVotedPros() ? R.drawable.ic_green_filled : R.drawable.ic_green_notfilled);
            mImageDislike.setImageResource(poll.isVoted() && !poll.isVotedPros() ? R.drawable.ic_red_filled : R.drawable.ic_red_notfilled);
            ProgressBar progressBar = itemView.findViewById(R.id.poll_progress);
            progressBar.setMax(poll.getCons() + poll.getPros());
            progressBar.setProgress(poll.getPros());

            if (!lockedPolls.contains(poll.getId()))
                ((ImageView) itemView.findViewById(R.id.delete_button)).setVisibility(View.INVISIBLE);

            mImageLike.setOnClickListener(v -> {
                if (lockedPolls.contains(poll.getId())) {
                    Toast.makeText(activity, "Голосовать за свои заявки нельзя!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (poll.isVoted()) {
                    if (poll.isVotedPros()) {
                        poll.setVoted(false);
                        poll.setPros(poll.getPros() - 1);
                    } else {
                        poll.setVotedPros(true);
                        poll.setCons(poll.getCons() - 1);
                        poll.setPros(poll.getPros() + 1);
                    }
                } else {
                    poll.setVoted(true);
                    poll.setVotedPros(true);
                    poll.setPros(poll.getPros() + 1);
                }
                try {
                    savePoll(poll, itemPosition);
                    updateData(poll, itemPosition);
                } catch (IOException e) {
                    Log.e("Polls Adapter", "Error while reading or writing data", e);
                }
            });

            mImageDislike.setOnClickListener(v -> {
                if (lockedPolls.contains(poll.getId())) {
                    Toast.makeText(activity, "Голосовать за свои заявки нельзя!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (poll.isVoted()) {
                    if (!poll.isVotedPros()) {
                        poll.setVoted(false);
                        poll.setCons(poll.getCons() - 1);
                    } else {
                        poll.setVotedPros(false);
                        poll.setPros(poll.getPros() - 1);
                        poll.setCons(poll.getCons() + 1);
                    }
                } else {
                    poll.setVoted(true);
                    poll.setVotedPros(false);
                    poll.setCons(poll.getCons() + 1);
                }
                try {
                    savePoll(poll, itemPosition);
                    updateData(poll, itemPosition);
                } catch (IOException e) {
                    Log.e("Polls Adapter", "Error while reading or writing data", e);
                }
            });

            ((ImageView) itemView.findViewById(R.id.delete_button)).setOnClickListener(l -> {
                JSONInteractor jsonInteractor = new JSONInteractor(activity, "data.json");
                try {
                    DataObject data = jsonInteractor.readJSON(DataObject.class);
                    if (!lockedPolls.contains(poll.getId()))
                        return;
                    ArrayList<Poll> pollArrayList = data.getPolls();
                    ArrayList<Integer> mypolls = data.getMypolls();
                    pollArrayList.removeIf(p -> p.getId() == poll.getId());
                    mypolls.removeIf(p -> p == poll.getId());
                    items.removeIf(p -> p.getId() == poll.getId());
                    data.setPolls(pollArrayList);
                    data.setMypolls(new ArrayList<>(mypolls.stream().distinct().collect(Collectors.toList())));
                    setLockedPolls(data.getMypolls());
                    jsonInteractor.writeJSON(data);
                    updateData(data.getMyPollsObject());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        public void savePoll(Poll poll, int itemPosition) throws IOException {
            new Thread(() -> {
                try {
                    DataObject data = dataInteractor.readJSON(DataObject.class);
                    ArrayList<Poll> polls = data.getPolls();
                    polls.set(itemPosition, poll);
                    data.setPolls(polls);
                    dataInteractor.writeJSON(data);
                    Log.d("Polls Adapter", dataInteractor.readRawJSON());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
