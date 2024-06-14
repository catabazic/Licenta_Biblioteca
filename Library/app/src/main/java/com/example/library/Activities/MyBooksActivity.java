package com.example.library.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.Database.FirebaseDatabaseHelper;
import com.example.library.Interfaces.OnItemClickListener;
import com.example.library.Models.DB.Loan;
import com.example.library.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import com.example.library.Adapters.BookAdapter;
import com.example.library.Models.DB.Book;

public class MyBooksActivity extends AppCompatActivity implements OnItemClickListener {
    private FloatingActionButton homeActionButton;
    private FloatingActionButton searchActionButton;
    private FloatingActionButton booksActionButton;
    private FloatingActionButton messagesActionButton;
    private FloatingActionButton userActionButton;

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private FirebaseDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);

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


        dbHelper = new FirebaseDatabaseHelper();
        dbHelper.getBorrowedBooks(MainActivity.sharedPreferences.getString("user_id",null)).addOnSuccessListener( l->{
            System.out.println(l.size());
            for(Loan loan : l){
                System.out.println(loan.getBookId());
            }
            adapter = new BookAdapter(l, this);
            recyclerView.setAdapter(adapter);
        });


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

    @Override
    public void onItemClick(Object book) {
        Book thisBook = (Book) book;
        Intent intent = new Intent(MyBooksActivity.this, SelectedBookActivity.class);
        intent.putExtra("book",thisBook);
        startActivity(intent);
    }
}
