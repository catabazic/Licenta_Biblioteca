package com.example.library.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.library.Database.DatabaseHelper;
import com.example.library.Models.Book;
import com.example.library.Models.Review;
import com.example.library.R;

import java.time.LocalDate;

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

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate currentDate = LocalDate.now();
            review.setDate(currentDate.toString());
        }

        backTxt=findViewById(R.id.BackTxtReview);
        sentTxt=findViewById(R.id.SentTxtReview);
        ratingBar=findViewById(R.id.ratingBarReview);
        titleTxt=findViewById(R.id.TitleTxt);
        commentTxt=findViewById(R.id.ReviewTxt);

        Intent intent = getIntent();
        if(intent != null) {
            Book book = (Book) intent.getSerializableExtra("book");
            if (book != null) {
                review.setId_book(book.getId());
            }
        }

        backTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sentTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(titleTxt.getText().toString());
                System.out.println(commentTxt.getText().toString());
                review.setReviewTitle(titleTxt.getText().toString());
                review.setReviewText(commentTxt.getText().toString());

                if(verifyData()){
                    DatabaseHelper dbhelper = new DatabaseHelper(ReviewBookActivity.this);
                    dbhelper.addReviewForABook(review);
                    finish();
                }
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                review.setRating(rating);
            }
        });

    }

    private boolean verifyData(){
        float tolerance = 0.0001f;
        if(review.getRating()<tolerance){
            Toast.makeText(ReviewBookActivity.this, "you didn't rate it!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(review.getReviewTitle()==null){
            Toast.makeText(ReviewBookActivity.this, "you should write a title!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
