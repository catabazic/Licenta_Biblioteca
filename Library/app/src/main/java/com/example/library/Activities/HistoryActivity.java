package com.example.library.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.Adapters.BookAdapter;
import com.example.library.Database.DatabaseHelper;
import com.example.library.Interfaces.OnItemClickListener;
import com.example.library.Models.Book;
import com.example.library.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HistoryActivity extends AppCompatActivity implements OnItemClickListener {
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
        setContentView(R.layout.activity_history);

        homeActionButton = findViewById(R.id.homeActionButton);
        searchActionButton = findViewById(R.id.searchActionButton);
        booksActionButton = findViewById(R.id.booksActionButton);
        messagesActionButton = findViewById(R.id.messagesActionButton);
        userActionButton = findViewById(R.id.userActionButton);
        recyclerView = findViewById(R.id.booksHistoryRecView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*bookList = new ArrayList<>();
        bookList.add(new Book("Litera stacojie", "NATHANIEL HAWTHORNE", "Stare: Imprumut pana pe data de 15.06.2024"));
        bookList.add(new Book("Another Book", "Another Author", "Another State"));*/


        DatabaseHelper dbHelper = new DatabaseHelper(HistoryActivity.this);
        bookList = dbHelper.getAllHistory(MainActivity.sharedPreferences.getInt("user_id", -1));

        adapter = new BookAdapter(bookList, this);
        recyclerView.setAdapter(adapter);

        homeActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HistoryActivity.this, HomeActivity.class));
                finish();
            }
        });

        searchActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HistoryActivity.this, SearchActivity.class));
                finish();
            }
        });

        booksActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If you're already in MyBooksActivity, you might not want to restart it
                // You can handle this case differently if needed
                startActivity(new Intent(HistoryActivity.this, MyBooksActivity.class));
                finish();
            }
        });

        messagesActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HistoryActivity.this, MessagesActivity.class));
                finish();
            }
        });

        userActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HistoryActivity.this, ProfileActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onItemClick(Book book) {
        DatabaseHelper dbHelper = new DatabaseHelper(HistoryActivity.this);
        dbHelper.getBookByNameAndAuthor(book.getName(), book.getAuthor());
        Intent intent = new Intent(HistoryActivity.this, SelectedBookActivity.class);
        intent.putExtra("book", book);
//        intent.putExtra("fromPage", this);
        startActivity(intent);
    }

}