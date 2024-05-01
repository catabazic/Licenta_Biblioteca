package com.example.library.Models;

import java.io.Serializable;

public class Book implements Serializable {
    private String name;
    private String author;
    private String state;
    private String retur;
    private String genre;
    private String description;
    private int disponible;
    private String image;
    private int id;

    public Book() {
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

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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

    // Constructor
    public Book(String name, String author, String state) {
        this.name = name;
        this.author = author;
        this.state = state;
    }

    public Book(String name, String author, String genre, String description, int disponible, String image) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.description = description;
        this.disponible = disponible;
        this.image=image;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
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

