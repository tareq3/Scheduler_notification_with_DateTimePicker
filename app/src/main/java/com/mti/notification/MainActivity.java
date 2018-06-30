/*
 * Created by Tareq Islam on 6/30/18 5:28 PM
 *
 *  Last modified 6/30/18 5:23 PM
 */

package com.mti.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
 String CHANNEL_ID;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=getApplicationContext();
  CHANNEL_ID =getString(R.string.default_notification_channel_id);

  createNotificationChannel();
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //region Tap Action for the Notifiacation
                // Create an explicit intent for an Activity in your app
                Intent intent = new Intent(mContext, Alert.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
                //endregion

                NotificationCompat.Builder builder=new NotificationCompat.Builder(mContext,CHANNEL_ID);

                //region Build Notifiacation
               builder  .setContentTitle("Tareq")
                        .setContentText("Is a kid")

                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        // Set the intent that will fire when the user taps the notification
                        .setContentIntent(pendingIntent)
                        //setAutoCancel automitically remove the notification on tap
                        .setAutoCancel(true);
                //endregion

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    builder .setSmallIcon(R.drawable.ic_launcher_foreground);
                }else{
                    //as kitkat have some default notification sound issue we need to setSound with the builder
                      Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        builder.setSound(alarmSound);


                      builder.setSmallIcon(R.mipmap.ic_launcher);
                }


                //region Show Notification
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);

                // notificationId is a unique int for each notification that you must define
                notificationManager.notify(1, builder.build());
                //endregion
            }
        });
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.default_notification_channel_name);
            String description = "Ajaira description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
