package com.example.library.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.library.Adapters.AuthorAdapter;
import com.example.library.Adapters.GenreAdapter;
import com.example.library.Adapters.LibraryBookAdapter;
import com.example.library.Database.DatabaseHelper;
import com.example.library.Models.Author;
import com.example.library.Models.Genre;
import com.example.library.Models.User;
import com.example.library.R;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RegisterSelectActivity  extends AppCompatActivity implements GenreAdapter.OnGenreSelectedListener, AuthorAdapter.OnAuthorSelectedListener {
    private DatabaseHelper dbHelper;
    private Button button;
    private RecyclerView genresRV;
    private RecyclerView authorsRV;
    private TextView main;
    private List<Genre> genres;
    private List<Author> authors;
    private Set<Integer> selectedGenres;
    private Set<Integer> selectedAuthors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_preferences);
        Intent intent = getIntent();
        selectedGenres = new HashSet<>();
        selectedAuthors = new HashSet<>();

        dbHelper = new DatabaseHelper(RegisterSelectActivity.this);
        button = findViewById(R.id.RegisterBtn);
        main = findViewById(R.id.mainTxt);
        String page = (String) intent.getSerializableExtra("page");
        if(!page.equals("register")){
            main.setText("Change preferences");
            button.setText("Done");
        }

        genres = dbHelper.getAllGenres();
        genresRV = findViewById(R.id.genres);
        genresRV.setHasFixedSize(true);
        int numberOfRows = 3;
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                numberOfRows,
                StaggeredGridLayoutManager.HORIZONTAL // Horizontal orientation
        );
        genresRV.setLayoutManager(layoutManager);
        GenreAdapter genreAdapter = new GenreAdapter(genres, this);
        genresRV.setAdapter(genreAdapter);

        authors = dbHelper.getAllAuthors();
        System.out.println("authors: " + authors.size());
        authorsRV = findViewById(R.id.authors);        authorsRV.setHasFixedSize(true);
        StaggeredGridLayoutManager layoutManager1 = new StaggeredGridLayoutManager(
                numberOfRows,
                StaggeredGridLayoutManager.HORIZONTAL // Horizontal orientation
        );
        authorsRV.setLayoutManager(layoutManager1);
        AuthorAdapter authorAdapter = new AuthorAdapter(authors, this);
        authorsRV.setAdapter(authorAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedGenres.size()>=5) {
                    if(!page.equals("register")){
                        User user = dbHelper.getUserById(MainActivity.sharedPreferences.getInt("user_id",-1));
                        dbHelper.deletePreferences(user.getId());
                        for (Integer genreId : selectedGenres) {
                            dbHelper.addPreferencesGenre(user, dbHelper.getGenreById(genreId));
                        }
                        for (Integer authorId : selectedAuthors) {
                            dbHelper.addPreferencesAuthor(user, dbHelper.getAuthorById(authorId));
                        }

                        finish();
                    }else {
                        String name = (String) intent.getSerializableExtra("name");
                        String number = (String) intent.getSerializableExtra("number");
                        String password = (String) intent.getSerializableExtra("password");
                        String email = (String) intent.getSerializableExtra("email");
                        int id = dbHelper.addNewUser(name, number, email, password);
                        SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
                        editor.putBoolean("isLoggedIn", true);
                        editor.putInt("user_id", id);
                        editor.apply();

                        User user = dbHelper.getUserById(id);
                        for (Integer genreId : selectedGenres) {
                            dbHelper.addPreferencesGenre(user, dbHelper.getGenreById(genreId));
                        }
                        for (Integer authorId : selectedAuthors) {
                            dbHelper.addPreferencesAuthor(user, dbHelper.getAuthorById(authorId));
                        }

                        startActivity(new Intent(RegisterSelectActivity.this, RegisterPhotoActivity.class));
                        finish();
                    }
                }else{
                    Toast.makeText(RegisterSelectActivity.this, "Yout should select 5 genres" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onGenreSelected(Genre genre) {
        if (selectedGenres.contains(genre.getId())) {
            selectedGenres.remove(genre.getId());
        } else {
            selectedGenres.add(genre.getId());
        }
        for(Integer genre1 : selectedGenres){
            System.out.println(genre1);
        }
//        Toast.makeText(this, "Selected genres count: " + selectedGenres.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthorSelected(Author author) {
        if (selectedAuthors.contains(author.getId())) {
            selectedAuthors.remove(author.getId());
        } else {
            selectedAuthors.add(author.getId());
        }
        for(Integer genre1 : selectedAuthors){
            System.out.println(genre1);
        }
//        Toast.makeText(this, "Selected author count: " + selectedAuthors.size(), Toast.LENGTH_SHORT).show();
    }
}
