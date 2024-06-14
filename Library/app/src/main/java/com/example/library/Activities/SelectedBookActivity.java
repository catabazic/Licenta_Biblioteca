package com.example.library.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.library.Database.FirebaseDatabaseHelper;
import com.example.library.Models.DB.Author;
import com.example.library.Models.DB.Book;
import com.example.library.Models.DB.Genre;
import com.example.library.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

public class SelectedBookActivity extends AppCompatActivity {
    FloatingActionButton homeActionButton;
    FloatingActionButton searchActionButton;
    FloatingActionButton booksActionButton;
    FloatingActionButton messagesActionButton;
    FloatingActionButton userActionButton;
    ImageButton backButton;
    private Book book;
    private TextView chatNameTxt;
    private TextView BookNameTxt;
    private TextView BookAuthorTxt;
    private TextView BookGenreTxt;
    private TextView BookAvailabilityTxt;
    private TextView BookDescriptionTxt;
    private Button reservButton;

    private TextView ratingNote;
    private TextView ratingNumber;
    private TextView seeAllRatings;
    private ProgressBar rating5;
    private ProgressBar rating4;
    private ProgressBar rating3;
    private ProgressBar rating2;
    private ProgressBar rating1;
    private ImageView imageView;

    private RatingBar ratingBar;
    private FirebaseDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_book);

        Intent intent = getIntent();
        if(intent != null) {
            book = (Book) intent.getSerializableExtra("book");
            System.out.println("we are here : " + book.getName());
        }

        homeActionButton = findViewById(R.id.homeActionButton);
        searchActionButton = findViewById(R.id.searchActionButton);
        booksActionButton = findViewById(R.id.booksActionButton);
        messagesActionButton = findViewById(R.id.messagesActionButton);
        userActionButton = findViewById(R.id.userActionButton);
        imageView = findViewById(R.id.imageView2);

        backButton=findViewById(R.id.backBtn);

        ratingNote=findViewById(R.id.BookRating);
        ratingNumber=findViewById(R.id.bookRatingNumber);
        seeAllRatings=findViewById(R.id.SeeAllRatingsTxt);
        ratingBar=findViewById(R.id.ratingBar);
        rating5=findViewById(R.id.progressBar5);
        rating4=findViewById(R.id.progressBar4);
        rating3=findViewById(R.id.progressBar3);
        rating2=findViewById(R.id.progressBar2);
        rating1=findViewById(R.id.progressBar1);


        chatNameTxt = findViewById(R.id.nameTxt);
        BookNameTxt = findViewById(R.id.BookNameTxt);
        BookAuthorTxt = findViewById(R.id.BookAuthorTxt);
        BookGenreTxt = findViewById(R.id.BookGenreTxt);
        BookAvailabilityTxt = findViewById(R.id.BookAvailabilityTxt);
        BookDescriptionTxt = findViewById(R.id.BookDescriptionTxt);
        reservButton = findViewById(R.id.BookReservationBtn);

        dbHelper = new FirebaseDatabaseHelper();
        updateUI(book);
        dbHelper.isBookBorrowed(MainActivity.sharedPreferences.getString("user_id",null),book.getId()).addOnSuccessListener(b->{
            if(b){
                reservButton.setText("Read Book");
            }
        });
        System.out.println("we know if book is borrowed");


        seeAllRatings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectedBookActivity.this, ReviewsBookAllActivity.class);
                intent.putExtra("book",book);
                startActivityForResult(intent,1);
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Intent intent = new Intent(SelectedBookActivity.this, ReviewBookActivity.class);
                intent.putExtra("book",book);
                startActivityForResult(intent, 1);
            }
        });

        reservButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                dbHelper.isBookBorrowed(MainActivity.sharedPreferences.getString("user_id",null),book.getId()).addOnSuccessListener(b->{
                    if(b) {

                    }else{
                        dbHelper.borrowBook(book.getId(), MainActivity.sharedPreferences.getString("user_id",null));
                        reservButton.setText("Read Book");
                        String disp = "Disponibilitate: " + String.valueOf(book.getDisponible()-1);
                        BookAvailabilityTxt.setText(disp);
                    }
                });
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        homeActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectedBookActivity.this, HomeActivity.class));
                finish();
            }
        });

        searchActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectedBookActivity.this, SearchActivity.class));
                finish();            }
        });

        booksActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectedBookActivity.this, MyBooksActivity.class));
                finish();
            }
        });

        messagesActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectedBookActivity.this, MessagesActivity.class));
                finish();
            }
        });

        userActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectedBookActivity.this, ProfileActivity.class));
                finish();
            }
        });

        System.out.println("we are at the end");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            this.updateUI(book);
        }
    }

    private void updateUI(Book book) {
        // Update text views immediately if possible
        chatNameTxt.setText(book.getName());
        BookNameTxt.setText(book.getName());
        BookAvailabilityTxt.setText("Disponibilitate: " + book.getDisponible());
        BookDescriptionTxt.setText(book.getDescription());

        String imageUrl = book.getImage();
        if(imageUrl!=null) {
            System.out.println(imageUrl);
            Picasso.get()
                    .load(imageUrl)
                    .into(imageView);
        }else{
            System.out.println("no photo");
        }

        Set<String> authors = book.getAuthors();
        Set<String> genres = book.getGenres();

        StringBuilder autBuilder = new StringBuilder();
        List<CompletableFuture<Author>> futures = new ArrayList<>();

        // Collect all futures
        for (String id : authors) {
            CompletableFuture<Author> futureAuthor = new CompletableFuture<>();
            dbHelper.getAuthorById(id, futureAuthor::complete);
            futures.add(futureAuthor);
        }

        // Combine all futures
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).thenRun(() -> {
            for (CompletableFuture<Author> future : futures) {
                try {
                    Author author = future.get();
                    if (autBuilder.length() > 0) {
                        autBuilder.append(" & ");
                    }
                    autBuilder.append(author.getName());
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            BookAuthorTxt.setText(autBuilder.toString());
        });

        StringBuilder gen = new StringBuilder();
        List<CompletableFuture<Genre>> futuresG = new ArrayList<>();

        // Collect all futures
        for (String id : genres) {
            CompletableFuture<Genre> futureGenre = new CompletableFuture<>();
            dbHelper.getGenreById(id, futureGenre::complete);
            futuresG.add(futureGenre);
        }

        // Combine all futures
        CompletableFuture.allOf(futuresG.toArray(new CompletableFuture[0])).thenRun(() -> {
            for (CompletableFuture<Genre> future : futuresG) {
                try {
                    Genre genre = future.get();
                    if (gen.length() > 0) {
                        gen.append(" & ");
                    }
                    gen.append(genre.getName());
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            BookGenreTxt.setText(gen.toString());
        });

        // Fetch and update rating and review details
        dbHelper.getRatingOfBook(book.getId()).addOnSuccessListener(f -> {
            BigDecimal bd = new BigDecimal(f).setScale(2, RoundingMode.HALF_UP);
            ratingNote.setText(String.valueOf(bd.floatValue()));
        });
        System.out.println("we set ratings");

        dbHelper.getNumberOfRatings(book.getId()).addOnSuccessListener(l -> {
            if (l.size() >= 6) {
                ratingNumber.setText(l.get(0) + " Reviews");
                rating1.setProgress(l.get(1));
                rating2.setProgress(l.get(2));
                rating3.setProgress(l.get(3));
                rating4.setProgress(l.get(4));
                rating5.setProgress(l.get(5));
            }
            System.out.println("we are inside of ratings");
        });
        System.out.println("we set ratings x2");
    }


}
