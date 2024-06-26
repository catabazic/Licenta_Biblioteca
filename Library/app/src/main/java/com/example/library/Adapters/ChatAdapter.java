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
import com.example.library.Models.Chat;
import com.example.library.R;
import com.google.firebase.Timestamp;
import com.squareup.picasso.Picasso;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    List<Chat> chatList;
    private OnItemClickListener listener;
    private int[] colors = {0x5BFFFFFF,0xA6FFFFFF};


    public ChatAdapter(List<Chat> chatList, OnItemClickListener listener) {
        this.chatList = chatList;
        this.listener=listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name;
        private TextView message;
        private TextView date;
        private ImageView profilePhoto;
        private LinearLayout layout;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.userName);
            message = itemView.findViewById(R.id.bookAuthor);
            date = itemView.findViewById(R.id.dateChatTxt);
            layout = itemView.findViewById(R.id.chatLayout);
            profilePhoto = itemView.findViewById(R.id.user_img3);
        }


        @Override
        public void onClick(View v) {
            System.out.println("i am here");
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    System.out.println("i guess i should be here");
                    Chat clickedChat = chatList.get(position);
                    listener.onItemClick(clickedChat);
                }
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
        return new ChatAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        Chat chat = chatList.get(position);
        FirebaseDatabaseHelper db = new FirebaseDatabaseHelper();
        db.getUserById(chat.getIdUser(), user ->{
            String imageUrl = user.getPhoto();
            if(imageUrl!=null) {
                System.out.println(imageUrl);
                Picasso.get()
                        .load(imageUrl)
                        .into(holder.profilePhoto);
            }else{
                System.out.println("no photo");
            }
        });
        String d = getFormattedDate(chat.getTimestapt());
        holder.date.setText(d);
        holder.name.setText(chat.getName());
        String mess = new String();
        if (chat.isLastMessageMine()) {
            mess += "You: " + chat.getMessage();
        } else {
            mess += chat.getName() + ": " + chat.getMessage();
        }
        holder.message.setText(mess);
        int colorIndex=position % colors.length;
        holder.layout.setBackgroundColor(colors[colorIndex]);
    }

    @Override
    public int getItemCount() {
        return chatList.size();
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
