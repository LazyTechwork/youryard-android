package com.example.yard.data;

import java.util.ArrayList;

public class DataObject {
    private ArrayList<Poll> polls;
    private ArrayList<Integer> my_polls;

    public ArrayList<Poll> getPolls() {
        return polls;
    }

    public void setPolls(ArrayList<Poll> polls) {
        this.polls = polls;
    }

    public ArrayList<Integer> getMy_polls() {
        return my_polls;
    }

    public void setMy_polls(ArrayList<Integer> my_polls) {
        this.my_polls = my_polls;
    }
}
