package com.example.library.Adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.Models.DB.Author;
import com.example.library.R;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.AuthorViewHolder> {

    public interface OnAuthorSelectedListener {
        void onAuthorSelected(Author author);
    }

    private List<Author> authors;
    private Set<String> selectedAuthors;
    private OnAuthorSelectedListener listener;

    public AuthorAdapter(List<Author> authors, OnAuthorSelectedListener listener) {
        this.authors = authors;
        this.selectedAuthors = new HashSet<>();
        this.listener = listener;
    }

    @Override
    public AuthorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_genre_author, parent, false);
        return new AuthorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AuthorViewHolder holder, int position) {
        Author author = authors.get(position);
        holder.bind(author, selectedAuthors.contains(author.getId()));
    }

    @Override
    public int getItemCount() {
        return authors.size();
    }

    public class AuthorViewHolder extends RecyclerView.ViewHolder {
        private TextView genreName;
        private Drawable backgroundDrawable;

        public AuthorViewHolder(View itemView) {
            super(itemView);
            genreName = itemView.findViewById(R.id.name);

            // Set default background if not already set
            if (genreName.getBackground() == null) {
                genreName.setBackgroundResource(R.drawable.frame_message);
            }

            backgroundDrawable = genreName.getBackground().mutate();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Author author = authors.get(position);
                        if (selectedAuthors.contains(author.getId())) {
                            selectedAuthors.remove(author.getId());
                        } else {
                            selectedAuthors.add(author.getId());
                        }
                        notifyItemChanged(position);
                        listener.onAuthorSelected(author);
                    }
                }
            });
        }

        public void bind(final Author author, boolean isSelected) {
            genreName.setText(author.getName());
            int color = isSelected ? 0x3C6750A3 : 0x856750A3;
            DrawableCompat.setTint(backgroundDrawable, color);
        }
    }
}
