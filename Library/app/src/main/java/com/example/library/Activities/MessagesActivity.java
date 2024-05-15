package com.example.library.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.Adapters.BookAdapter;
import com.example.library.Adapters.ChatAdapter;
import com.example.library.Database.DatabaseHelper;
import com.example.library.Interfaces.OnItemClickListener;
import com.example.library.Models.Book;
import com.example.library.Models.Chat;
import com.example.library.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MessagesActivity extends AppCompatActivity implements OnItemClickListener {
    FloatingActionButton homeActionButton;
    FloatingActionButton searchActionButton;
    FloatingActionButton booksActionButton;
    FloatingActionButton messagesActionButton;
    FloatingActionButton userActionButton;

    private ImageButton addConversation;
    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private List<Chat> chatList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        homeActionButton = findViewById(R.id.homeActionButton);
        searchActionButton = findViewById(R.id.searchActionButton);
        booksActionButton = findViewById(R.id.booksActionButton);
        messagesActionButton = findViewById(R.id.messagesActionButton);
        userActionButton = findViewById(R.id.userActionButton);
        addConversation=findViewById(R.id.MessagesAddBtn);


        recyclerView = findViewById(R.id.ListOfMessages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DatabaseHelper dbHelper = new DatabaseHelper(MessagesActivity.this);
        chatList = dbHelper.getChats(MainActivity.sharedPreferences.getInt("user_id", -1));

        adapter = new ChatAdapter(chatList, this);
        recyclerView.setAdapter(adapter);

        addConversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

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

    @Override
    public void onItemClick(Object object) {
        Chat thisChat = (Chat) object;
        Intent intent = new Intent(MessagesActivity.this, ChatActivity.class);
        intent.putExtra("chat",thisChat);
        startActivity(intent);
    }
}
