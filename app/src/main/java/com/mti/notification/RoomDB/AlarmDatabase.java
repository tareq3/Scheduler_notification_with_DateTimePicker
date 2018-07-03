/*
 * Created by Tareq Islam on 7/3/18 1:20 AM
 *
 *  Last modified 7/3/18 1:20 AM
 */

package com.mti.notification.RoomDB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.mti.notification.Recycler.ModelEntity.AlarmModel;

/***
 * Created by Tareq on 03,July,2018.
 */
@Database(entities = {AlarmModel.class},version = 2, exportSchema = false)
public abstract class AlarmDatabase extends RoomDatabase {
    public abstract DaoAccess daoAccess();
}
