package com.example.library.Recomandation;

import com.example.library.Database.DatabaseHelper;
import com.example.library.Models.Review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendationHelper {
    private DatabaseHelper dbHelper;

    public RecommendationHelper(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public Map<String, Integer> analyzeUserPreferences(int userId) {
        List<Map<String, Object>> preferences = dbHelper.getUserPreferences(userId);
        Map<String, Integer> preferenceCounts = new HashMap<>();

        for (Map<String, Object> preference : preferences) {
            if (preference.containsKey("genre_id")) {
                int genreId = (int) preference.get("genre_id");
                preferenceCounts.put("genre_" + genreId, preferenceCounts.getOrDefault("genre_" + genreId, 0) + 1);
            } else if (preference.containsKey("author_id")) {
                int authorId = (int) preference.get("author_id");
                preferenceCounts.put("author_" + authorId, preferenceCounts.getOrDefault("author_" + authorId, 0) + 1);
            }
        }

        return preferenceCounts;
    }

    public Map<Integer, Float> analyzeUserReviews(int userId) {
        List<Review> reviews = dbHelper.getUserReviews(userId);
        Map<Integer, Float> reviewScores = new HashMap<>();

        for (Review review : reviews) {
            reviewScores.put(review.getId_book(), review.getRating());
        }

        return reviewScores;
    }
}
