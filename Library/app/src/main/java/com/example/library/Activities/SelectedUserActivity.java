package com.example.library.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.library.Database.DatabaseHelper;
import com.example.library.Models.Book;
import com.example.library.Models.Chat;
import com.example.library.Models.User;
import com.example.library.R;

public class SelectedUserActivity extends AppCompatActivity {
    private ImageButton backButton;
    private User user;
    private TextView name;
    private TextView email;
    private TextView phone;
    private Button addConv;
    private DatabaseHelper dbHelper;

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
        phone = findViewById(R.id.phoneTxt);
        addConv = findViewById(R.id.addBtn);
        dbHelper = new DatabaseHelper(this);

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
                Chat chat = dbHelper.addChat(user.getId(), MainActivity.sharedPreferences.getInt("user_id", -1));
                chat.setName(user.getName());
                Intent intent = new Intent(SelectedUserActivity.this, ChatActivity.class);
                intent.putExtra("chat",chat);
                startActivity(intent);
                finish();
            }
        });

    }

    private void updateData(){
        name.setText(user.getName());
        email.setText(user.getEmail());
        phone.setText(user.getPhoto());
    }
}
