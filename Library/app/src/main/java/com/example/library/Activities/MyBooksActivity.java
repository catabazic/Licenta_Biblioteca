package com.example.library.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.Database.DatabaseHelper;
import com.example.library.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import com.example.library.Adapters.BookAdapter;
import com.example.library.Models.Book;

public class MyBooksActivity extends AppCompatActivity {
    FloatingActionButton homeActionButton;
    FloatingActionButton searchActionButton;
    FloatingActionButton booksActionButton;
    FloatingActionButton messagesActionButton;
    FloatingActionButton userActionButton;

    private RecyclerView recyclerView; //booksRecView
    private BookAdapter adapter;
    private List<Book> bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);

        homeActionButton = findViewById(R.id.homeActionButton);
        searchActionButton = findViewById(R.id.searchActionButton);
        booksActionButton = findViewById(R.id.booksActionButton);
        messagesActionButton = findViewById(R.id.messagesActionButton);
        userActionButton = findViewById(R.id.userActionButton);
        recyclerView = findViewById(R.id.booksRecView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*bookList = new ArrayList<>();
        bookList.add(new Book("Litera stacojie", "NATHANIEL HAWTHORNE", "Stare: Imprumut pana pe data de 15.06.2024"));
        bookList.add(new Book("Another Book", "Another Author", "Another State"));*/


        DatabaseHelper dbHelper = new DatabaseHelper(MyBooksActivity.this);
        bookList = dbHelper.getBorrowedBooks(MainActivity.sharedPreferences.getInt("user_id",-1));

        adapter = new BookAdapter(bookList);
        recyclerView.setAdapter(adapter);

        homeActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyBooksActivity.this, HomeActivity.class));
                finish();
            }
        });

        searchActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyBooksActivity.this, SearchActivity.class));
                finish();
            }
        });

        booksActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If you're already in MyBooksActivity, you might not want to restart it
                // You can handle this case differently if needed
                startActivity(new Intent(MyBooksActivity.this, MyBooksActivity.class));
                finish();
            }
        });

        messagesActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyBooksActivity.this, MessagesActivity.class));
                finish();
            }
        });

        userActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyBooksActivity.this, ProfileActivity.class));
                finish();
            }
        });
    }
}
