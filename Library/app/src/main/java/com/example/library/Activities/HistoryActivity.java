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
import com.example.library.Database.DatabaseHelper;
import com.example.library.Interfaces.OnItemClickListener;
import com.example.library.Models.Book;
import com.example.library.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HistoryActivity extends AppCompatActivity implements OnItemClickListener {
    private RecyclerView recyclerView; //booksRecView
    private BookAdapter adapter;
    private List<Book> bookList;
    private ImageButton backButton;
    private TextView text;

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


        DatabaseHelper dbHelper = new DatabaseHelper(HistoryActivity.this);
        if(type.equals("history")) {
            bookList = dbHelper.getAllHistory(MainActivity.sharedPreferences.getInt("user_id", -1));
        }else if (type.equals("my books")){
            bookList = dbHelper.getBorrowedBooks(MainActivity.sharedPreferences.getInt("user_id",-1));
            text.setText("Your books");
        }
        adapter = new BookAdapter(bookList, this);
        recyclerView.setAdapter(adapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onItemClick(Object book) {
        DatabaseHelper dbHelper = new DatabaseHelper(HistoryActivity.this);
        Book thisBook = (Book) book;
//        dbHelper.getBookByNameAndAuthor(thisBook.getName(), thisBook.getAuthors());
        Intent intent = new Intent(HistoryActivity.this, SelectedBookActivity.class);
        intent.putExtra("book", thisBook);
//        intent.putExtra("fromPage", this);
        startActivity(intent);
    }

}