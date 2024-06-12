package com.example.library.Models.DB;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Book implements Serializable {
    private String ID;
    private String name;
    private String image;
    private String description;
    private int disponible;
    private Set<String> authors;
    private Set<String> genres;
//    private String state;
//    private String retur;
    private String date;

    public Book() {
        authors = new HashSet<>();
        genres  = new HashSet<>();
    }

    public String getId() {
        return ID;
    }

    public void setId(String ID) {
        this.ID = ID;
    }

    public void resetAuthors(){
        this.authors.clear();
    }

    public void resetGenres(){
        this.genres.clear();
    }

    public String getDateRelease() {
        return date;
    }

    public void setDateRelease(String dateRelease) {
        this.date = dateRelease;
    }

    public void setAuthors(List<String> authors) {
        for(String author : authors){
            this.authors.add(author);
        }
    }

    public void setGenres(List<String> genres) {
        for(String genre : genres){
            this.genres.add(genre);
        }
    }

    public Set<String> getAuthors() {
        return authors;
    }

    public Set<String> getGenres() {
        return genres;
    }

//    public String getRetur() {
//        return retur;
//    }

//    public void setRetur(String retur) {
//        this.retur = retur;
//    }

    public void setName(String name) {
        this.name = name;
    }

//    public void setState(String state) {
//        this.state = state;
//    }
    public void setDescription(String description) {
        this.description = description;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    public int getDisponible() {
        return disponible;
    }

    public String getImage() {
        return image;
    }

//    public String getState() {
//        return state;
//    }
}

