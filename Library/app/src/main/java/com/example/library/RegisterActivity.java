package com.example.library;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText emaildEditText;
    private EditText numberEditText;
    private EditText password1EditText;
    private EditText password2EditText;
    private Button registerButton;
    private Button otherChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        DatabaseHelper dbHelper = new DatabaseHelper(RegisterActivity.this);

        nameEditText = findViewById(R.id.NumeRegisterTxt);
        numberEditText = findViewById(R.id.NumarRegisterTxt);
        emaildEditText = findViewById(R.id.EmailRegisterTxt);
        password1EditText = findViewById(R.id.PasswordRegisterTxt);
        password2EditText = findViewById(R.id.Password2RegisterTxt);
        registerButton = findViewById(R.id.RegisterBtn);
        otherChoice = findViewById(R.id.RegisterChoice);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String number = numberEditText.getText().toString();
                String email = emaildEditText.getText().toString();
                String password1 = password1EditText.getText().toString();
                String password2 = password2EditText.getText().toString();

                if(name.length()>1 && isPhoneNumberCorrect(number) && isEmailCorrect(email) && isPasswordOk(password1,password2)){
                    dbHelper.addNewUser(name,number,email,password1);
                    startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                    finish();
                }
            }
        });
        otherChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private boolean isPhoneNumberCorrect(String number) {
        String regex = "\\+(?:[0-9] ?){6,14}[0-9]";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);
        if(!matcher.matches()){
            Toast.makeText(RegisterActivity.this, "Your phone number is invalid", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean isEmailCorrect(String email){
        try {
            DatabaseHelper dbHelper = new DatabaseHelper(RegisterActivity.this);
            String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            if(!matcher.matches()){
                Toast.makeText(RegisterActivity.this, "Your email is invalid", Toast.LENGTH_SHORT).show();
                return false;
            }
            if(!dbHelper.uniqueEmailRegister(email)) {
                Toast.makeText(RegisterActivity.this, "This email is already used", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isPasswordOk(String password1, String password2){
        if(!password1.equals(password2)){
            Toast.makeText(RegisterActivity.this, "Your password is not identical", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password1.length()<6){
            Toast.makeText(RegisterActivity.this, "Passward should have at least 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        boolean hasUpper = false;
        boolean hasNumber = false;
        boolean hasLower = false;

        for (char c : password1.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if(Character.isLowerCase(c)){
                hasLower = true;
            }

            if (hasUpper && hasNumber && hasLower) {
                return true;
            }
        }
        Toast.makeText(RegisterActivity.this, "You should include one digit and one upper letter in your password", Toast.LENGTH_SHORT).show();
        return false;
    }
}
