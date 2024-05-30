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
//import com.example.library.Recomandation.RecommendationEngine;
//import com.example.library.Recomandation.RecommendationHelper;
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
    List<Book> recommBooks;
    RecyclerView recommRecyclerView;
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


//        RecommendationHelper recommendationHelper = new RecommendationHelper(dbHelper);
//        RecommendationEngine recommendationEngine = new RecommendationEngine(recommendationHelper, dbHelper);
//        recommBooks = recommendationEngine.recommendBooks(MainActivity.sharedPreferences.getInt("user_id",-1));

        homeActionButton = findViewById(R.id.homeActionButton);
        searchActionButton = findViewById(R.id.searchActionButton);
        booksActionButton = findViewById(R.id.booksActionButton);
        messagesActionButton = findViewById(R.id.messagesActionButton);
        userActionButton = findViewById(R.id.userActionButton);

//        recommRecyclerView = findViewById(R.id.RecomendedBooksRecyclerView);
//        recommRecyclerView.setHasFixedSize(true);
//        recommRecyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL,false));
//        LibraryBookAdapter adapterRecomm = new LibraryBookAdapter(recommBooks,this);
//        recommRecyclerView.setAdapter(adapterRecomm);


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
    public void onItemClick(Object book) {
        Book thisBook = (Book) book;
        Intent intent = new Intent(HomeActivity.this, SelectedBookActivity.class);
        intent.putExtra("book",thisBook);
        startActivity(intent);

    }
}
