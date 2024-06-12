package com.example.library.Models.DB;

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

    public void setDataRetur(String dataRetur) {
        this.dataRetur = dataRetur;
    }
}
