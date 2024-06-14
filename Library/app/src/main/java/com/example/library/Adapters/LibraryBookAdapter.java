package com.example.library.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.library.Database.FirebaseDatabaseHelper;
import com.example.library.Interfaces.OnItemClickListener;
import com.example.library.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

import com.example.library.Models.DB.Book;
import com.squareup.picasso.Picasso;

public class LibraryBookAdapter  extends RecyclerView.Adapter<LibraryBookAdapter.ViewHolder> {
    private List<Book> mBooks;
    private OnItemClickListener listener;

    // Constructor
    public LibraryBookAdapter(List<Book> books, OnItemClickListener listener) {
        this.mBooks = books;
        this.listener=listener;
    }

    // ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        public TextView bookNameTextView;
        public TextView authorTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            bookNameTextView = itemView.findViewById(R.id.booksBookName);
            authorTextView = itemView.findViewById(R.id.booksBookAuthor);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            System.out.println("i am here");
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    System.out.println("i guess i should be here");
                    Book clickedBook = mBooks.get(position); // Retrieve the clicked book
                    System.out.println(clickedBook.getName());
                    listener.onItemClick(clickedBook); // Pass the clicked book to the listener
                }
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View bookView = inflater.inflate(R.layout.item_library_book, parent, false);
        return new ViewHolder(bookView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = mBooks.get(position);
        String imageUrl = book.getImage();
        if(imageUrl!=null) {
            System.out.println(imageUrl);
            Picasso.get()
                    .load(imageUrl)
                    .into(holder.imageView);
        }else{
            System.out.println("no photo");
        }

        holder.bookNameTextView.setText(book.getName());

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
                    holder.authorTextView.setText(aut.toString());
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    public int getItemCount() {
        return mBooks.size();
    }
}
