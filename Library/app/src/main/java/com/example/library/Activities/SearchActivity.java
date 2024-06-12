package com.example.library.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.Adapters.SearchBookAdapter;
import com.example.library.Database.FirebaseDatabaseHelper;
import com.example.library.Database.FirestoreCallback;
import com.example.library.Interfaces.OnItemClickListener;
import com.example.library.Models.DB.Author;
import com.example.library.Models.DB.Book;
import com.example.library.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements OnItemClickListener {
    FloatingActionButton homeActionButton;
    FloatingActionButton searchActionButton;
    FloatingActionButton booksActionButton;
    FloatingActionButton messagesActionButton;
    FloatingActionButton userActionButton;
    private SearchView searchView;
    private RecyclerView listView;
    private SearchBookAdapter adapter;
    private List<Book> bookList;
    private FirebaseDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        homeActionButton = findViewById(R.id.homeActionButton);
        searchActionButton = findViewById(R.id.searchActionButton);
        booksActionButton = findViewById(R.id.booksActionButton);
        messagesActionButton = findViewById(R.id.messagesActionButton);
        userActionButton = findViewById(R.id.userActionButton);

        searchView = findViewById(R.id.searchBar);
        searchView.clearFocus();
        listView = findViewById(R.id.SearchResults);
        listView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new FirebaseDatabaseHelper();

        bookList = new ArrayList<>();
        adapter = new SearchBookAdapter(bookList, this);
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dbHelper.getBooksSearch(query).addOnSuccessListener( l ->{
                    System.out.println("It s all right");
                    for (Book book : l) {
                        System.out.println(book.getName());
                    }
                    adapter.updateData(l);
                }).addOnFailureListener(l->{
                    System.out.println("there is something wrong");
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                dbHelper.getBooksSearch(newText).addOnSuccessListener( l ->{
                    System.out.println("It s all right");
                    System.out.println(l.size());
                    for (Book book : l) {
                        System.out.println(book.getName());
                    }
                    adapter.updateData(l);
                }).addOnFailureListener(l->{
                    System.out.println("there is something wrong");
                });
                System.out.println("I am out");
                return true;
            }
        });

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
        });
        homeActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this, HomeActivity.class));
                finish();
            }
        });

        searchActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this, SearchActivity.class));
                finish();            }
        });

        booksActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this, MyBooksActivity.class));
                finish();
            }
        });

        messagesActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this, MessagesActivity.class));
                finish();
            }
        });

        userActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this, ProfileActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onItemClick(Object object) {
        Book thisBook = (Book) object;
        Intent intent = new Intent(SearchActivity.this, SelectedBookActivity.class);
        intent.putExtra("book",thisBook);
        startActivity(intent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof SearchView) {
                v.clearFocus();
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private void searchBooks(String query) {

    }
}
