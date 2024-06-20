package com.example.library.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.library.Database.FirebaseDatabaseHelper;
import com.example.library.R;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private Button registerButton;
    public static final String SHARED_PREFS="com.example.library";
    public static SharedPreferences sharedPreferences;
    private FirebaseDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new FirebaseDatabaseHelper();
//        dbHelper.insertMockData();
//        scheduleNotification(this);

//        Intent serviceIntent = new Intent(this, MyBackgroundService.class);
//        startService(serviceIntent);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        dbHelper.getUserById(MainActivity.sharedPreferences.getString("user_id", null), user -> {
            if(user==null){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn",false);
                editor.putString("user_id", null);
                editor.apply();
            }
            boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
            if (isLoggedIn) {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                finish();
            }
        });


        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
//
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }


}