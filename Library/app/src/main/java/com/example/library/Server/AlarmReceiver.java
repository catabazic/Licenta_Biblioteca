package com.example.library.Server;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.library.R;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "daily_notification";
    private static final int NOTIFICATION_ID = 100;

    /*@Override
    public void onReceive(Context context, Intent intent) {
        // Trigger the notification
        sendNotification(context);
    }*/

    @Override
    public void onReceive(Context context, Intent intent) {
        // Start your service when the alarm triggers
        Intent serviceIntent = new Intent(context, MyBackgroundService.class);
        context.startService(serviceIntent);
    }

    private void sendNotification(Context context) {
        // Create a notification channel (required for Android 8.0 and higher)
        createNotificationChannel(context);

        // Create the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Daily Notification")
                .setContentText("Don't forget to check your tasks for today!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        //notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void createNotificationChannel(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "Daily Notification";
            String description = "Reminder to check daily tasks";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.createNotificationChannel(channel);
        }
    }
}