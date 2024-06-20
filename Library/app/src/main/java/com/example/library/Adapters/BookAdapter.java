package com.example.library.Adapters;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.Database.FirebaseDatabaseHelper;
import com.example.library.Interfaces.OnItemClickListener;
import com.example.library.Models.DB.Author;
import com.example.library.Models.DB.Loan;
import com.example.library.R;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.example.library.Models.DB.Book;
import com.squareup.picasso.Picasso;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Loan> loanList;
    private OnItemClickListener listener;
    private List<String> returns;
    private int[] colors = {0x5BFFFFFF,0xA6FFFFFF};
    FirebaseDatabaseHelper db;

    public BookAdapter(List<Loan> loanList, OnItemClickListener listener) {
        this.loanList = loanList;
        this.listener=listener;
//        this.returns = returns;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView bookName;
        TextView bookAuthor;
        TextView bookState;
        TextView bookRetur;
        LinearLayout layout;
        ImageView imageView;

        public BookViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView3);
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
                    db.getBookById(loanList.get(position).getBookId()).addOnSuccessListener(clickedBook->{
                        listener.onItemClick(clickedBook);
                    });
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        setState(holder,position);
        db = new FirebaseDatabaseHelper();
        db.getBookById(loanList.get(position).getBookId()).addOnSuccessListener(book->{
            holder.bookName.setText(book.getName());
            Set<String> authors = book.getAuthors();

            String imageUrl = book.getImage();
            if(imageUrl!=null) {
                System.out.println(imageUrl);
                Picasso.get()
                        .load(imageUrl)
                        .into(holder.imageView);
            }else{
                System.out.println("no photo");
            }

            StringBuilder autBuilder = new StringBuilder();
            List<CompletableFuture<Author>> futures = new ArrayList<>();

            // Collect all futures
            for (String id : authors) {
                CompletableFuture<Author> futureAuthor = new CompletableFuture<>();
                db.getAuthorById(id, futureAuthor::complete);
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

        });

        int colorIndex = position % colors.length;
        holder.layout.setBackgroundColor(colors[colorIndex]);
    }


    @Override
    public int getItemCount() {
        return loanList.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setState(@NonNull BookViewHolder holder, int index){
        if(loanList.get(index).getDataRetur()!=null){
            String state = "Was available until " + getFormattedDate(loanList.get(index).getTimestapt(2));
            holder.bookState.setText(state);
        }else if(loanList.get(index).getDataInceput()!=null){
            String state = "Is available from " + getFormattedDate(loanList.get(index).getTimestapt(1));
            holder.bookState.setText(state);
        }else if(loanList.get(index).getDataCerere()!=null){
            String state = "Reserved from " + getFormattedDate(loanList.get(index).getTimestapt(0));
            holder.bookState.setText(state);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getFormattedDate(Date date) {
        LocalDateTime messageDate = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        LocalDateTime now = LocalDateTime.now();

        if (messageDate.toLocalDate().isEqual(now.toLocalDate())) {
            // Same day
            return messageDate.format(DateTimeFormatter.ofPattern("HH:mm"));
        } else if (ChronoUnit.DAYS.between(messageDate.toLocalDate(), now.toLocalDate()) < 7) {
            return messageDate.format(DateTimeFormatter.ofPattern("EEE"));
        } else if (messageDate.getYear() == now.getYear()) {
            return messageDate.format(DateTimeFormatter.ofPattern("dd MMM"));
        } else {
            return messageDate.format(DateTimeFormatter.ofPattern("yyyy"));
        }
    }
}
