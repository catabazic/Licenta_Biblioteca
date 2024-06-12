package com.example.library.Recomandation;


import com.example.library.Database.FirebaseDatabaseHelper;
import com.example.library.Database.FirestoreCallback;
import com.example.library.Models.DB.Author;
import com.example.library.Models.DB.Book;
import com.example.library.Models.DB.Genre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class Recommender {
    private FirebaseDatabaseHelper dbHelper;

    public Recommender(){
        dbHelper = new FirebaseDatabaseHelper();
    }

    public double calculateSimilarityAuthor(List<Author> userProfile, Set<String> authors){
        Map<String, Integer> authorProfile = new HashMap<>();
        for(String author : authors){
            authorProfile.put(author, authorProfile.getOrDefault(author, 0) + 1);
        }

        double dotProduct = 0.0;
        for(String id : authorProfile.keySet()){
            for(Author author : userProfile){
                if(Objects.equals(author.getId(), id)){
                    dotProduct += authorProfile.get(id);
                }
            }
        }

        double userProfileMagnitude = userProfile.size();
        userProfileMagnitude = Math.sqrt(userProfileMagnitude);

        double authorProfileMagnitude = authorProfile.size();
        authorProfileMagnitude = Math.sqrt(authorProfileMagnitude);

        if(authorProfileMagnitude == 0 || userProfileMagnitude == 0){
            return 0.0;
        }else{
            return dotProduct / (authorProfileMagnitude * userProfileMagnitude);
        }
    }

    public double calculateSimilarityGenre(List<Genre> userProfile, Set<String> genres){
        Map<String, Integer> genreProfile = new HashMap<>();
        for(String genre : genres){
            genreProfile.put(genre, genreProfile.getOrDefault(genre, 0) + 1);
        }

        double dotProduct = 0.0;
        for(String id : genreProfile.keySet()){
            for(Genre genre : userProfile){
                if(Objects.equals(genre.getId(), id)){
                    dotProduct += genreProfile.get(id);
                }
            }
        }

        double userProfileMagnitude = userProfile.size();
        userProfileMagnitude = Math.sqrt(userProfileMagnitude);

        double genreProfileMagnitude = genreProfile.size();
        genreProfileMagnitude = Math.sqrt(genreProfileMagnitude);

        if(genreProfileMagnitude == 0 || userProfileMagnitude == 0){
            return 0.0;
        }else{
            return dotProduct / (genreProfileMagnitude * userProfileMagnitude);
        }
    }


    public void recommendedBooks(String userId, FirestoreCallback<List<Book>> callback){
        Map<Book, Double> similarityScores = new HashMap<>();

        CompletableFuture<List<Book>> allBooksFuture = new CompletableFuture<>();
        dbHelper.getAllBooks().addOnSuccessListener(allBooksFuture::complete);

        CompletableFuture<List<Author>> userAuthorsFuture = new CompletableFuture<>();
        dbHelper.getPreferencesAuthors(userId, userAuthorsFuture::complete);

        CompletableFuture<List<Genre>> userGenresFuture = new CompletableFuture<>();
        dbHelper.getPreferencesGenre(userId, userGenresFuture::complete);

        CompletableFuture.allOf(allBooksFuture, userAuthorsFuture, userGenresFuture).thenRun(() -> {
            List<Book> books = allBooksFuture.join();
            List<Author> userAuthors = userAuthorsFuture.join();
            List<Genre> userGenres = userGenresFuture.join();

            for (Book book : books) {
                double similarity = calculateSimilarityAuthor(userAuthors, book.getAuthors()) +
                        calculateSimilarityGenre(userGenres, book.getGenres());
                similarityScores.put(book, similarity);
            }

            List<Map.Entry<Book, Double>> sortedBooks = new ArrayList<>(similarityScores.entrySet());
            sortedBooks.sort((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()));

            List<Book> recommendedBooks = new ArrayList<>();
            for (Map.Entry<Book, Double> entry : sortedBooks) {
                recommendedBooks.add(entry.getKey());
            }

            callback.onComplete(recommendedBooks);
        });


    }
}
