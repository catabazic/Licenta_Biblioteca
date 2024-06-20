package com.example.library.Models.DB;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Loan {
    String ID;
    private String userId;
    private String bookId;
    private String dataCerere;
    private String dataInceput;
    private String dataRetur;

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public String getId() {
        return ID;
    }

    public void setId(String ID) {
        this.ID = ID;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getDataCerere() {
        return dataCerere;
    }

    public void setDataCerere(String dataCerere) {
        this.dataCerere = dataCerere;
    }

    public String getDataInceput() {
        return dataInceput;
    }

    public void setDataInceput(String dataInceput) {
        this.dataInceput = dataInceput;
    }

    public String getDataRetur() {
        return dataRetur;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Date getTimestapt(int what){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss z yyyy");
        ZonedDateTime zonedDateTime;
        if(what == 0 ) {
            zonedDateTime = ZonedDateTime.parse(dataCerere, formatter);
        }else if(what == 1){
            zonedDateTime = ZonedDateTime.parse(dataInceput, formatter);
        }else{
            zonedDateTime = ZonedDateTime.parse(dataRetur, formatter);
        }

        Instant instant = zonedDateTime.toInstant();
        return Timestamp.from(instant);
    }

    public void setDataRetur(String dataRetur) {
        this.dataRetur = dataRetur;
    }
}
