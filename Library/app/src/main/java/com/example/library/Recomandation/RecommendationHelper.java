package com.example.library.Recomandation;

import com.example.library.Database.DatabaseHelper;
import com.example.library.Models.Book;
import com.example.library.Models.Loan;
import com.example.library.Models.Review;
import com.example.library.Models.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendationHelper {
    private DatabaseHelper dbHelper;

    public RecommendationHelper(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public Map<String, Integer> analyzeUserPreferences(int userId) {
        List<Review> reviews = dbHelper.getUserReviews(userId);
        List<Book> loans = dbHelper.getBorrowedBooks(userId);
        List<View> views = dbHelper.getUserViews(userId);

        Map<String, Integer> preferenceCounts = new HashMap<>();

        for (Review review : reviews) {
            Book book = dbHelper.getBookById(review.getId_book());
            incrementPreference(preferenceCounts, book);
        }

        for (Book book : loans) {
            incrementPreference(preferenceCounts, book);
        }

        for (View view : views) {
            Book book = dbHelper.getBookById(view.getBookId());
            incrementPreference(preferenceCounts, book);
        }

        return preferenceCounts;
    }

    private void incrementPreference(Map<String, Integer> preferenceCounts, Book book) {
        if (book != null) {
            String genreKey = "genre_" + book.getGenre();
            String authorKey = "author_" + book.getAuthor();

            preferenceCounts.put(genreKey, preferenceCounts.getOrDefault(genreKey, 0) + 1);
            preferenceCounts.put(authorKey, preferenceCounts.getOrDefault(authorKey, 0) + 1);
        }
    }
}
