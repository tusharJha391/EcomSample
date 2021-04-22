package com.allandroidprojects.ecomsample;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.allandroidprojects.sampleecomapp.R;
import com.allandroidprojects.sampleecomapp.options.WishlistActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyService extends FirebaseMessagingService {
    public MyService() {

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        sendNotification();
        //startActivity(new Intent(MyService.this, WishlistActivity.class));
        Intent i = new Intent();
        i.setClass(this, WishlistActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }

    public void sendNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,"myChannelId")
                .setSmallIcon(R.drawable.shopping_icon_black)
                .setContentTitle("My Notification")
                .setContentText("Hello World");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(0,mBuilder.build());
        }
    }


}
