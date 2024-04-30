package com.example.library.Adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.R;

import java.util.List;

import com.example.library.Models.Book;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> bookList;

    public BookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView bookName;
        TextView bookAuthor;
        TextView bookState;

        public BookViewHolder(View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.BooksBookName);
            bookAuthor = itemView.findViewById(R.id.BooksBookAuthor);
            bookState = itemView.findViewById(R.id.BooksBookState);
        }
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.bookName.setText(book.getName());
        holder.bookAuthor.setText(book.getAuthor());
        holder.bookState.setText(book.getState());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}
