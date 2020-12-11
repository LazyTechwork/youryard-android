package com.example.yard.data;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class DataObject {
    private ArrayList<Poll> polls;
    private ArrayList<Integer> mypolls;
    private ArrayList<Covid> covids;

    public ArrayList<Poll> getPolls() {
        return polls;
    }

    public void setPolls(ArrayList<Poll> polls) {
        this.polls = polls;
    }

    public ArrayList<Integer> getMypolls() {
        return mypolls;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<Poll> getMyPollsObject() {
        return new ArrayList<>(polls.stream().filter(s -> mypolls.contains(s.getId())).collect(Collectors.toList()));
    }

    public void setMypolls(ArrayList<Integer> mypolls) {
        this.mypolls = mypolls;
    }

    public ArrayList<Covid> getCovids() {
        return covids;
    }

    public void setCovids(ArrayList<Covid> covids) {
        this.covids = covids;
    }
}
