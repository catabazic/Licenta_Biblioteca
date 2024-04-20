package com.example.library;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MyBooksActivity extends AppCompatActivity {
    FloatingActionButton homeActionButton;
    FloatingActionButton searchActionButton;
    FloatingActionButton booksActionButton;
    FloatingActionButton messagesActionButton;
    FloatingActionButton userActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);

        homeActionButton = findViewById(R.id.homeActionButton);
        searchActionButton = findViewById(R.id.searchActionButton);
        booksActionButton = findViewById(R.id.booksActionButton);
        messagesActionButton = findViewById(R.id.messagesActionButton);
        userActionButton = findViewById(R.id.userActionButton);

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
