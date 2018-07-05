/*
 * Created by Tareq Islam on 6/30/18 5:28 PM
 *
 *  Last modified 6/30/18 5:23 PM
 */

package com.mti.notification;

import android.app.Notification;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mti.notification.Recycler.Adapter.ListItemAdapter;
import com.mti.notification.Recycler.ModelEntity.AlarmModel;
import com.mti.notification.RoomDB.AlarmDatabase;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "MainActivity";


    //Declare view
    TimePickerDialog mTimePickerDialog;
    DatePickerDialog mDatePickerDialog;
    Calendar mCalendar;
    Button mButton;
    EditText mTitleEditField;


    //Declare Recycler
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManagerRecyler;

    ListItemAdapter mAdapter;

   public static List<AlarmModel> mToDoList=new ArrayList<>();


    //RoomDB Declaration

    public static final String DATABASE_NAME = "alarm_db";
    private AlarmDatabase mAlarmDatabase;


    //Other Declarations

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCalendar = Calendar.getInstance();
        mButton = findViewById(R.id.button);
        mTitleEditField = findViewById(R.id.editTitle);

        mAlarmDatabase = Room.databaseBuilder(getApplicationContext(), AlarmDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();



        initDateTimePicker();

        initRecyclerView();

        loadData2List();

    }

    private void initDateTimePicker() {

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

        //mDatePickerDialog.show(getFragmentManager(), "Datepickerdialog");

    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManagerRecyler = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManagerRecyler);

        loadData2RecyclerAdapter();
    }


    @Override
    protected void onResume() {
        super.onResume();


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       mAlarmDatabase.daoAccess().clearAlarms();

                       loadData2List();

                       //if no alarm on the list then disable the reciever for battery saving
                       NotificationScheduler.disableReciever(getApplicationContext(),AlarmReceiver.class);
                   }
               }).start();
            }
        });

        mTitleEditField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                mDatePickerDialog.show(getFragmentManager(), "Datepickerdialog");

                return false;
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


        lockNotification();


    }

    private void lockNotification() {

        if (!(mTitleEditField == null)) {
            //Todo: This method have two portion. First set data into pojo , Second Set the Reminder

            //First Part// Set data into pojo and Save data in Database so that we can use with braodcaster
            // mLocalData.setmSec(mCalendar.getTimeInMillis());

            final AlarmModel.Builder alarmModelBuilder = new AlarmModel.Builder()
                    //we skipped id cz we only need at data storing
                    .withTitle(mTitleEditField.getText().toString())
                    .withMSec(mCalendar.getTimeInMillis());

            new Thread(new Runnable() {
                @Override
                public void run() {
           //         int i = mAlarmDatabase.daoAccess().fetchAllAlarmTasks().size();

                   AlarmModel alarmModel = alarmModelBuilder.build();

                    mAlarmDatabase.daoAccess().inserSingleAlarmTask(alarmModel);

                    alarmModel=mAlarmDatabase.daoAccess().getLastEntry();

                    Log.d("" +getClass().getName(), "Test" +alarmModel.getAlramId());
                NotificationScheduler.setReminder(getApplicationContext(), AlarmReceiver.class,alarmModel.getAlramId() , alarmModel.getSec());


                    loadData2List();
                    //Second Part


                  }
            }).start();
        }
    }

    private void loadData2List() {

        new Thread(new Runnable() {
            @Override
            public void run() {

              mToDoList = mAlarmDatabase.daoAccess().fetchAllAlarmTasks();

                loadData2RecyclerAdapter();
            }
        }).start();



    }

    private void loadData2RecyclerAdapter() {

runOnUiThread(new Runnable() {
    @Override
    public void run() {
        //Todo: First add Shimmer or loader dialog

        mAdapter = new ListItemAdapter(MainActivity.this, mToDoList);
        mRecyclerView.setAdapter(mAdapter);
    }
});

    }

    public static String ConvertMilliSecondsToFormattedDate(long milliSeconds){
       String dateFormat = "dd-MM-yyyy hh:mm";
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return simpleDateFormat.format(calendar.getTime());
    }



    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals("DELETE")) {

            Toast.makeText(this, "delete" + item.getOrder(), Toast.LENGTH_SHORT).show();

            deleteItem(item.getOrder());
        }
        return super.onContextItemSelected(item);
    }

    private void deleteItem(final int order) {
//Todo: By using the oreder of the item and the List we can get the model class which can provide us model attributes for db call etc....
     final   AlarmModel model=mToDoList.get(order);
        new Thread(new Runnable() {
            @Override
            public void run() {
//           Todo: For deleting a reminder: (1) first cancel the reminder then , (2) delete the task from database, (3) reload the view


                Log.d("" +getClass().getName(), "Delete"+model.getAlramId());

                //deleting item
                mAlarmDatabase.daoAccess().deleteSingleAlarm(model);


                //reloading the recyclerView
                 loadData2List();

                //cancel the reminder
                NotificationScheduler.cancelReminder(getApplicationContext(), AlarmReceiver.class,model.getAlramId());

            }
        }).start();


    }
}
