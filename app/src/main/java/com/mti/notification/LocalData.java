/*
 * Created by Tareq Islam on 7/1/18 1:30 AM
 *
 *  Last modified 7/1/18 1:30 AM
 */

package com.mti.notification;

import android.content.Context;
import android.content.SharedPreferences;

/***
 * Created by Tareq on 01,July,2018.
 */
public class LocalData {

    private static  final String APP_SHARED_PREF="RemindMePref";
    private SharedPreferences appSharedPref;
    private SharedPreferences.Editor prefEditor;


    private static final String reminderStatus="reminderStatus";

    private static final String mSec="mSec";

    public  Long getmSec() {
        return appSharedPref.getLong(mSec, 0);
    }

    public void setmSec(Long sec){

        prefEditor.putLong(mSec, sec);
        prefEditor.commit();
    }


    public LocalData(Context context) {
        this.appSharedPref=context.getSharedPreferences(APP_SHARED_PREF, Context.MODE_PRIVATE);
        this.prefEditor=appSharedPref.edit();
    }

    //Setting page Set Reminder

    public boolean getReminderStatus(){
        return appSharedPref.getBoolean(reminderStatus,false);

    }

    public void setReminderStatus(boolean status){
        prefEditor.putBoolean(reminderStatus,status);
        prefEditor.commit();

    }

    public void reset()
    {
        prefEditor.clear();
        prefEditor.commit();

    }

}
