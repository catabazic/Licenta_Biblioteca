package com.example.library.Helper;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailHelper {

    public interface MailSenderCallback {
        void onSuccess();
        void onFailure(Exception e);
    }

    private final String username = "digitalbookshelfonline@gmail.com";
    private final String appPassword = "znecmprkxkqcvduu"; // Use your app password here
    private final Context context;
    private final ExecutorService executorService;

    public MailHelper(Context context) {
        this.context = context;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void sendEmail(String recipient, String subject, String body, MailSenderCallback callback) {
        Runnable sendEmailTask = () -> {
            Log.d("MailSender", "Starting sendEmailTask");

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            Log.d("MailSender", "Authenticating");
                            return new PasswordAuthentication(username, appPassword);
                        }
                    });

            try {
                Log.d("MailSender", "Creating message");
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
                message.setSubject(subject);
                message.setText(body);

                Log.d("MailSender", "Sending message");
                Transport.send(message);

                runOnUiThread(() -> {
                    Log.d("MailSender", "Email sent successfully");
                    Toast.makeText(context, "Email sent successfully!", Toast.LENGTH_SHORT).show();
                    callback.onSuccess();
                });

            } catch (MessagingException e) {
                Log.e("MailSender", "Failed to send email", e);
                runOnUiThread(() -> {
                    Toast.makeText(context, "Failed to send email", Toast.LENGTH_SHORT).show();
                    callback.onFailure(e);
                });
            }
        };

        Log.d("MailSender", "Executing sendEmailTask");
        executorService.execute(sendEmailTask);
    }

    private void runOnUiThread(Runnable action) {
        if (context instanceof Activity) {
            ((Activity) context).runOnUiThread(action);
        } else {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(action);
        }
    }
}
