package com.example.library;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button OtherChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //DatabaseHelper databaseHelper = new DatabaseHelper(LoginActivity.this);

        usernameEditText = findViewById(R.id.EmailLoginTxt);
        passwordEditText = findViewById(R.id.PasswordLoginTxt);
        loginButton = findViewById(R.id.LoginBtn);
        OtherChoice = findViewById(R.id.LoginChoiceBtn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                System.out.println("Üsername: " + username + ", password: " + password);

                // Check credentials (e.g., against local database or server)
                if (isValidCredentials(username, password)) {
                    SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();

                    editor.putBoolean("isLoggedIn",true);
                    editor.apply();
                    // Navigate to home screen
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                } else {
                    // Display error message
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
        OtherChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });


//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }


    private boolean isValidCredentials(String username, String password) {
        try {
            // Initialize DatabaseHelper
            DatabaseHelper dbHelper = new DatabaseHelper(LoginActivity.this);
//            dbHelper.justAdd();
            if (dbHelper.authenticateUser(username, password)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
