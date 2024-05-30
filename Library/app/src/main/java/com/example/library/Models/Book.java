package com.example.library.Models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Book implements Serializable {
    private String name;
    private String state;
    private String retur;
    private Set<Author> authors;
    private Set<Genre> genres;
    private String description;
    private int disponible;
    private String image;
    private int id;

    public Book() {
        authors = new HashSet<>();
        genres  = new HashSet<>();
    }

    public void resetAuthors(){
        this.authors.clear();
    }

    public void resetGenres(){
        this.genres.clear();
    }

    public void setAuthors(List<Author> authors) {
        for(Author author : authors){
            this.authors.add(author);
        }
    }

    public void setGenres(List<Genre> genres) {
        for(Genre genre : genres){
            this.genres.add(genre);
        }
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRetur() {
        return retur;
    }

    public void setRetur(String retur) {
        this.retur = retur;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(String state) {
        this.state = state;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // Getter methods
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

    public String getState() {
        return state;
    }
}

