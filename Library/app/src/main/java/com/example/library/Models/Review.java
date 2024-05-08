package com.example.library.Models;

public class Review {
    private int id_user;
    private int id_book;
    private String date;
    private float rating;
    private String reviewTitle;
    private String reviewText;

    public Review() {
    }

    public int getId_user() {
        return id_user;
    }

    public int getId_book() {
        return id_book;
    }

    public String getDate() {
        return date;
    }

    public float getRating() {
        return rating;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setId_book(int id_book) {
        this.id_book = id_book;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}
