package com.example.library.Models.DB;

public class Review {
    String ID;

    private String id_user;
    private String id_book;
    private String date;
    private float rating;
    private String reviewTitle;
    private String reviewText;

    public Review() {
        rating = 0;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getId_user() {
        return id_user;
    }

    public String getId_book() {
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

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public void setId_book(String id_book) {
        this.id_book = id_book;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRating(double rating) {
        this.rating = (float)rating;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}
