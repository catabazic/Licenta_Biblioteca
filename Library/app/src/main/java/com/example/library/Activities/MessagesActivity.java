package com.example.library.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.Adapters.ChatAdapter;
import com.example.library.Database.FirebaseDatabaseHelper;
import com.example.library.Interfaces.OnItemClickListener;
import com.example.library.Models.Chat;
import com.example.library.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

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

    private FirebaseDatabaseHelper dbHelper;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        dbHelper = new FirebaseDatabaseHelper();

        homeActionButton = findViewById(R.id.homeActionButton);
        searchActionButton = findViewById(R.id.searchActionButton);
        booksActionButton = findViewById(R.id.booksActionButton);
        messagesActionButton = findViewById(R.id.messagesActionButton);
        userActionButton = findViewById(R.id.userActionButton);
        addConversation=findViewById(R.id.MessagesAddBtn);

        recyclerView = findViewById(R.id.ListOfMessages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadChats();

        addConversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessagesActivity.this, SearchUserActivity.class));
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        loadChats();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loadChats() {
        dbHelper.getChats(MainActivity.sharedPreferences.getString("user_id", null), chats -> {
            chatList = chats;
            Collections.sort(chatList, new Comparator<Chat>() {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public int compare(Chat c1, Chat c2) {
                    LocalDateTime date1 = LocalDateTime.parse(c1.getDate(), formatter);
                    LocalDateTime date2 = LocalDateTime.parse(c2.getDate(), formatter);
                    return date2.compareTo(date1); // Descending order
                }
            });
            adapter = new ChatAdapter(chatList, this);
            recyclerView.setAdapter(adapter);
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
