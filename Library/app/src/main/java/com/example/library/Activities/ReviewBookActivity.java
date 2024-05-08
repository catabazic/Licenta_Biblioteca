package com.example.library.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.library.Models.Review;
import com.example.library.R;

public class ReviewBookActivity extends AppCompatActivity {

    private Review review;
    private TextView backTxt;
    private TextView sentTxt;
    private RatingBar ratingBar;
    private EditText titleTxt;
    private EditText commentTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_book);
        review=new Review();
        review.setId_user(MainActivity.sharedPreferences.getInt("user_id",-1));

        backTxt=findViewById(R.id.BackTxtReview);
        sentTxt=findViewById(R.id.SentTxtReview);
        ratingBar=findViewById(R.id.ratingBarReview);
        titleTxt=findViewById(R.id.TitleTxt);
        commentTxt=findViewById(R.id.ReviewTxt);

        backTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sentTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=titleTxt.toString();
                String comment = commentTxt.toString();
            }
        });

        ratingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = ratingBar.getRating();
                review.setRating(rating);
            }
        });


    }
}
