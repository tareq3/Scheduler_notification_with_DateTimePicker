/*
 * Created by Tareq Islam on 6/30/18 11:15 PM
 *
 *  Last modified 6/30/18 11:00 PM
 */

/*
 * Created by Tareq Islam on 6/30/18 3:37 PM
 *
 *  Last modified 6/30/18 3:37 PM
 */

package com.mti.notification;

/***
 * Created by Tareq on 30,June,2018.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver{

    Context mContext;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        if (intent.getAction() != null && context != null) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
                // Set the alarm here.
                Log.d("AlarmReciever", "onReceive: BOOT_COMPLETED");
                LocalData localData = new LocalData(context);
                NotificationScheduler.setReminder(context, AlarmReceiver.class, localData.get_hour(), localData.get_min(), localData.getmSec());
                return;
            }
        }

        Log.d("AlarmReciever", "onReceive: ");

        //Trigger the notification
        NotificationScheduler.showNotification(context, MainActivity.class,
                "You have 5 unwatched videos", "Watch them now?");



    }


}