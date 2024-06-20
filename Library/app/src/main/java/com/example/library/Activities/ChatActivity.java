package com.example.library.Activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.Adapters.MessagesAdapter;
import com.example.library.Database.FirebaseDatabaseHelper;
import com.example.library.Models.Chat;
import com.example.library.Models.DB.Message;
import com.example.library.Models.DB.User;
import com.example.library.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private ImageButton backButton;
    private ImageButton sentBtn;
    private EditText myMessage;
    private TextView name;
    private RecyclerView messages;
    private List<Message> messageList;
    private MessagesAdapter adapter;
    private ImageView profilePhoto;
    private Chat chat;
    private FirebaseDatabaseHelper dbHelper;
    private User user;

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
        profilePhoto = findViewById(R.id.user_img3);

        name = findViewById(R.id.nameTxt);
        name.setText(chat.getName());

        messages = findViewById(R.id.MessagesrecyclerView);



        dbHelper = new FirebaseDatabaseHelper();

        dbHelper.getUserById(chat.getIdUser(), user ->{
            this.user = user;
            String imageUrl = user.getPhoto();
            if(imageUrl!=null) {
                System.out.println(imageUrl);
                Picasso.get()
                        .load(imageUrl)
                        .into(profilePhoto);
            }else{
                System.out.println("no photo");
            }
        });
        dbHelper.getAllMessages(chat.getId()).addOnCompleteListener(new OnCompleteListener<List<Message>>() {
            @Override
            public void onComplete(@NonNull Task<List<Message>> task) {
                if (task.isSuccessful()) {
                    messageList = task.getResult();
                    adapter = new MessagesAdapter(messageList, MainActivity.sharedPreferences.getString("user_id", null));
                    messages.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
                    adapter = new MessagesAdapter(messageList, MainActivity.sharedPreferences.getString("user_id", null));
                    messages.setAdapter(adapter);
                    messages.scrollToPosition(adapter.getItemCount() - 1);
                } else {
                    Log.d(TAG, "Error getting messages: ", task.getException());
                }
            }
        });

        profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this, SelectedUserActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("page", "chat");
                startActivity(intent);
            }
        });



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
                    dbHelper.addMessage(chat.getId(),MainActivity.sharedPreferences.getString("user_id",null),message);
                    dbHelper.getAllMessages(chat.getId()).addOnCompleteListener(l ->{
                        messageList = l.getResult();
                        adapter.updateData(messageList);
                        myMessage.setText("");
                        messages.scrollToPosition(adapter.getItemCount() - 1);
                    });
                }
            }
        });

    }
}
