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
import com.example.library.Database.DatabaseHelper;
import com.example.library.Models.Book;
import com.example.library.Models.Review;
import com.example.library.R;

import java.util.ArrayList;
import java.util.List;

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
    private List<Review> reviewList;
    private DatabaseHelper dbHelper;
    private ReviewBookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_reviews_book);

        dbHelper = new DatabaseHelper(ReviewsBookAllActivity.this);

        Intent intent = getIntent();
        if(intent != null) {
            book = (Book) intent.getSerializableExtra("book");
            System.out.println("It was set");
            System.out.println(book.getId());
        }
        reviewList = dbHelper.getAllReviewsOfBook(book.getId());
        List<String> names= new ArrayList<String>();
        for(Review review : reviewList){
            names.add(dbHelper.getUserById(review.getId_user()).getName());
        }

        backButton=findViewById(R.id.backBtn);
        ratingNote=findViewById(R.id.BookRating);
        ratingNumber=findViewById(R.id.bookRatingNumber);
        ratingBar=findViewById(R.id.ratingBar);
        rating5=findViewById(R.id.progressBar5);
        rating4=findViewById(R.id.progressBar4);
        rating3=findViewById(R.id.progressBar3);
        rating2=findViewById(R.id.progressBar2);
        rating1=findViewById(R.id.progressBar1);

        recyclerView = findViewById(R.id.reviewsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReviewBookAdapter(reviewList, names);
        recyclerView.setAdapter(adapter);

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
        ratingNote.setText(String.valueOf(dbHelper.getRatingOfBook(book.getId())));
        List<Integer> list = dbHelper.getNumberOfRatings(book.getId());
        String numberOfRatingsStr = String.valueOf(list.get(0));
        numberOfRatingsStr+=" Reviews";
        ratingNumber.setText(numberOfRatingsStr);
        rating1.setProgress(list.get(1));
        rating2.setProgress(list.get(2));
        rating3.setProgress(list.get(3));
        rating4.setProgress(list.get(4));
        rating5.setProgress(list.get(5));

    }

}
