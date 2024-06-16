package com.example.library.Models;

import android.os.Build;

import androidx.annotation.RequiresApi;


import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;
import java.util.Date;

public class Chat implements Serializable {
    private String id;
    private String Name;
    private String idUser;
    private String message;
    private String date;
    private boolean lastMessageMine;

    public Chat() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
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

    public String getName() {
        return Name;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Date getTimestapt(){
        System.out.println("date " +date.toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss z yyyy");

        ZonedDateTime zonedDateTime = ZonedDateTime.parse(date, formatter);

        Instant instant = zonedDateTime.toInstant();

        return Timestamp.from(instant);
    }

    public boolean isLastMessageMine() {
        return lastMessageMine;
    }
}
