package com.example.library.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.Adapters.MessagesAdapter;
import com.example.library.Adapters.ReviewBookAdapter;
import com.example.library.Database.DatabaseHelper;
import com.example.library.Models.Book;
import com.example.library.Models.Chat;
import com.example.library.Models.Message;
import com.example.library.R;

import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private ImageButton backButton;
    private ImageButton sentBtn;
    private EditText myMessage;
    private TextView name;
    private RecyclerView messages;
    private List<Message> messageList;
    private MessagesAdapter adapter;
    private Chat chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        if(intent != null) {
            chat = (Chat) intent.getSerializableExtra("chat");
        }
        backButton = findViewById(R.id.chatBackBtn);
        sentBtn = findViewById(R.id.chatSentBtn);
        myMessage = findViewById(R.id.chatMassagetxt);

        name = findViewById(R.id.chatNameTxt);
        name.setText(chat.getName());

        messages = findViewById(R.id.MessagesrecyclerView);



        DatabaseHelper dbHelper = new DatabaseHelper(ChatActivity.this);
        messageList = dbHelper.getAllMessages(chat.getId());

        messages.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessagesAdapter(messageList, MainActivity.sharedPreferences.getInt("user_id",-1));
        messages.setAdapter(adapter);
        messages.scrollToPosition(adapter.getItemCount() - 1);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = myMessage.getText().toString();
                if(!message.isEmpty()){
                    dbHelper.addMessage(chat.getId(),MainActivity.sharedPreferences.getInt("user_id",-1),message);
                    messageList = dbHelper.getAllMessages(chat.getId());
                    adapter.updateData(messageList);
                    myMessage.setText("");
                    messages.scrollToPosition(adapter.getItemCount() - 1);
                }
            }
        });

    }
}
