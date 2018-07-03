/*
 * Created by Tareq Islam on 7/3/18 1:16 AM
 *
 *  Last modified 7/3/18 1:16 AM
 */

package com.mti.notification.RoomDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.support.v4.app.AppLaunchChecker;

import com.mti.notification.Recycler.ModelEntity.AlarmModel;

import java.util.List;

/***
 * Created by Tareq on 03,July,2018.
 */
@Dao
public interface DaoAccess {

    //Todo: Crud right here

    @Insert
    void inserSingleAlarmTask(AlarmModel alarmModel);

    @Query("SELECT * FROM AlarmModel")
    List<AlarmModel> fetchAllAlarmTasks();

    @Query("DELETE FROM AlarmModel")
    abstract void clearAlarms();

    /*@Query("DELETE FROM AlarmModel WHERE alramId = :m_alarmId")
    abstract void deleteSingleAlarm(int m_alarmId);*/


    @Query("SELECT * FROM AlarmModel   ORDER BY alramId DESC    LIMIT 1")
    AlarmModel getLastEntry();

    @Delete
    void deleteSingleAlarm(AlarmModel model);
}
