package com.example.library.Adapters;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.Database.FirebaseDatabaseHelper;
import com.example.library.Interfaces.OnItemClickListener;
import com.example.library.Models.DB.Book;
import com.example.library.R;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

public class SearchBookAdapter extends RecyclerView.Adapter<SearchBookAdapter.ViewHolder> {
    List<Book> bookList;
    private OnItemClickListener listener;


    public SearchBookAdapter(List<Book> bookList, OnItemClickListener listener) {
        this.bookList = bookList;
        this.listener=listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name;
        private TextView author;
        private LinearLayout layout;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.userName);
            author= itemView.findViewById(R.id.bookAuthor);
            layout = itemView.findViewById(R.id.chatLayout);
        }


        @Override
        public void onClick(View v) {
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Book clickedChat = bookList.get(position);
                    listener.onItemClick(clickedChat);
                }
            }
        }
    }

    @NonNull
    @Override
    public SearchBookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_book, parent, false);
        return new SearchBookAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchBookAdapter.ViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.name.setText(book.getName());
        FirebaseDatabaseHelper database = new FirebaseDatabaseHelper();
        Set<String> authors = book.getAuthors();
        StringBuilder aut = new StringBuilder();
        CountDownLatch latch = new CountDownLatch(authors.size());

        // Run database operations in a background thread
        Executors.newSingleThreadExecutor().execute(() -> {
            for (String id : authors) {
                database.getAuthorById(id, author -> {
                    synchronized (aut) {
                        if (author != null) {
                            if (aut.length() == 0) {
                                aut.append(author.getName());
                            } else {
                                aut.append(" & ").append(author.getName());
                            }
                        }
                        latch.countDown();
                    }
                });
            }

            try {
                latch.await();
                // Post the result back to the main thread
                new Handler(Looper.getMainLooper()).post(() -> {
                    holder.author.setText(aut.toString());
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public void updateData(List<Book> newData) {
        System.out.println(bookList.size());
        bookList.clear(); // Clear the existing dataset
        bookList.addAll(newData); // Add the new data
        System.out.println(bookList.size());
        notifyDataSetChanged(); // Notify the adapter that the dataset has changed
    }
}
