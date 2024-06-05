package com.example.library.Recomandation;

import com.example.library.Database.DatabaseHelper;
import com.example.library.Models.Author;
import com.example.library.Models.Book;
import com.example.library.Models.Genre;
import com.example.library.Models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Recommender {
    private DatabaseHelper dbHelper;

    public Recommender(DatabaseHelper db){
        dbHelper = db;
    }

    public double calculateSimilarityAuthor(List<Author> userProfile, Set<Author> authors){
        Map<Integer, Integer> authorProfile = new HashMap<>();
        for(Author author : authors){
            authorProfile.put(author.getId(), authorProfile.getOrDefault(author.getId(), 0) + 1);
        }

        double dotProduct = 0.0;
        for(Integer id : authorProfile.keySet()){
            for(Author author : userProfile){
                if(author.getId() == id){
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

    public double calculateSimilarityGenre(List<Genre> userProfile, Set<Genre> genres){
        Map<Integer, Integer> genreProfile = new HashMap<>();
        for(Genre genre : genres){
            genreProfile.put(genre.getId(), genreProfile.getOrDefault(genre.getId(), 0) + 1);
        }

        double dotProduct = 0.0;
        for(Integer id : genreProfile.keySet()){
            for(Genre genre : userProfile){
                if(genre.getId() == id){
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


    public List<Book> recommendedBooks(User user){
        List<Genre> userGenres = dbHelper.getPreferencesGenre(user.getId());
        List<Author> userAuthors = dbHelper.getPreferencesAuthr(user.getId());
        List<Book> books = dbHelper.getAllBooks();
        Map<Book, Double> similarityScores = new HashMap<>();

        for(Book book : books){
            double similarity = calculateSimilarityAuthor(userAuthors,book.getAuthors()) +
                    calculateSimilarityGenre(userGenres, book.getGenres());
            similarityScores.put(book,similarity);
        }

        List<Map.Entry<Book, Double>> sortedBooks = new ArrayList<>(similarityScores.entrySet());
        sortedBooks.sort((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()));

        List<Book> recommendedBooks = new ArrayList<>();
        for (Map.Entry<Book, Double> entry : sortedBooks) {
            recommendedBooks.add(entry.getKey());
        }

        return recommendedBooks;

    }
}
