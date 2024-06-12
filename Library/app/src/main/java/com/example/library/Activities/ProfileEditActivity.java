package com.example.library.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.library.Database.FirebaseDatabaseHelper;
import com.example.library.Models.DB.User;
import com.example.library.R;

public class ProfileEditActivity extends AppCompatActivity {
    private TextView back;
    private TextView send;
    private TextView photo;
    private EditText nume;
    private TextView numar;
    private TextView genres;
    private TextView authors;
    private TextView changePreferences;
    private TextView email;
    private TextView emailBtn;
    private Button passwordBtn;
    private Button logoutBtn;
    private User user;
    private FirebaseDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_edit);

        back = findViewById(R.id.BackTxt);
        send = findViewById(R.id.SentTxt);
        photo = findViewById(R.id.photoBtn);
        nume = findViewById(R.id.nameEditText);
        numar = findViewById(R.id.phoneEditTxt);
        email = findViewById(R.id.email);
        emailBtn = findViewById(R.id.emailBtn);
        passwordBtn = findViewById(R.id.passwordChange);
        logoutBtn = findViewById(R.id.LogoutBtn);
        genres = findViewById(R.id.genresTxt);
        authors = findViewById(R.id.authorsTxt);
        changePreferences = findViewById(R.id.changePreferences);

        dbHelper = new FirebaseDatabaseHelper();

        dbHelper.getUserById(MainActivity.sharedPreferences.getString("user_id", null), u -> {
            user = u;
            this.chengeData();
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nume.getText().toString();
                String number = numar.getText().toString();
                dbHelper.changePhoneNumber(user.getId(), number);
                dbHelper.changeName(user.getId(), name);
                finish();
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileEditActivity.this, ChangeEmailActivity.class);
                startActivity(intent);
            }
        });

        passwordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileEditActivity.this, ChangePasswordActivity.class));
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
                editor.remove("isLoggedIn"); // Remove the isLoggedIn key
                editor.apply();
                startActivity(new Intent(ProfileEditActivity.this, MainActivity.class));
                finish();
            }
        });

        changePreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileEditActivity.this, RegisterSelectActivity.class);
                intent.putExtra("page", "edit");
                startActivity(intent);
            }
        });
    }

    private void chengeData(){
        numar.setText(user.getNumber());
        email.setText(user.getEmail());
        nume.setText(user.getName());
        dbHelper.getPreferencesAuthors(user.getId(), authorList ->{
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
