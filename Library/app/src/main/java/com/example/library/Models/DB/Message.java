package com.example.library.Models.DB;

public class Message {
     String ID;
     private String idConversation;
     private String idUser;
     private String date;
     private String message;

     public Message() {
     }

     public String getID() {
          return ID;
     }

     public void setID(String ID) {
          this.ID = ID;
     }

     public void setId(String id) {
          this.idConversation = id;
     }

     public void setId_user(String id_user) {
          this.idUser = id_user;
     }

     public void setDate(String date) {
          this.date = date;
     }

     public void setMessage(String message) {
          this.message = message;
     }

     public String getId() {
          return idConversation;
     }

     public String getId_user() {
          return idUser;
     }

     public String getDate() {
          return date;
     }

     public String getMessage() {
          return message;
     }
}
