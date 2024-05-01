package com.example.library.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.library.Database.DatabaseHelper;
import com.example.library.Models.User;
import com.example.library.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileActivity extends AppCompatActivity {
    FloatingActionButton homeActionButton;
    FloatingActionButton searchActionButton;
    FloatingActionButton booksActionButton;
    FloatingActionButton messagesActionButton;
    FloatingActionButton userActionButton;
    Button editProfile;
    Button history;
    Button myBooks;
    Button myRewievs;
    Button parola;
    Button logout;
    TextView name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        homeActionButton = findViewById(R.id.homeActionButton);
        searchActionButton = findViewById(R.id.searchActionButton);
        booksActionButton = findViewById(R.id.booksActionButton);
        messagesActionButton = findViewById(R.id.messagesActionButton);
        userActionButton = findViewById(R.id.userActionButton);

        editProfile=findViewById(R.id.profileEditBtn);
        history = findViewById(R.id.profileHistoryBtn);
        myBooks = findViewById(R.id.profileMyBooksBtn);
        myRewievs = findViewById(R.id.profileMyRewiewsBtn);
        parola = findViewById(R.id.profileParolaBtn);
        logout = findViewById(R.id.profileLogoutBtn);

        name = findViewById(R.id.profileNameTxt);

        editProfile();

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, HistoryActivity.class));
                finish();
            }
        });

        myBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MyBooksActivity.class));
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v){
                 SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
                 editor.remove("isLoggedIn"); // Remove the isLoggedIn key
                 editor.apply();
                 startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                 finish();
            }
        });

        homeActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
                finish();
            }
        });

        searchActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, SearchActivity.class));
                finish();            }
        });

        booksActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MyBooksActivity.class));
                finish();
            }
        });

        messagesActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MessagesActivity.class));
                finish();
            }
        });

        userActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
                finish();
            }
        });
    }

    private void editProfile(){
        DatabaseHelper dbHelper = new DatabaseHelper(ProfileActivity.this);
        User user = dbHelper.getUserById(MainActivity.sharedPreferences.getInt("user_id",-1));
        name.setText(user.getName());
    }
}
