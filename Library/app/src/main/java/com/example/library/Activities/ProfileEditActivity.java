package com.example.library.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.library.Database.FirebaseDatabaseHelper;
import com.example.library.Interfaces.FirestoreCallback;
import com.example.library.Models.DB.User;
import com.example.library.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class ProfileEditActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_IMAGE_PICKER = 1;
    private static final int REQUEST_CODE_ANOTHER_ACTION = 2;
    private TextView back;
    private TextView send;
    private TextView photo;
    private EditText nume;
    private TextView numar;
    private TextView genres;
    private TextView authors;
    private TextView changePreferences;
    private TextView email;
    private TextView emailBtn;
    private Button passwordBtn;
    private Button logoutBtn;
    private ImageView profilePhoto;
    private User user;
    private FirebaseDatabaseHelper dbHelper;
    private FirebaseStorage storage;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_edit);

        back = findViewById(R.id.BackTxt);
        send = findViewById(R.id.SentTxt);
        photo = findViewById(R.id.photoBtn);
        nume = findViewById(R.id.nameEditText);
        numar = findViewById(R.id.phoneEditTxt);
        email = findViewById(R.id.email);
        emailBtn = findViewById(R.id.emailBtn);
        passwordBtn = findViewById(R.id.passwordChange);
        logoutBtn = findViewById(R.id.LogoutBtn);
        genres = findViewById(R.id.genresTxt);
        authors = findViewById(R.id.authorsTxt);
        changePreferences = findViewById(R.id.changePreferences);
        profilePhoto = findViewById(R.id.user_img3);

        dbHelper = new FirebaseDatabaseHelper();
        storage = FirebaseStorage.getInstance();


        this.changeData();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImageToFirebase(task ->{
                    String name = nume.getText().toString();
                    String number = numar.getText().toString();
                    String userId = user.getId();
                    Task<Void> changePhoneNumberTask = dbHelper.changePhoneNumber(userId, number);
                    Task<Void> changeNameTask = dbHelper.changeName(userId, name);

                    Tasks.whenAll(changePhoneNumberTask, changeNameTask)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent resultIntent = new Intent();
                                        setResult(RESULT_OK, resultIntent);
                                    } else {
                                        Exception e = task.getException();
                                    }
                                    finish();
                                }
                            });

                });
            }
        });


        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ProfileEditActivity.this). cropSquare()
                        .compress(1024).maxResultSize(1000,1000)
                        .start(REQUEST_CODE_IMAGE_PICKER);
            }
        });

        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileEditActivity.this, ChangeEmailActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ANOTHER_ACTION);
            }
        });

        passwordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileEditActivity.this, ChangePasswordActivity.class));
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
                editor.remove("isLoggedIn"); // Remove the isLoggedIn key
                editor.apply();
                startActivity(new Intent(ProfileEditActivity.this, MainActivity.class));
                finish();
            }
        });

        changePreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileEditActivity.this, RegisterSelectActivity.class);
                intent.putExtra("page", "edit");
                startActivityForResult(intent, REQUEST_CODE_ANOTHER_ACTION);
            }
        });
    }

    private void changeData(){
        dbHelper.getUserById(MainActivity.sharedPreferences.getString("user_id", null), user -> {
            this.user = user;
            numar.setText(user.getNumber());
            email.setText(user.getEmail());
            nume.setText(user.getName());
            String imageUrl = user.getPhoto();
            if(imageUrl!=null) {
                System.out.println(imageUrl);
                Picasso.get()
                        .load(imageUrl)
                        .into(profilePhoto);
            }else{
                System.out.println("no photo");
            }
            dbHelper.getPreferencesAuthors(user.getId(), authorList -> {
                if (!authorList.isEmpty()) {
                    StringBuilder authorStr = new StringBuilder("Pref authors: ");
                    for (int i = 0; i < authorList.size(); i++) {
                        authorStr.append(authorList.get(i).getName());
                        if (i < authorList.size() - 1) {
                            authorStr.append(", ");
                        }
                    }
                    authors.setText(authorStr);
                } else {
                    authors.setText("");
                }
            });

            dbHelper.getPreferencesGenre(user.getId(), genreList -> {
                if (!genreList.isEmpty()) {
                    StringBuilder genreStr = new StringBuilder("Pref genres: ");
                    for (int i = 0; i < genreList.size(); i++) {
                        genreStr.append(genreList.get(i).getName());
                        if (i < genreList.size() - 1) {
                            genreStr.append(", ");
                        }
                    }
                    genres.setText(genreStr);
                } else {
                    genres.setText("");
                }
            });
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_IMAGE_PICKER:
                if (resultCode == RESULT_OK && data != null) {
                    imageUri = data.getData();
                    profilePhoto.setImageURI(imageUri);
                } else if (resultCode == ImagePicker.RESULT_ERROR) {
                    Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_ANOTHER_ACTION:
                this.changeData();
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    private void uploadImageToFirebase(FirestoreCallback callback) {
        if(imageUri!=null) {
            StorageReference storageRef = storage.getReference().child("profile_images/" + MainActivity.sharedPreferences.getString("user_id", null) + "/" + UUID.randomUUID().toString());
            storageRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri downloadUri) {
                                    callback.onComplete(saveImageUrlToFirestore(downloadUri.toString()));
                                    Toast.makeText(ProfileEditActivity.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            callback.onComplete(null);
                            Toast.makeText(ProfileEditActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            callback.onComplete(null);
        }
    }

    private Task<Void> saveImageUrlToFirestore(String downloadUrl) {
        String id = MainActivity.sharedPreferences.getString("user_id", null);
        return dbHelper.changePhoto(id, downloadUrl)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ProfileEditActivity.this, "Profile Photo URL saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileEditActivity.this, "Error saving URL", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
