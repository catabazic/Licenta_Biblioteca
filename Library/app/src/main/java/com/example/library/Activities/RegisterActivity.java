package com.example.library.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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


import com.example.library.Database.FirebaseDatabaseHelper;
import com.example.library.Helper.MailHelper;
import com.example.library.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText emaildEditText;
    private EditText numberEditText;
    private EditText password1EditText;
    private EditText password2EditText;
    private Button registerButton;
    private Button otherChoice;
    private FirebaseDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        dbHelper = new FirebaseDatabaseHelper();

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
                    sendMail(name, number, email, password1);
                    Toast.makeText(RegisterActivity.this, "Check your mail", Toast.LENGTH_SHORT).show();
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


    private void sendMail(String name, String number, String email, String password1){
        int min = 10000;
        int max = 99999;
        int randomNumber = (int) (Math.random() * (max - min + 1)) + min;

        long currentTimeMillis = System.currentTimeMillis();
        SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
        editor.putInt("mailChange", randomNumber);
        editor.putLong("mailChangeTimestamp", currentTimeMillis);
        editor.apply();

        MailHelper mailHelper = new MailHelper(RegisterActivity.this);
        String recipient = email;
        String subject = "Verification Required: Confirm Your New Email Address";
        String body = "Dear " + name + "\n" +
                "\n " +
                "We hope this message finds you well.\n" +
                "\n" +
                "You have recently requested to change the email address associated with your Digital Bookshelf account. To ensure the security of your account and verify that this request is legitimate, we need to confirm your new email address.\n" +
                "\n" +
                "Please use the following code to verify your new email address:\n" +
                "\n" +
                randomNumber + "\n" +
                "If you did not request this change, please disregard this email. Your current email address will remain unchanged.\n" +
                "\n" +
                "Thank you for your prompt attention to this matter.\n" +
                "\n" +
                "Best regards,\n" +
                "\n" +
                "Digital Bookshelf";

        mailHelper.sendEmail(recipient, subject, body, new MailHelper.MailSenderCallback() {
            @Override
            public void onSuccess() {
                Intent intent = new Intent(RegisterActivity.this, ChangeCheckActivity.class);
                intent.putExtra("page", "register");
                intent.putExtra("email", recipient);
                intent.putExtra("name", name);
                intent.putExtra("number", number);
                intent.putExtra("password", password1);
                startActivity(intent);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 500);
            }
            @Override
            public void onFailure(Exception e) {
                System.out.println(e);
            }
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

    private boolean isEmailCorrect(String email) {
        try {
            /*if (!dbHelper.uniqueEmailRegister(email).getResult()) {
                Toast.makeText(RegisterActivity.this, "This email is already used", Toast.LENGTH_SHORT).show();
                return false;
            }*/
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
