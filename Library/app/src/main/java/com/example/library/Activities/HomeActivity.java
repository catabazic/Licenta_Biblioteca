package com.example.library.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.Interfaces.OnItemClickListener;
import com.example.library.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.library.Adapters.LibraryBookAdapter;
import com.example.library.Database.DatabaseHelper;
import com.example.library.Models.Book;

import java.io.Serializable;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements OnItemClickListener {
    FloatingActionButton homeActionButton;
    FloatingActionButton searchActionButton;
    FloatingActionButton booksActionButton;
    FloatingActionButton messagesActionButton;
    FloatingActionButton userActionButton;
    List<Book> popularBooks;
    List<Book> newBooks;
    RecyclerView popularRecyclerView;
    RecyclerView newRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        DatabaseHelper dbHelper = new DatabaseHelper(HomeActivity.this);
        /*SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.onUpgrade(db,1,2);*/
        popularBooks = dbHelper.getPopularBooks();
        newBooks = dbHelper.getNewBooks();

        homeActionButton = findViewById(R.id.homeActionButton);
        searchActionButton = findViewById(R.id.searchActionButton);
        booksActionButton = findViewById(R.id.booksActionButton);
        messagesActionButton = findViewById(R.id.messagesActionButton);
        userActionButton = findViewById(R.id.userActionButton);


        popularRecyclerView = findViewById(R.id.PopularBooksRecyclerView);
        popularRecyclerView.setHasFixedSize(true);
        popularRecyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL,false));
        LibraryBookAdapter adapterPopular = new LibraryBookAdapter(popularBooks, this);
        popularRecyclerView.setAdapter(adapterPopular);

        newRecyclerView = findViewById(R.id.NewBooksRecyclerView);
        newRecyclerView.setHasFixedSize(true);
        newRecyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL,false));
        LibraryBookAdapter adapterNew = new LibraryBookAdapter(newBooks, this);
        newRecyclerView.setAdapter(adapterNew);

        homeActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                finish();
            }
        });

        searchActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, SearchActivity.class));
                finish();            }
        });

        booksActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MyBooksActivity.class));
                finish();
            }
        });

        messagesActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MessagesActivity.class));
                finish();
            }
        });

        userActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onItemClick(Book book) {
        /*String bookName = book.getName();
        String author = book.getAuthor();*/
        DatabaseHelper dbHelper = new DatabaseHelper(HomeActivity.this);
        dbHelper.getBookByNameAndAuthor(book.getName(),book.getAuthor());
        Intent intent = new Intent(HomeActivity.this, SelectedBookActivity.class);
        intent.putExtra("book",book);
//        intent.putExtra("fromPage", this);
        startActivity(intent);

    }
}