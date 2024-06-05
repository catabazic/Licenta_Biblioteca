package com.example.library.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.library.Database.DatabaseHelper;
import com.example.library.R;

public class RegisterPhotoActivity  extends AppCompatActivity {
    private TextView changePhoto;
    private Button button;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_photo);

        dbHelper = new DatabaseHelper(RegisterPhotoActivity.this);
        changePhoto = findViewById(R.id.photoBtn);
        button = findViewById(R.id.RegisterBtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RegisterPhotoActivity.this, HomeActivity.class));
                finish();
            }
        });

    }
}
