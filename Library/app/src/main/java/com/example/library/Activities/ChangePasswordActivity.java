package com.example.library.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.library.Database.FirebaseDatabaseHelper;
import com.example.library.R;

public class ChangePasswordActivity extends AppCompatActivity {

    private TextView back;
    private EditText oldPassword;
    private EditText password;
    private EditText passwordRepeat;
    private Button changeBtn;
    private FirebaseDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        back = findViewById(R.id.BackTxt);
        oldPassword = findViewById(R.id.emailEdit);
        password = findViewById(R.id.password);
        passwordRepeat = findViewById(R.id.passwordRepeat);
        changeBtn = findViewById(R.id.button);
        dbHelper = new FirebaseDatabaseHelper();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.isPasswordOk(MainActivity.sharedPreferences.getString("user_id",null), oldPassword.getText().toString()).addOnSuccessListener(b->{
                    if(isPasswordOk(password.getText().toString(),passwordRepeat.getText().toString()) && b){
                        dbHelper.changePassword(MainActivity.sharedPreferences.getString("user_id",null), password.getText().toString());
                        startActivity(new Intent(ChangePasswordActivity.this, ProfileEditActivity.class));
                        finish();
                        Toast.makeText(ChangePasswordActivity.this, "You changed password", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ChangePasswordActivity.this, "You password is incorect!", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }

    private boolean isPasswordOk(String password1, String password2){
        if(!password1.equals(password2)){
            Toast.makeText(ChangePasswordActivity.this, "Your password is not identical", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password1.length()<6){
            Toast.makeText(ChangePasswordActivity.this, "Passward should have at least 6 characters", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(ChangePasswordActivity.this, "You should include one digit and one upper letter in your password", Toast.LENGTH_SHORT).show();
        return false;
    }
}
