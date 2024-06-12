package com.example.library.Adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.Database.FirebaseDatabaseHelper;
import com.example.library.Interfaces.OnItemClickListener;
import com.example.library.Models.DB.Author;
import com.example.library.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.example.library.Models.DB.Book;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> bookList;
    private OnItemClickListener listener;
    private List<String> returns;
    private int[] colors = {0x5BFFFFFF,0xA6FFFFFF};

    public BookAdapter(List<Book> bookList, OnItemClickListener listener) {
        this.bookList = bookList;
        this.listener=listener;
//        this.returns = returns;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView bookName;
        TextView bookAuthor;
        TextView bookState;
        TextView bookRetur;
        LinearLayout layout;

        public BookViewHolder(View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.BooksBookName);
            bookAuthor = itemView.findViewById(R.id.BooksBookAuthor);
            bookState = itemView.findViewById(R.id.BooksBookState);
            bookRetur = itemView.findViewById(R.id.BooksBookState2);
            layout = itemView.findViewById(R.id.wholeBookLayout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Book clickedBook = bookList.get(position); // Retrieve the clicked book
                    listener.onItemClick(clickedBook); // Pass the clicked book to the listener
                }
            }
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
        Set<String> authors = book.getAuthors();
        FirebaseDatabaseHelper database = new FirebaseDatabaseHelper();

        // Use StringBuilder for efficient string concatenation
        StringBuilder autBuilder = new StringBuilder();
        List<CompletableFuture<Author>> futures = new ArrayList<>();

        // Collect all futures
        for (String id : authors) {
            CompletableFuture<Author> futureAuthor = new CompletableFuture<>();
            database.getAuthorById(id, futureAuthor::complete);
            futures.add(futureAuthor);
        }

        // Combine all futures
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).thenRun(() -> {
            for (CompletableFuture<Author> future : futures) {
                try {
                    Author author = future.get();
                    if (autBuilder.length() > 0) {
                        autBuilder.append(" & ");
                    }
                    autBuilder.append(author.getName());
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            holder.bookAuthor.setText(autBuilder.toString());
        });

        int colorIndex = position % colors.length;
        holder.layout.setBackgroundColor(colors[colorIndex]);
    }


    @Override
    public int getItemCount() {
        return bookList.size();
    }
}
