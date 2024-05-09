package com.example.library.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.library.Database.DatabaseHelper;
import com.example.library.Models.Book;
import com.example.library.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

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

    private RatingBar ratingBar;
    private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_book);

        Intent intent = getIntent();
        if(intent != null) {
            book = (Book) intent.getSerializableExtra("book");
        }

        homeActionButton = findViewById(R.id.homeActionButton);
        searchActionButton = findViewById(R.id.searchActionButton);
        booksActionButton = findViewById(R.id.booksActionButton);
        messagesActionButton = findViewById(R.id.messagesActionButton);
        userActionButton = findViewById(R.id.userActionButton);

        backButton=findViewById(R.id.reviewBackBtn);

        ratingNote=findViewById(R.id.BookRating);
        ratingNumber=findViewById(R.id.bookRatingNumber);
        seeAllRatings=findViewById(R.id.SeeAllRatingsTxt);
        ratingBar=findViewById(R.id.ratingBar);
        rating5=findViewById(R.id.progressBar5);
        rating4=findViewById(R.id.progressBar4);
        rating3=findViewById(R.id.progressBar3);
        rating2=findViewById(R.id.progressBar2);
        rating1=findViewById(R.id.progressBar1);


        chatNameTxt = findViewById(R.id.chatNameTxt);
        BookNameTxt = findViewById(R.id.BookNameTxt);
        BookAuthorTxt = findViewById(R.id.BookAuthorTxt);
        BookGenreTxt = findViewById(R.id.BookGenreTxt);
        BookAvailabilityTxt = findViewById(R.id.BookAvailabilityTxt);
        BookDescriptionTxt = findViewById(R.id.BookDescriptionTxt);
        reservButton = findViewById(R.id.BookReservationBtn);

        dbHelper = new DatabaseHelper(SelectedBookActivity.this);
        updateUI(book);
        if(dbHelper.isBookBorrowed(MainActivity.sharedPreferences.getInt("user_id",-1),book.getId())){
            reservButton.setText("Read Book");
        }


        seeAllRatings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectedBookActivity.this, ReviewsBookAllActivity.class);
                intent.putExtra("book",book);
                startActivity(intent);
                finish();
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Intent intent = new Intent(SelectedBookActivity.this, ReviewBookActivity.class);
                intent.putExtra("book",book);
                startActivity(intent);
            }
        });

        reservButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(dbHelper.isBookBorrowed(MainActivity.sharedPreferences.getInt("user_id",-1),book.getId())){

                }else {
                    dbHelper.borrowBook(book.getId(), MainActivity.sharedPreferences.getInt("user_id", -1));
                    reservButton.setText("Read Book");
                    BookAvailabilityTxt.setText("Disponibilitate: " + String.valueOf(book.getDisponible()-1));
                }
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

    }

    private void updateUI(Book book) {
        chatNameTxt.setText(book.getName());

        BookNameTxt.setText(book.getName());
        BookAuthorTxt.setText(book.getAuthor());
        BookGenreTxt.setText(book.getGenre());
        BookAvailabilityTxt.setText("Disponibilitate: " + book.getDisponible());
        BookDescriptionTxt.setText(book.getDescription());


        ratingNote.setText(String.valueOf(dbHelper.getRatingOfBook(book.getId())));
        List<Integer> list = dbHelper.getNumberOfRatings(book.getId());
        String numberOfRatingsStr = String.valueOf(list.get(0));
        numberOfRatingsStr+=" Reviews";
        ratingNumber.setText(numberOfRatingsStr);
        System.out.println(list.get(0) + ", " + list.get(1) + ", " + list.get(2) + ", " +list.get(3) + ", " +list.get(4) + ", " +list.get(5));
        rating1.setProgress(list.get(1));
        rating2.setProgress(list.get(2));
        rating3.setProgress(list.get(3));
        rating4.setProgress(list.get(4));
        rating5.setProgress(list.get(5));

    }
}
