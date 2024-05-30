package com.example.library.Models;

import java.io.Serializable;

public class Author implements Serializable {
    private int id;
    private String name;

    public Author(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
