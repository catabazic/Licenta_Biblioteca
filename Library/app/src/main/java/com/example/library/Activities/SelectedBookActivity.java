package com.example.library.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.library.Database.DatabaseHelper;
import com.example.library.Models.Book;
import com.example.library.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_book);

        Intent intent = getIntent();
        if(intent != null) {
            book = (Book) intent.getSerializableExtra("book");
//            fromPage = intent.getSerializableExtra("fromPage");

        }

        homeActionButton = findViewById(R.id.homeActionButton);
        searchActionButton = findViewById(R.id.searchActionButton);
        booksActionButton = findViewById(R.id.booksActionButton);
        messagesActionButton = findViewById(R.id.messagesActionButton);
        userActionButton = findViewById(R.id.userActionButton);

        backButton=findViewById(R.id.bookBackBtn);

        chatNameTxt = findViewById(R.id.chatNameTxt);
        BookNameTxt = findViewById(R.id.BookNameTxt);
        BookAuthorTxt = findViewById(R.id.BookAuthorTxt);
        BookGenreTxt = findViewById(R.id.BookGenreTxt);
        BookAvailabilityTxt = findViewById(R.id.BookAvailabilityTxt);
        BookDescriptionTxt = findViewById(R.id.BookDescriptionTxt);
        reservButton = findViewById(R.id.BookReservationBtn);

        updateUI(book);

        reservButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                DatabaseHelper dbHelper = new DatabaseHelper(SelectedBookActivity.this);
                dbHelper.borrowBook(book.getId(),MainActivity.sharedPreferences.getInt("user_id",-1));
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
        // Update chat name (if needed)
        chatNameTxt.setText(book.getName());

        // Update book details
        BookNameTxt.setText(book.getName());
        BookAuthorTxt.setText(book.getAuthor());
        BookGenreTxt.setText(book.getGenre());
        BookAvailabilityTxt.setText("Disponibilitate: " + book.getDisponible());
        BookDescriptionTxt.setText(book.getDescription());


    }
}
