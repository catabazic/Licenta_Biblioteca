package com.example.library.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.Adapters.SearchUserAdapter;

import com.example.library.Database.FirebaseDatabaseHelper;
import com.example.library.Interfaces.OnItemClickListener;
import com.example.library.Models.DB.Book;
import com.example.library.Models.DB.User;
import com.example.library.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchUserActivity extends Activity implements OnItemClickListener {
    private SearchView searchView;
    private RecyclerView listView;
    private ImageButton backButton;
    private SearchUserAdapter adapter;
    private List<User> userList;
    private FirebaseDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        backButton = findViewById(R.id.backBtn);

        searchView = findViewById(R.id.searchBar);
        searchView.clearFocus();
        listView = findViewById(R.id.SearchResults);
        listView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new FirebaseDatabaseHelper();

        userList = new ArrayList<>();
        adapter = new SearchUserAdapter(userList, this);
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dbHelper.getUsersSearch(query, MainActivity.sharedPreferences.getString("user_id", null)).addOnSuccessListener(l->{
                    System.out.println("It s all right");
                    System.out.println(l.size());
                    for (User user : l) {
                        System.out.println(user.getName());
                    }
                    adapter.updateData(l);
                }).addOnFailureListener(e->{
                    System.out.println(e);
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                dbHelper.getUsersSearch(newText, MainActivity.sharedPreferences.getString("user_id", null)).addOnSuccessListener(l->{
                    System.out.println("It s all right");
                    System.out.println(l.size());
                    for (User book : l) {
                        System.out.println(book.getName());
                    }
                    adapter.updateData(l);
                }).addOnFailureListener(e->{
                    System.out.println(e);
                });
                return true;
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public void onItemClick(Object object) {
        User thisUser = (User) object;
        Intent intent = new Intent(SearchUserActivity.this, SelectedUserActivity.class);
        intent.putExtra("user", thisUser);
        intent.putExtra("page", "search");
        startActivity(intent);
        finish();
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

}
