package com.example.library;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MessagesActivity extends AppCompatActivity {
    FloatingActionButton homeActionButton = findViewById(R.id.homeActionButton);
    FloatingActionButton searchActionButton = findViewById(R.id.searchActionButton);
    FloatingActionButton booksActionButton = findViewById(R.id.booksActionButton);
    FloatingActionButton messagesActionButton = findViewById(R.id.messagesActionButton);
    FloatingActionButton userActionButton = findViewById(R.id.userActionButton);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        homeActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessagesActivity.this, HomeActivity.class));
                finish();
            }
        });

        searchActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessagesActivity.this, SearchActivity.class));
                finish();            }
        });

        booksActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessagesActivity.this, MyBooksActivity.class));
                finish();
            }
        });

        messagesActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessagesActivity.this, MessagesActivity.class));
                finish();
            }
        });

        userActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessagesActivity.this, ProfileActivity.class));
                finish();
            }
        });
    }
}
