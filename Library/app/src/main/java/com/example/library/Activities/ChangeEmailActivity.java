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
import com.example.library.Sender.MailSender;

public class ChangeEmailActivity extends AppCompatActivity {
    private TextView back;
    private EditText email;
    private Button button;
    private DatabaseHelper dbHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_mail);

        dbHelper = new DatabaseHelper(ChangeEmailActivity.this);
        back = findViewById(R.id.BackTxt);
        email = findViewById(R.id.emailEdit);
        button = findViewById(R.id.button);

        user = dbHelper.getUserById(MainActivity.sharedPreferences.getInt("user_id", -1));
        back.setOnClickListener(v -> finish());

        button.setOnClickListener(v -> {
            if (isEmailCorrect(email.getText().toString())) {
                int min = 10000;
                int max = 99999;
                int randomNumber = (int) (Math.random() * (max - min + 1)) + min;

                long currentTimeMillis = System.currentTimeMillis();
                SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
                editor.putInt("mailChange", randomNumber);
                editor.putLong("mailChangeTimestamp", currentTimeMillis);
                editor.apply();

                MailSender mailSender = new MailSender(ChangeEmailActivity.this);
                String recipient = email.getText().toString();
                String subject = "Verification Required: Confirm Your New Email Address";
                String body = "Dear " + user.getName() + "\n" +
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

                mailSender.sendEmail(recipient, subject, body, new MailSender.MailSenderCallback() {
                    @Override
                    public void onSuccess() {
                        Intent intent = new Intent(ChangeEmailActivity.this, ChangeCheck.class);
                        intent.putExtra("email", recipient);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        startActivity(new Intent(ChangeEmailActivity.this, ChangeEmailActivity.class));
                        finish();
                    }
                });
            }
        });
    }

    private boolean isEmailCorrect(String email) {
        try {
            if (!dbHelper.uniqueEmailRegister(email)) {
                Toast.makeText(ChangeEmailActivity.this, "This email is already used", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
