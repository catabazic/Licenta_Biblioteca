package com.example.library.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.Adapters.ReviewBookAdapter;
import com.example.library.Database.FirebaseDatabaseHelper;
import com.example.library.Models.DB.Book;
import com.example.library.Models.DB.Review;
import com.example.library.R;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ReviewsMineActivity extends AppCompatActivity {
    private ImageButton backButton;
    private RecyclerView recyclerView;
    private ReviewBookAdapter adapter;
    private FirebaseDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_reviews_mine);

        dbHelper = new FirebaseDatabaseHelper();

        dbHelper.getAllReviewsOfUser(MainActivity.sharedPreferences.getString("user_id", null))
                .addOnSuccessListener(reviews -> {
                    System.out.println("At least I am here");
                    System.out.println(reviews.size());
                    List<String> names = new ArrayList<>();
                    List<Task<Book>> tasks = new ArrayList<>();

                    for (Review review : reviews) {
                        Task<Book> task = dbHelper.getBookById(review.getId_book())
                                .addOnSuccessListener(book -> {
                                    names.add(book.getName());
                                });
                        tasks.add(task);
                    }

                    Tasks.whenAllComplete(tasks).addOnSuccessListener(taskList -> {
                        recyclerView = findViewById(R.id.reviewsRecyclerView);
                        recyclerView.setLayoutManager(new LinearLayoutManager(this));
                        adapter = new ReviewBookAdapter(reviews, names);
                        recyclerView.setAdapter(adapter);
                    }).addOnFailureListener(e -> {
                        System.out.println(e);
                    });
                })
                .addOnFailureListener(e -> {
                    System.out.println(e);
                });



        backButton = findViewById(R.id.backBtn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
