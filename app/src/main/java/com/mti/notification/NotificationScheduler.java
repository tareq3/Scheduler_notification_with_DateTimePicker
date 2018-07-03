/*
 * Created by Tareq Islam on 7/1/18 1:50 AM
 *
 *  Last modified 7/1/18 1:50 AM
 */

package com.mti.notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/***
 * Created by Tareq on 01,July,2018.
 */
class NotificationScheduler {

    public NotificationScheduler() {
    }

    // private static final int DAILY_REMINDER_REQUEST_CODE = 100;
    public static   String CHANNEL_ID;

    public static void setReminder(Context context, Class<?> cls, int alram_Req_Code_ID, Long aLong) {


        // Enable a receiver

        ComponentName receiver = new ComponentName(context, cls);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);


        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        Intent notificationIntent = new Intent(context, cls);
        PendingIntent broadcast = PendingIntent.getBroadcast(context, alram_Req_Code_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (alarmManager != null) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, aLong, broadcast);
            }
        }


    }

    public static void cancelReminder(Context context,Class<?> cls,int alarm_Req_Code_ID)
    {
     /*   // Disable a receiver

        ComponentName receiver = new ComponentName(context, cls);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
*/
        Intent intent1 = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarm_Req_Code_ID, intent1, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (am != null) {
            am.cancel(pendingIntent);
            Log.d("Notification", "Delete: cancel"+alarm_Req_Code_ID);
        }
      //  pendingIntent.cancel();
    }

    public static void showNotification(Context context, Class<?> cls, String title, String content) {
        Intent notificationIntent = new Intent(context, cls);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(cls);
        stackBuilder.addNextIntent(notificationIntent);
        Toast.makeText(context, "Alarm triggered", Toast.LENGTH_SHORT).show();

        CHANNEL_ID=context.getString(R.string.default_notification_channel_id);


        createNotificationChannel(context);



        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,CHANNEL_ID);

        //region Build Notifiacation
        builder  .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                //setAutoCancel automitically remove the notification on tap
                .setAutoCancel(true);


        //Todo: As Oreo doesn't support icon from mipmap, On the other hand kitkat doesn't support from drawable.
        // we need to differentiate among them.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder .setSmallIcon(R.drawable.ic_launcher_foreground);
        }else{
            //as kitkat have some default notification sound issue we need to setSound with the builder
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder.setSound(alarmSound);

            builder.setSmallIcon(R.mipmap.ic_launcher);
        }
//endregion

        //region Show Notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
        //endregion


    }

    private static void createNotificationChannel(Context mContext) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name =mContext.getString(R.string.default_notification_channel_name);
            String description = "Ajaira description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager =mContext.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
