package com.example.yard.data;

public class Poll {
    private int id, pros, cons;
    private String name, description, city, image;

    public Poll(int id, int pros, int cons, String name, String description, String city, String image) {
        this.id = id;
        this.pros = pros;
        this.cons = cons;
        this.name = name;
        this.city = city;
        this.description = description;
        this.image = image;
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
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
