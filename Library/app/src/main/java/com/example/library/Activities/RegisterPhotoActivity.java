package com.example.library.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.library.Database.FirebaseDatabaseHelper;
import com.example.library.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class RegisterPhotoActivity  extends AppCompatActivity {
    private TextView changePhoto;
    private Button button;
    private FirebaseDatabaseHelper dbHelper;
    private ImageView profilePhoto;
    private FirebaseStorage storage;

    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_photo);

        dbHelper = new FirebaseDatabaseHelper();
        storage = FirebaseStorage.getInstance();

        changePhoto = findViewById(R.id.photoBtn);
        button = findViewById(R.id.RegisterBtn);
        profilePhoto = findViewById(R.id.user_img3);

        changePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(RegisterPhotoActivity.this). cropSquare()
                        .compress(1024).maxResultSize(1000,1000)
                        .start();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterPhotoActivity.this, HomeActivity.class));
                finish();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            profilePhoto.setImageURI(imageUri);
            uploadImageToFirebase();
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImageToFirebase() {
        if(imageUri!=null) {
            StorageReference storageRef = storage.getReference().child("profile_images/" + MainActivity.sharedPreferences.getString("user_id", null) + "/" + UUID.randomUUID().toString());
            storageRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri downloadUri) {
                                    saveImageUrlToFirestore(downloadUri.toString());
                                    Toast.makeText(RegisterPhotoActivity.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterPhotoActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void saveImageUrlToFirestore(String downloadUrl) {
        String id = MainActivity.sharedPreferences.getString("user_id", null);
        dbHelper.changePhoto(id, downloadUrl)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(RegisterPhotoActivity.this, "Profile Photo URL saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterPhotoActivity.this, "Error saving URL", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
