package com.example.library.Models.DB;

public class ChatDb {
    String ID;
    String idUser1;
    String idUser2;

    public String getIdUser1() {
        return idUser1;
    }

    public String getId() {
        return ID;
    }

    public void setId(String ID) {
        this.ID = ID;
    }

    public void setIdUser1(String idUser1) {
        this.idUser1 = idUser1;
    }

    public String getIdUser2() {
        return idUser2;
    }

    public void setIdUser2(String idUser2) {
        this.idUser2 = idUser2;
    }
}
