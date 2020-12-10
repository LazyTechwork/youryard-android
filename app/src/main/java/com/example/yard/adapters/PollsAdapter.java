package com.example.yard.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yard.R;
import com.example.yard.Register;
import com.example.yard.application_services.PoolsService;
import com.example.yard.data.Poll;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

        int green_flag = 0, red_flag = 0;

        public Bitmap getBitmapFromURL(String src) {
            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        public void bind(int itemPosition) {
            Poll poll = items.get(itemPosition);
            TextView mVotesCount = itemView.findViewById(R.id.votes_count);
            ImageView mImageLike = itemView.findViewById(R.id.like);
            ImageView mImageDislike = itemView.findViewById(R.id.dislike);
            ImageView mImage = itemView.findViewById(R.id.imageView);
            new Thread(() -> {
                try {
                    mImage.setImageBitmap(getBitmapFromURL(poll.getImage()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();


            mVotesCount.setText("+" + String.valueOf(poll.getCons() + poll.getPros()));
            ((TextView) itemView.findViewById(R.id.poll_city)).setText(poll.getCity());
            ((TextView) itemView.findViewById(R.id.poll_header)).setText(poll.getName());
            ((TextView) itemView.findViewById(R.id.poll_desc)).setText(poll.getDescription());
            ProgressBar progressBar = (ProgressBar) itemView.findViewById(R.id.poll_progress);
            progressBar.setMax(poll.getCons() + poll.getPros());
            progressBar.setProgress(poll.getPros());

            mImageLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (green_flag == 0 && red_flag == 0) {
                        mImageLike.setImageResource(R.drawable.ic_green_filled);
                        mVotesCount.setText("+" + (1 + Integer.valueOf(mVotesCount.getText().toString())));
                        green_flag = 1;
                        progressBar.setProgress(progressBar.getProgress() + 1);
                    } else {
                        if (green_flag == 1) {
                            mImageLike.setImageResource(R.drawable.ic_green_notfilled);
                            mVotesCount.setText("+" + (-1 + Integer.valueOf(mVotesCount.getText().toString())));
                            green_flag = 0;
                            progressBar.setProgress(progressBar.getProgress() - 1);
                        } else {
                            mImageLike.setImageResource(R.drawable.ic_green_filled);
                            mImageDislike.setImageResource(R.drawable.ic_red_notfilled);
                            mVotesCount.setText("+" + (+2 + Integer.valueOf(mVotesCount.getText().toString())));
                            green_flag = 1;
                            red_flag = 0;
                            progressBar.setProgress(progressBar.getProgress() + 2);
                        }
                    }
                }
            });

            mImageDislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (red_flag == 0 && green_flag == 0) {
                        mImageDislike.setImageResource(R.drawable.ic_red_filled);
                        mVotesCount.setText("+" + (-1 + Integer.valueOf(mVotesCount.getText().toString())));
                        red_flag = 1;
                        progressBar.setProgress(progressBar.getProgress() - 1);
                    } else {
                        if (red_flag == 1) {
                            mImageDislike.setImageResource(R.drawable.ic_red_notfilled);
                            mVotesCount.setText("+" + (1 + Integer.valueOf(mVotesCount.getText().toString())));
                            red_flag = 0;
                            progressBar.setProgress(progressBar.getProgress() + 1);
                        } else {
                            mImageDislike.setImageResource(R.drawable.ic_red_filled);
                            mImageLike.setImageResource(R.drawable.ic_green_notfilled);
                            mVotesCount.setText("+" + (-2 + Integer.valueOf(mVotesCount.getText().toString())));
                            red_flag = 1;
                            green_flag = 0;
                            progressBar.setProgress(progressBar.getProgress() - 2);
                        }
                    }
                }
            });

        }
    }
}
