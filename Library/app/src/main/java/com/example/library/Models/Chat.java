package com.example.library.Models;

import java.io.Serializable;

public class Chat implements Serializable {
    private int id;
    private String Name;
    private String message;
    private String date;
    private boolean lastMessageMine;

    public Chat() {
    }

    public void setName(String name) {
        Name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLastMessageMine(boolean lastMessageMine) {
        this.lastMessageMine = lastMessageMine;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public boolean isLastMessageMine() {
        return lastMessageMine;
    }
}
