//package com.example.library.Recomandation;
//
//import com.example.library.Database.DatabaseHelper;
//import com.example.library.Models.Book;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//public class RecommendationEngine {
//    private RecommendationHelper recommendationHelper;
//    private DatabaseHelper dbHelper;
//
//    public RecommendationEngine(RecommendationHelper recommendationHelper, DatabaseHelper dbHelper) {
//        this.recommendationHelper = recommendationHelper;
//        this.dbHelper = dbHelper;
//    }
//
//    public List<Book> recommendBooks(int userId) {
//        Map<String, Integer> preferences = recommendationHelper.analyzeUserPreferences(userId);
//        List<Book> books = dbHelper.getBookDetails();
//
//        List<Book> recommendedBooks = new ArrayList<>();
//        for (Book book : books) {
//            String author = book.getAuthor();
//            String genre = book.getGenre();
//
//            int score = 0;
//            if (preferences.containsKey("genre_" + genre)) {
//                score += preferences.get("genre_" + genre);
//            }
//            if (preferences.containsKey("author_" + author)) {
//                score += preferences.get("author_" + author);
//            }
//
//            if (score > 0) {
//                recommendedBooks.add(book);
//            }
//        }
//
//        return recommendedBooks;
//    }
//}