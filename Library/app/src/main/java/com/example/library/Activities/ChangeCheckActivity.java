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
import com.example.library.Models.User;
import com.example.library.R;

public class ChangeCheckActivity extends AppCompatActivity {

    private TextView back;
    private EditText number;
    private Button button;
    private TextView text;
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
        text = findViewById(R.id.ReviewTitleTxt);

        String email;
        Intent intent = getIntent();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        email = (String) intent.getSerializableExtra("email");
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
                        if(((String) intent.getSerializableExtra("page")).equals("change")) {
                            dbHelper.changeEmail(MainActivity.sharedPreferences.getInt("user_id", -1), finalEmail);
                            startActivity(new Intent(ChangeCheckActivity.this, ProfileEditActivity.class));
                        }else if(((String) intent.getSerializableExtra("page")).equals("register")){
                            String name = (String) intent.getSerializableExtra("name");
                            String number = (String) intent.getSerializableExtra("number");
                            String password = (String) intent.getSerializableExtra("password");
//                            int id = dbHelper.addNewUser(name, number, email, password);
                            Intent intent = new Intent(ChangeCheckActivity.this, RegisterSelectActivity.class);
                            intent.putExtra("email", email);
                            intent.putExtra("name", name);
                            intent.putExtra("number", number);
                            intent.putExtra("password", password);
                            intent.putExtra("page", "register");
                            startActivity(intent);
                        }
                        finish();
                    }
                }
//                    SharedPreferences.Editor removeEditor = MainActivity.sharedPreferences.edit();
//                    removeEditor.remove("mailChange");
//                    removeEditor.remove("mailChangeTimestamp");
//                    removeEditor.apply();
                Toast.makeText(ChangeCheckActivity.this, "Email is not set!", Toast.LENGTH_SHORT).show();
            }
        });


    }

}