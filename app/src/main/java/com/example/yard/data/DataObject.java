package com.example.yard.data;

import java.util.ArrayList;

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
