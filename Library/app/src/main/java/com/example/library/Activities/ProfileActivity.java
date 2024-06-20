package com.example.library.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.library.Database.FirebaseDatabaseHelper;
import com.example.library.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    FloatingActionButton homeActionButton;
    FloatingActionButton searchActionButton;
    FloatingActionButton booksActionButton;
    FloatingActionButton messagesActionButton;
    FloatingActionButton userActionButton;
    private ImageView profilePhoto;
    Button editProfile;
    Button history;
    Button myBooks;
    Button myRewievs;
    Button logout;
    TextView name;
    private FirebaseDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        dbHelper = new FirebaseDatabaseHelper();

        homeActionButton = findViewById(R.id.homeActionButton);
        searchActionButton = findViewById(R.id.searchActionButton);
        booksActionButton = findViewById(R.id.booksActionButton);
        messagesActionButton = findViewById(R.id.messagesActionButton);
        userActionButton = findViewById(R.id.userActionButton);

        editProfile=findViewById(R.id.profileEditBtn);
        history = findViewById(R.id.profileHistoryBtn);
        myBooks = findViewById(R.id.profileMyBooksBtn);
        myRewievs = findViewById(R.id.profileMyRewiewsBtn);
        logout = findViewById(R.id.LogoutBtn);
        profilePhoto = findViewById(R.id.user_img3);

        name = findViewById(R.id.photoBtn);

        editProfile();

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ProfileActivity.this, ProfileEditActivity.class), 1);
            }
        });
        myRewievs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ReviewsMineActivity.class));
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, HistoryActivity.class);
                intent.putExtra("type", "history");
                startActivity(intent);
            }
        });

        myBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, HistoryActivity.class);
                intent.putExtra("type", "my books");
                startActivity(intent);
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
        dbHelper.getUserById(MainActivity.sharedPreferences.getString("user_id", null), user -> {
            name.setText(user.getName());
            String imageUrl = user.getPhoto();
            if(imageUrl!=null) {
                System.out.println(imageUrl);
                Picasso.get()
                        .load(imageUrl)
                        .into(profilePhoto);
            }else{
                System.out.println("no photo");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            this.editProfile();
        }
    }
}
