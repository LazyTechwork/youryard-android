package com.example.yard.data;

public class Poll {
    private int id, pros, cons;
    private String name, description, address, image;
    private boolean isVoted, votedPros;

    public Poll(int id, int pros, int cons, String name, String description, String city, String image, boolean isVoted, boolean votedPros) {
        this.id = id;
        this.pros = pros;
        this.cons = cons;
        this.name = name;
        this.address = city;
        this.description = description;
        this.image = image;
        this.isVoted = isVoted;
        this.votedPros = votedPros;
    }

    public Poll(int id, int pros, int cons, String name, String description, String city, String image) {
        this(id, pros, cons, name, description, city, image, true, true);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPros() {
        return pros;
    }

    public void setPros(int pros) {
        this.pros = pros;
    }

    public int getCons() {
        return cons;
    }

    public void setCons(int cons) {
        this.cons = cons;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return address;
    }

    public void setCity(String city) {
        this.address = city;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isVoted() {
        return isVoted;
    }

    public void setVoted(boolean voted) {
        isVoted = voted;
    }

    public boolean isVotedPros() {
        return votedPros;
    }

    public void setVotedPros(boolean votedPros) {
        this.votedPros = votedPros;
    }
}
