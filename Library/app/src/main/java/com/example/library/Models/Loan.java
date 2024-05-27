package com.example.library.Models;

public class Loan {
    private int userId;
    private int bookId;
    private String dataCerere;
    private String dataInceput;
    private String dataRetur;

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
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

    public void setDataRetur(String dataRetur) {
        this.dataRetur = dataRetur;
    }
}
