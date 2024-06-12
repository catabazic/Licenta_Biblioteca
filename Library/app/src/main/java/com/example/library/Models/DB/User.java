package com.example.library.Models.DB;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class User implements Serializable {
    String ID;

    private String name;
    private String email;
    private String number;
    private String photo;
    private String password;
    private Set<String> preferredGenres;
    private Set<String> preferredAuthors;

    public User(String name, String number, String password) {
        this.name = name;
        this.number = number;
        this.password = password;
    }

    public User(String name, String email, String number, String password) {
        this.name = name;
        this.number = number;
        this.password = password;
        this.email = email;
    }

    public User(){}

    public String getId() {
        return ID;
    }

    public void setId(String ID) {
        this.ID = ID;
    }

    public void resetAuthors(){
        this.preferredAuthors.clear();
    }

    public void resetGenres(){
        this.preferredGenres.clear();
    }

    public void setAuthors(List<String> authors) {
        for(String author : authors){
            this.preferredAuthors.add(author);
        }
    }

    public void setGenres(List<String> genres) {
        for(String genre : genres){
            this.preferredGenres.add(genre);
        }
    }

    public Set<String> getAuthors() {
        return preferredAuthors;
    }

    public Set<String> getGenres() {
        return preferredGenres;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }

    public String getPhoto() {
        return photo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
