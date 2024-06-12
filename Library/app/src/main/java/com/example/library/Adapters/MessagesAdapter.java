package com.example.library.Adapters;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.Interfaces.OnItemClickListener;
import com.example.library.Models.DB.Book;
import com.example.library.Models.Chat;
import com.example.library.Models.DB.Message;
import com.example.library.R;

import java.util.List;

import android.view.Gravity;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {
    private List<Message> messageList;

    private String idUser;
    public MessagesAdapter(List<Message> messageList, String idUser) {
        this.messageList = messageList;
        this.idUser = idUser;
    }

    public void updateData(List<Message> newData) {
        messageList.clear(); // Clear the existing dataset
        messageList.addAll(newData); // Add the new data
        notifyDataSetChanged(); // Notify the adapter that the dataset has changed
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textMessage;

        ViewHolder(View itemView) {
            super(itemView);
            textMessage = itemView.findViewById(R.id.TextMessageTxt);
        }

    }

    @NonNull
    @Override
    public MessagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesAdapter.ViewHolder holder, int position) {
        Message message = messageList.get(position);

        holder.textMessage.setText(message.getMessage());

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.textMessage.getLayoutParams();
        Drawable backgroundDrawable = holder.textMessage.getBackground();


        if (message.getId_user().equals(idUser)) {
            params.gravity = Gravity.END;
            holder.textMessage.setLayoutParams(params);
            DrawableCompat.setTint(backgroundDrawable, 0x3C6750A3);
        } else {
            params.gravity = Gravity.START;
            holder.textMessage.setLayoutParams(params);
            DrawableCompat.setTint(backgroundDrawable, 0x856750A3);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            holder.textMessage.setBackground(backgroundDrawable);
        } else {
            holder.textMessage.setBackgroundDrawable(backgroundDrawable);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
