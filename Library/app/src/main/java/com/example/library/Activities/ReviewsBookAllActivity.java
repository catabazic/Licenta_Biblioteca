package com.example.library.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.Adapters.ReviewBookAdapter;
import com.example.library.Database.FirebaseDatabaseHelper;
import com.example.library.Models.DB.Book;
import com.example.library.Models.DB.Review;
import com.example.library.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ReviewsBookAllActivity  extends AppCompatActivity {
    private ImageButton backButton;
    private TextView ratingNote;
    private TextView ratingNumber;
    private ProgressBar rating5;
    private ProgressBar rating4;
    private ProgressBar rating3;
    private ProgressBar rating2;
    private ProgressBar rating1;
    private RecyclerView recyclerView;
    private RatingBar ratingBar;
    private Book book;
    private FirebaseDatabaseHelper dbHelper;
    private ReviewBookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_reviews_book);

        dbHelper = new FirebaseDatabaseHelper();

        Intent intent = getIntent();
        if(intent != null) {
            book = (Book) intent.getSerializableExtra("book");
            System.out.println("It was set");
            System.out.println(book.getId());
        }
        recyclerView = findViewById(R.id.reviewsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper.getAllReviewsOfBook(book.getId()).addOnSuccessListener(reviewList -> {
            List<String> names = new ArrayList<>();
            AtomicInteger pendingTasks = new AtomicInteger(reviewList.size());

            for (Review review : reviewList) {
                dbHelper.getUserById(review.getId_user(), user -> {
                    if (user != null) {
                        names.add(user.getName());
                    } else {
                        names.add("Unknown User");
                    }

                    // Check if all tasks are completed
                    if (pendingTasks.decrementAndGet() == 0) {
                        // All users fetched, update the RecyclerView
                        adapter = new ReviewBookAdapter(reviewList, names);
                        recyclerView.setAdapter(adapter);
                        System.out.println("Reviews are here");
                    }
                });
            }

        }).addOnFailureListener(e -> {
            System.out.println("There was a problem fetching reviews");
        });


        backButton=findViewById(R.id.backBtn);
        ratingNote=findViewById(R.id.BookRating);
        ratingNumber=findViewById(R.id.bookRatingNumber);
        ratingBar=findViewById(R.id.ratingBar);
        rating5=findViewById(R.id.progressBar5);
        rating4=findViewById(R.id.progressBar4);
        rating3=findViewById(R.id.progressBar3);
        rating2=findViewById(R.id.progressBar2);
        rating1=findViewById(R.id.progressBar1);

        updateUI(book);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Intent intent = new Intent(ReviewsBookAllActivity.this, ReviewBookActivity.class);
                intent.putExtra("book",book);
                startActivity(intent);
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void updateUI(Book book) {
        dbHelper.getRatingOfBook(book.getId()).addOnSuccessListener(f ->{
            BigDecimal bd =new BigDecimal(f);
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            ratingNote.setText(String.valueOf(bd.floatValue()));
        });
        dbHelper.getNumberOfRatings(book.getId()).addOnSuccessListener(list ->{
            String numberOfRatingsStr = String.valueOf(list.get(0));
            numberOfRatingsStr+=" Reviews";
            ratingNumber.setText(numberOfRatingsStr);
            rating1.setProgress(list.get(1));
            rating2.setProgress(list.get(2));
            rating3.setProgress(list.get(3));
            rating4.setProgress(list.get(4));
            rating5.setProgress(list.get(5));
        });

    }

}
