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

import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mti.notification.Recycler.ModelEntity.AlarmModel;
import com.mti.notification.RoomDB.AlarmDatabase;

import java.util.List;

import static com.mti.notification.MainActivity.DATABASE_NAME;

public class AlarmReceiver extends BroadcastReceiver{
    private AlarmDatabase mAlarmDatabase;
    Context mContext;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub



        if (intent.getAction() != null && context != null) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {

                mAlarmDatabase= Room.databaseBuilder(context, AlarmDatabase.class, DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();

                // Set the alarm here.
             //   Log.d("AlarmReciever", "onReceive: BOOT_COMPLETED");
             //   LocalData localData = new LocalData(context);

               final Context mContext=context;
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                      List<AlarmModel> alarmModelList=mAlarmDatabase.daoAccess().fetchAllAlarmTasks();

                       for(AlarmModel alarmModel : alarmModelList)
                       NotificationScheduler.setReminder(mContext, AlarmReceiver.class,alarmModel.getAlramId() , alarmModel.getSec());

                   }
               }).start();
                return;
            }
        }

        Log.d("AlarmReciever", "onReceive: ");

        //Trigger the notification
        NotificationScheduler.showNotification(context, MainActivity.class,
                "You have 5 unwatched videos", "Watch them now?");



    }


}