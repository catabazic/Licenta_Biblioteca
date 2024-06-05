package com.example.library.Models;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class User implements Serializable {
    private int id;
    private String name;
    private String email;
    private String number;
    private String photo;
//    private Set<Genre> preferedGenres;
//    private Set<Author> preferedAuthors;
//
//    public void resetAuthors(){
//        this.preferedAuthors.clear();
//    }
//
//    public void resetGenres(){
//        this.preferedGenres.clear();
//    }
//
//    public void setAuthors(List<Author> authors) {
//        for(Author author : authors){
//            this.preferedAuthors.add(author);
//        }
//    }
//
//    public void setGenres(List<Genre> genres) {
//        for(Genre genre : genres){
//            this.preferedGenres.add(genre);
//        }
//    }
//
//    public Set<Author> getAuthors() {
//        return preferedAuthors;
//    }
//
//    public Set<Genre> getGenres() {
//        return preferedGenres;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
