package com.example.library.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.Interfaces.OnItemClickListener;
import com.example.library.Models.DB.User;
import com.example.library.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.ViewHolder> {
    List<User> userList;
    private OnItemClickListener listener;


    public SearchUserAdapter(List<User> userList, OnItemClickListener listener) {
        this.userList = userList;
        this.listener=listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name;
//        private TextView author;
        private LinearLayout layout;
        private ImageView profilePhoto;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.userName);
//            author= itemView.findViewById(R.id.bookAuthor);
            layout = itemView.findViewById(R.id.chatLayout);
            profilePhoto = itemView.findViewById(R.id.user_img3);
        }


        @Override
        public void onClick(View v) {
            System.out.println("i am here");
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    System.out.println("i guess i should be here");
                    User clickedChat = userList.get(position);
                    listener.onItemClick(clickedChat);
                }
            }
        }
    }

    @NonNull
    @Override
    public SearchUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_user, parent, false);
        return new SearchUserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchUserAdapter.ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.name.setText(user.getName());
        String imageUrl = user.getPhoto();
        if(imageUrl!=null) {
            System.out.println(imageUrl);
            Picasso.get()
                    .load(imageUrl)
                    .into(holder.profilePhoto);
        }else{
            System.out.println("no photo");
        }
//        holder.author.setText(book.getAuthor());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void updateData(List<User> newData) {
        userList.clear(); // Clear the existing dataset
        userList.addAll(newData); // Add the new data
        notifyDataSetChanged(); // Notify the adapter that the dataset has changed
    }

}
