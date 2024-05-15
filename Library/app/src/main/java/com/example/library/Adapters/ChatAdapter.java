package com.example.library.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.Interfaces.OnItemClickListener;
import com.example.library.Models.Chat;
import com.example.library.R;

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
        private LinearLayout layout;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.nameChatTxt);
            message = itemView.findViewById(R.id.messageChatTxt);
            date = itemView.findViewById(R.id.dateChatTxt);
            layout = itemView.findViewById(R.id.chatLayout);
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

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        Chat chat = chatList.get(position);
        holder.date.setText(chat.getDate());
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


}
