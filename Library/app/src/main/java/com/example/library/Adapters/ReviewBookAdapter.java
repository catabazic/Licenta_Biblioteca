package com.example.library.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.Database.DatabaseHelper;
import com.example.library.Models.Review;
import com.example.library.R;

import java.util.List;

public class ReviewBookAdapter extends RecyclerView.Adapter<ReviewBookAdapter.ViewHolder>{
    private List<Review> list;
    private List<String> name;

    public ReviewBookAdapter(List<Review> list, List<String> name) {
        this.list = list;
        this.name = name;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView date;
        TextView titleReview;
        TextView commentReview;
        ImageView star5;
        ImageView star4;
        ImageView star3;
        ImageView star2;
        ImageView star1;



        ViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userNameTxt);
            date = itemView.findViewById(R.id.dateTxt);
            titleReview = itemView.findViewById(R.id.titleReviewTxt);
            commentReview = itemView.findViewById(R.id.commentReviewTxt);
            star5 = itemView.findViewById(R.id.star5);
            star4 = itemView.findViewById(R.id.star4);
            star3 = itemView.findViewById(R.id.star3);
            star2 = itemView.findViewById(R.id.star2);
            star1 = itemView.findViewById(R.id.star1);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = list.get(position);
        holder.userName.setText(name.get(position));
        holder.date.setText(review.getDate());
        holder.titleReview.setText(review.getReviewTitle());
        holder.commentReview.setText(review.getReviewText());
        float rating = review.getRating();
        if(rating<=5){
            if(rating % 0.5 <0.01){
                holder.star1.setImageResource(R.drawable.ic_star_half);
            }
        } if(rating <= 4){
            holder.star1.setImageResource(R.drawable.ic_star_empty);
            if(rating % 0.5 <0.01){
                holder.star2.setImageResource(R.drawable.ic_star_half);
            }
        } if(rating <= 3){
            holder.star2.setImageResource(R.drawable.ic_star_empty);
            if(rating % 0.5 <0.01){
                holder.star3.setImageResource(R.drawable.ic_star_half);
            }
        } if(rating <= 2){
            holder.star3.setImageResource(R.drawable.ic_star_empty);
            if(rating % 0.5 <0.01){
                holder.star4.setImageResource(R.drawable.ic_star_half);
            }
        } if(rating <= 1){
            holder.star4.setImageResource(R.drawable.ic_star_empty);
            if(rating % 0.5 <0.01){
                holder.star5.setImageResource(R.drawable.ic_star_half);
            }
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
