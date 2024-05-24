package com.example.library.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.Adapters.ReviewBookAdapter;
import com.example.library.Database.DatabaseHelper;
import com.example.library.Models.Review;
import com.example.library.R;

import java.util.ArrayList;
import java.util.List;

public class ReviewsMineActivity extends AppCompatActivity {
    private ImageButton backButton;
    private RecyclerView recyclerView;
    private List<Review> reviewList;
    private ReviewBookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_reviews_mine);

        DatabaseHelper dbHelper = new DatabaseHelper(ReviewsMineActivity.this);

        reviewList = dbHelper.getAllReviewsOfUser(MainActivity.sharedPreferences.getInt("user_id",-1));
        List<String> names= new ArrayList<String>();
        for(Review review : reviewList){
            names.add(dbHelper.getBookById(review.getId_book()).getName());
        }

        backButton=findViewById(R.id.backBtn);
        recyclerView = findViewById(R.id.reviewsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReviewBookAdapter(reviewList, names);
        recyclerView.setAdapter(adapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
