package com.example.library.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.Interfaces.OnItemClickListener;
import com.example.library.Models.Book;
import com.example.library.Models.Chat;
import com.example.library.Models.User;
import com.example.library.R;

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

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.userName);
//            author= itemView.findViewById(R.id.bookAuthor);
            layout = itemView.findViewById(R.id.chatLayout);
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
