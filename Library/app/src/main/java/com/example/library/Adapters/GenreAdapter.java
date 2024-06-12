package com.example.library.Adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.Models.DB.Genre;
import com.example.library.R;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder> {

    public interface OnGenreSelectedListener {
        void onGenreSelected(Genre genre);
    }

    private List<Genre> genres;
    private Set<String> selectedGenres;
    private OnGenreSelectedListener listener;

    public GenreAdapter(List<Genre> genres, OnGenreSelectedListener listener) {
        this.genres = genres;
        this.selectedGenres = new HashSet<>();
        this.listener = listener;
    }

    @Override
    public GenreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_genre_author, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GenreViewHolder holder, int position) {
        Genre genre = genres.get(position);
        holder.bind(genre, selectedGenres.contains(genre.getId()));
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    public class GenreViewHolder extends RecyclerView.ViewHolder {
        private TextView genreName;
        private Drawable backgroundDrawable;

        public GenreViewHolder(View itemView) {
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
                        Genre genre = genres.get(position);
                        if (selectedGenres.contains(genre.getId())) {
                            selectedGenres.remove(genre.getId());
                        } else {
                            selectedGenres.add(genre.getId());
                        }
                        notifyItemChanged(position);
                        listener.onGenreSelected(genre);
                    }
                }
            });
        }

        public void bind(final Genre genre, boolean isSelected) {
            genreName.setText(genre.getName());
            int color = isSelected ? 0x3C6750A3 : 0x856750A3;
            DrawableCompat.setTint(backgroundDrawable, color);
        }
    }
}
