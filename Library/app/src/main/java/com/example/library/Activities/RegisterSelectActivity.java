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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.library.Adapters.AuthorAdapter;
import com.example.library.Adapters.GenreAdapter;
import com.example.library.Database.FirebaseDatabaseHelper;
import com.example.library.Models.DB.Author;
import com.example.library.Models.DB.Genre;
import com.example.library.R;
import com.example.library.Server.SharedViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RegisterSelectActivity  extends AppCompatActivity implements GenreAdapter.OnGenreSelectedListener, AuthorAdapter.OnAuthorSelectedListener {

    private FirebaseDatabaseHelper dbHelper;
    private Button button;
    private RecyclerView genresRV;
    private RecyclerView authorsRV;
    private TextView main;
    private List<Genre> genres;
    private List<Author> authors;
    private Set<String> selectedGenres;
    private Set<String> selectedAuthors;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_preferences);
        Intent intent = getIntent();
        selectedGenres = new HashSet<>();
        selectedAuthors = new HashSet<>();


        dbHelper = new FirebaseDatabaseHelper();
        button = findViewById(R.id.RegisterBtn);
        main = findViewById(R.id.mainTxt);
        String page = (String) intent.getSerializableExtra("page");
        if(!page.equals("register")){
            main.setText("Change preferences");
            button.setText("Done");
        }

//        dbHelper.getAllGenres(gen ->{
//            genres = gen;
//        });
        System.out.println("PUR SIS IMPLU CEVA PLEASE");
        int numberOfRows = 3;
        dbHelper.getAllGenres().addOnSuccessListener(g -> {
            System.out.println("Everything is allright");
            if(g!=null) {
                genres = g;
            }else{
                genres = new ArrayList<>();
            }
            genresRV = findViewById(R.id.genres);
            genresRV.setHasFixedSize(true);

            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                    numberOfRows,
                    StaggeredGridLayoutManager.HORIZONTAL // Horizontal orientation
            );
            genresRV.setLayoutManager(layoutManager);
            GenreAdapter genreAdapter = new GenreAdapter(genres, this);
            genresRV.setAdapter(genreAdapter);
        }).addOnFailureListener(e -> {
            System.out.println("Something is wrong");
        });
        System.out.println("PUR SIS IMPLU ALT CEVA");

//        dbHelper.getAllAuthors(aut -> {
//            if (aut != null) {
//                authors = aut;
//                System.out.println(authors.size());
//            } else {
//                // Handle null response gracefully, e.g., show an error message
//                Log.e(TAG, "Null response received from getAllAuthors");
//                System.out.println("Null response received from getAllAuthors");
//            }
//        });
        dbHelper.getAllAuthors().addOnSuccessListener(a -> {
            System.out.println("Everything is allright");
            if(a!=null){
                authors = a;
            }else{
                genres = new ArrayList<>();
            }
            authorsRV = findViewById(R.id.authors);
            authorsRV.setHasFixedSize(true);
            StaggeredGridLayoutManager layoutManager1 = new StaggeredGridLayoutManager(
                    numberOfRows,
                    StaggeredGridLayoutManager.HORIZONTAL // Horizontal orientation
            );
            authorsRV.setLayoutManager(layoutManager1);
            AuthorAdapter authorAdapter = new AuthorAdapter(authors, this);
            authorsRV.setAdapter(authorAdapter);
        }).addOnFailureListener(e -> {
            System.out.println("Something is wrong");
        });
//        System.out.println("something");
//        System.out.println("authors: " + authors.size());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedGenres.size()>=4) {
                    if(!page.equals("register")){
                        String userId= MainActivity.sharedPreferences.getString("user_id", null);
                        dbHelper.deletePreferences(userId);

                        for (String genreId : selectedGenres) {
                            dbHelper.addPreferencesGenre(userId, genreId);
                        }
                        for (String authorId : selectedAuthors) {
                            dbHelper.addPreferencesAuthor(userId, authorId);
                        }
                        Intent resultIntent = new Intent();
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }else {
                        String name = (String) intent.getSerializableExtra("name");
                        String number = (String) intent.getSerializableExtra("number");
                        String password = (String) intent.getSerializableExtra("password");
                        String email = (String) intent.getSerializableExtra("email");
                        dbHelper.addNewUser(name, number, email, password).addOnSuccessListener(i ->{
                            String id = i;
                            SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
                            editor.putBoolean("isLoggedIn", true);
                            editor.putString("user_id", id);
                            editor.apply();

                            for (String genreId : selectedGenres) {
                                dbHelper.addPreferencesGenre(id, genreId);
                            }
                            for (String authorId : selectedAuthors) {
                                dbHelper.addPreferencesAuthor(id, authorId);
                            }

                        });

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
        for(String genre1 : selectedGenres){
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
        for(String genre1 : selectedAuthors){
            System.out.println(genre1);
        }
//        Toast.makeText(this, "Selected author count: " + selectedAuthors.size(), Toast.LENGTH_SHORT).show();
    }
}
