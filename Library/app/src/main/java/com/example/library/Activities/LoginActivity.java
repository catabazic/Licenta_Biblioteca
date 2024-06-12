package com.example.library.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.library.Database.FirebaseDatabaseHelper;
import com.example.library.Database.FirestoreCallback;
import com.example.library.R;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button OtherChoice;
    private FirebaseDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //DatabaseHelper databaseHelper = new DatabaseHelper(LoginActivity.this);
        dbHelper = new FirebaseDatabaseHelper();

        usernameEditText = findViewById(R.id.EmailLoginTxt);
        passwordEditText = findViewById(R.id.PasswordLoginTxt);
        loginButton = findViewById(R.id.LoginBtn);
        OtherChoice = findViewById(R.id.LoginChoiceBtn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                System.out.println("Username: " + username + ", password: " + password);

                // Check credentials (e.g., against local database or server)
                isValidCredentials(username, password, new FirestoreCallback<Boolean>() {
                    @Override
                    public void onComplete(Boolean result) {
                        if(result){
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
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


    private void isValidCredentials(String username, String password, FirestoreCallback<Boolean> callback) {
         dbHelper.authentificateUser(username, password).addOnSuccessListener( id ->{
                if (id!=null) {
                    SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
                    editor.putBoolean("isLoggedIn",true);
                    editor.putString("user_id", id);
                    editor.apply();
                    callback.onComplete(true);
                }else{
                    callback.onComplete(false);
                }
        });
    }
}
