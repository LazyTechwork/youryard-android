package com.example.yard.data;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Message {
    private String text, sender;
    private Date date;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", new Locale("ru"));
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", new Locale("ru"));

    public Message(String text, String date, String sender) {
        this.text = text;
        this.date = new Date(Long.parseLong(date));
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return dateFormat.format(date);
    }

    public Long getTimestamp() {
        return date.getTime();
    }


    public String getTime() {
        return timeFormat.format(date);
    }

    public void setDate(String date) {
        this.date = new Date(Long.parseLong(date));
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
