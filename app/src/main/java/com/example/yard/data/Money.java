package com.example.yard.data;

public class Money {
    private int id, collected, need;
    private String name;
    private String description;
    private String address;
    private String fee;
    private boolean payed;

    public Money(int id, int collected, int need, String name, String description, String address, String fee, boolean payed) {
        this.id = id;
        this.collected = collected;
        this.need = need;
        this.name = name;
        this.description = description;
        this.address = address;
        this.fee = fee;
        this.payed = payed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCollected() {
        return collected;
    }

    public void setCollected(int collected) {
        this.collected = collected;
    }

    public int getNeed() {
        return need;
    }

    public void setNeed(int need) {
        this.need = need;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }
}
