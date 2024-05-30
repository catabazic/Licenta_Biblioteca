package com.example.library.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.library.Database.DatabaseHelper;
import com.example.library.Models.User;
import com.example.library.R;

public class ProfileEditActivity extends AppCompatActivity {
    private TextView back;
    private TextView send;
    private TextView photo;
    private EditText nume;
    private TextView numar;
    private TextView email;
    private TextView numarBtn;
    private TextView emailBtn;
    private Button passwordBtn;
    private Button logoutBtn;
    private User user;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_edit);

        back = findViewById(R.id.BackTxt);
        send = findViewById(R.id.SentTxt);
        photo = findViewById(R.id.profilePhotoBtn);
        nume = findViewById(R.id.nameEditText);
        numar = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        numarBtn = findViewById(R.id.phoneBtn);
        emailBtn = findViewById(R.id.emailBtn);
        passwordBtn = findViewById(R.id.passwordChange);
        logoutBtn = findViewById(R.id.LogoutBtn);

        dbHelper = new DatabaseHelper(ProfileEditActivity.this);
        user = dbHelper.getUserById(MainActivity.sharedPreferences.getInt("user_id",-1));

        this.chengeData();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        numarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileEditActivity.this, ChangeEmailActivity.class));
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
    }

    private void chengeData(){
        numar.setText(user.getPhoto());
        email.setText(user.getEmail());
        nume.setText(user.getName());
    }
}
