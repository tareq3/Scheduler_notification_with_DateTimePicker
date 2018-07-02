/*
 * Created by Tareq Islam on 6/30/18 5:28 PM
 *
 *  Last modified 6/30/18 5:23 PM
 */

package com.mti.notification;

import android.app.AlarmManager;
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
import android.widget.Button;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private static final String TAG = "MainActivity";
LocalData mLocalData;
    Calendar mCalendar;
    TimePickerDialog mTimePickerDialog;
    DatePickerDialog mDatePickerDialog;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCalendar = Calendar.getInstance();
        mButton = findViewById(R.id.button);


mLocalData=new LocalData(this);




        mTimePickerDialog = TimePickerDialog.newInstance(
                this,
                mCalendar.get(Calendar.HOUR_OF_DAY),
                mCalendar.get(Calendar.MINUTE),
                mCalendar.get(Calendar.SECOND),
                false

        );

        mDatePickerDialog = DatePickerDialog.newInstance(
                this,
                mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)
        );

        mDatePickerDialog.show(getFragmentManager(), "Datepickerdialog");


    }


    @Override
    protected void onResume() {
        super.onResume();


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatePickerDialog.show(getFragmentManager(), "Datepickerdialog");
            }
        });


    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, monthOfYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        mTimePickerDialog.show(getFragmentManager(), "Timepickerdialog");
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mCalendar.set(Calendar.MINUTE, minute);
        mCalendar.set(Calendar.SECOND, second);



        mLocalData.setmSec(mCalendar.getTimeInMillis());
        NotificationScheduler.setReminder(MainActivity.this, AlarmReceiver.class, mLocalData.get_hour(), mLocalData.get_min(), mLocalData.getmSec());



    }


}
