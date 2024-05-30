package com.example.library.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.library.Database.DatabaseHelper;
import com.example.library.Models.Book;
import com.example.library.Models.User;
import com.example.library.R;

public class ChangeCheck extends AppCompatActivity {

    private TextView back;
    private EditText number;
    private Button button;
    private DatabaseHelper dbHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_check);
        dbHelper = new DatabaseHelper(this);

        back = findViewById(R.id.BackTxt);
        number = findViewById(R.id.numberCheck);
        button = findViewById(R.id.buttonCheck);

        String email = null;
        Intent intent = getIntent();
        if(intent != null) {
            email = (String) intent.getSerializableExtra("email");
        }else{
            finish();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String finalEmail = email;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long expirationTimeMillis = 3 * 60 * 1000;
                long storedTimestamp = MainActivity.sharedPreferences.getLong("mailChangeTimestamp", 0);
                long currentTimestamp = System.currentTimeMillis();
                if(currentTimestamp - storedTimestamp < expirationTimeMillis){
                    int storedRandomNumber = MainActivity.sharedPreferences.getInt("mailChange", -1);
                    if(Integer.parseInt(number.getText().toString()) == storedRandomNumber){
                        dbHelper.changeEmail(MainActivity.sharedPreferences.getInt("user_id", -1), finalEmail);
                        Toast.makeText(ChangeCheck.this, "Email is changed!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ChangeCheck.this, ProfileEditActivity.class));
                        finish();
                    }
                }
                SharedPreferences.Editor removeEditor = MainActivity.sharedPreferences.edit();
                removeEditor.remove("mailChange");
                removeEditor.remove("mailChangeTimestamp");
                removeEditor.apply();
                Toast.makeText(ChangeCheck.this, "Email is not changed!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ChangeCheck.this, ChangeCheck.class));
                finish();
            }
        });

    }
}
