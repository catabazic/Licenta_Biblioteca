package com.example.library.Models;

public class Message {
     private int idConversation;
     private int idUser;
     private String date;
     private String message;

     public Message() {
     }

     public void setId(int id) {
          this.idConversation = id;
     }

     public void setId_user(int id_user) {
          this.idUser = id_user;
     }

     public void setDate(String date) {
          this.date = date;
     }

     public void setMessage(String message) {
          this.message = message;
     }

     public int getId() {
          return idConversation;
     }

     public int getId_user() {
          return idUser;
     }

     public String getDate() {
          return date;
     }

     public String getMessage() {
          return message;
     }
}
