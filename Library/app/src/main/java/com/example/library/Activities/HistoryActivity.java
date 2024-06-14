package com.example.library.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.Adapters.BookAdapter;
import com.example.library.Database.FirebaseDatabaseHelper;
import com.example.library.Interfaces.OnItemClickListener;
import com.example.library.Models.DB.Book;
import com.example.library.R;

import java.util.List;

public class HistoryActivity extends AppCompatActivity implements OnItemClickListener {
    private RecyclerView recyclerView; //booksRecView
    private BookAdapter adapter;
    private ImageButton backButton;
    private TextView text;
    private FirebaseDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();
        String type = null;
        if(intent != null) {
            type = (String) intent.getSerializableExtra("type");
        }

        recyclerView = findViewById(R.id.booksHistoryRecView);
        backButton=findViewById(R.id.backBtn);
        text = findViewById(R.id.historyIntroTxt);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*bookList = new ArrayList<>();
        bookList.add(new Book("Litera stacojie", "NATHANIEL HAWTHORNE", "Stare: Imprumut pana pe data de 15.06.2024"));
        bookList.add(new Book("Another Book", "Another Author", "Another State"));*/


        dbHelper = new FirebaseDatabaseHelper();
        if(type.equals("history")) {
            dbHelper.getAllHistory(MainActivity.sharedPreferences.getString("user_id", null)).addOnSuccessListener(l ->{
                adapter = new BookAdapter(l, this);
                recyclerView.setAdapter(adapter);
            }).addOnFailureListener(t->{
                System.out.println(t);
            });
        }else if (type.equals("my books")){
            dbHelper.getBorrowedBooks(MainActivity.sharedPreferences.getString("user_id",null)).addOnSuccessListener(l ->{
                adapter = new BookAdapter(l, this);
                recyclerView.setAdapter(adapter);
            });
            text.setText("Your books");
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onItemClick(Object book) {
        Book thisBook = (Book) book;
//        dbHelper.getBookByNameAndAuthor(thisBook.getName(), thisBook.getAuthors());
        Intent intent = new Intent(HistoryActivity.this, SelectedBookActivity.class);
        intent.putExtra("book", thisBook);
//        intent.putExtra("fromPage", this);
        startActivity(intent);
    }

}