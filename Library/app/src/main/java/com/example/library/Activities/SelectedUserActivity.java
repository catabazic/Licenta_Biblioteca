package com.example.library.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.library.Database.FirebaseDatabaseHelper;
import com.example.library.Models.DB.User;
import com.example.library.R;
import com.squareup.picasso.Picasso;

public class SelectedUserActivity extends AppCompatActivity {
    private ImageButton backButton;
    private User user;
    private TextView name;
    private TextView email;
    private TextView genres;
    private TextView authors;
    private Button addConv;
    private FirebaseDatabaseHelper dbHelper;
    private ImageView profilePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_user);

        Intent intent = getIntent();
        if(intent != null) {
            user = (User) intent.getSerializableExtra("user");
        }

        backButton = findViewById(R.id.backBtn);
        name = findViewById(R.id.nameTxt);
        email = findViewById(R.id.emailTxt);
        genres = findViewById(R.id.preferedGenresTxt);
        authors = findViewById(R.id.preferedAuthorsTxt);
        addConv = findViewById(R.id.addBtn);
        dbHelper = new FirebaseDatabaseHelper();
        profilePhoto = findViewById(R.id.user_img3);

        updateData();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addConv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.addChat(user.getId(), MainActivity.sharedPreferences.getString("user_id", null)).addOnSuccessListener(chat ->{
                    chat.setName(user.getName());
                    Intent intent = new Intent(SelectedUserActivity.this, ChatActivity.class);
                    intent.putExtra("chat",chat);
                    startActivity(intent);
                    finish();
                });
            }
        });

    }

    private void updateData(){
        String imageUrl = user.getPhoto();
        if(imageUrl!=null) {
            System.out.println(imageUrl);
            Picasso.get()
                    .load(imageUrl)
                    .into(profilePhoto);
        }else{
            System.out.println("no photo");
        }
        name.setText(user.getName());
        email.setText(user.getEmail());
        dbHelper.getPreferencesAuthors(user.getId(),authorList ->{
            if(!authorList.isEmpty()) {
                StringBuilder authorStr = new StringBuilder("Pref authors: ");
                for (int i = 0; i < authorList.size(); i++) {
                    authorStr.append(authorList.get(i).getName());
                    if (i < authorList.size() - 1) {
                        authorStr.append(", ");
                    }
                }
                authors.setText(authorStr);
            }else {
                authors.setText("");
            }
        });

        dbHelper.getPreferencesGenre(user.getId(), genreList ->{
            if(!genreList.isEmpty()){
                StringBuilder genreStr = new StringBuilder("Pref genres: ");
                for (int i = 0; i < genreList.size(); i++) {
                    genreStr.append(genreList.get(i).getName());
                    if (i < genreList.size() - 1) {
                        genreStr.append(", ");
                    }
                }
                genres.setText(genreStr);
            }else {
                genres.setText("");
            }
        });
    }
}
