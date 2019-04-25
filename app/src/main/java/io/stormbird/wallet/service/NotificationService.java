package io.stormbird.wallet.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import io.stormbird.wallet.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by James on 25/04/2019.
 * Stormbird in Sydney
 */
public class NotificationService
{
    private final Context context;
    private final String CHANNEL_ID = "ALPHAWALLET CHANNEL";
    private final int NOTIFICATION_ID = 314151024;

    public NotificationService(Context ctx)
    {
        context = ctx;
        createNotificationChannel();
    }

    private void createNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            AudioAttributes attr = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            CharSequence name = context.getString(R.string.app_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.setSound(notification, attr);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @SuppressWarnings("deprecation")
    public void DisplayNotification(String title, String content, int priority)
    {
        int color;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            color = context.getColor(R.color.holo_blue);
        }
        else
        {
            color = context.getResources().getColor(R.color.holo_blue);
        }

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_alpha_notification)
                .setColor(color)
                .setContentTitle(title)
                .setContentText(content)
                .setSound(notification, 1)
                .setAutoCancel(true)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(priority);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
