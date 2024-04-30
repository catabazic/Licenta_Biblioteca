package com.example.library.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import com.example.library.Models.Book;

public class LibraryBookAdapter  extends RecyclerView.Adapter<LibraryBookAdapter.ViewHolder> {
    private List<Book> mBooks;

    // Constructor
    public LibraryBookAdapter(List<Book> books) {
        mBooks = books;
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView bookNameTextView;
        public TextView authorTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            bookNameTextView = itemView.findViewById(R.id.booksBookName);
            authorTextView = itemView.findViewById(R.id.booksBookAuthor);
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
        String imageUrl = mBooks.get(position).getImage();
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream((InputStream) new URL(imageUrl).getContent());
            holder.imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            System.out.println("upsi, no photo");
        }

        holder.bookNameTextView.setText(mBooks.get(position).getName());
        holder.authorTextView.setText(mBooks.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }
}
