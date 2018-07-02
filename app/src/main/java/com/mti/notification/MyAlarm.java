/*
 * Created by Tareq Islam on 6/30/18 11:56 PM
 *
 *  Last modified 6/30/18 11:56 PM
 */

package com.mti.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Calendar;

/***
 * Created by Tareq on 30,June,2018.
 */
public class MyAlarm {





public static void setAlarm(Context context,Calendar calendar){
Calendar calendarCurrent=Calendar.getInstance();

//cancel reminder




        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(context, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (alarmManager != null) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcast);
            }
        }

    }

}
