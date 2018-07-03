/*
 * Created by Tareq Islam on 7/3/18 12:43 AM
 *
 *  Last modified 7/3/18 12:43 AM
 */

package com.mti.notification.Recycler.ModelEntity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/***
 * Created by Tareq on 03,July,2018.
 */
@Entity
public class AlarmModel {

    @PrimaryKey(autoGenerate = true)
    private int alramId=0;

    private String title;

    private long mSec;

    public AlarmModel() {
    }

    private AlarmModel(Builder builder) {
        setAlramId(builder.alramId);
        setTitle(builder.title);
        setSec(builder.mSec);
    }

    public int getAlramId() {
        return alramId;
    }

    public void setAlramId(int alramId) {
        this.alramId = alramId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getSec() {
        return mSec;
    }

    public void setSec(long sec) {
        mSec = sec;
    }


    public static final class Builder {
        private int alramId;
        private String title;
        private long mSec;

        public Builder() {
        }

        public Builder withAlramId(int val) {
            alramId = val;
            return this;
        }

        public Builder withTitle(String val) {
            title = val;
            return this;
        }

        public Builder withMSec(long val) {
            mSec = val;
            return this;
        }

        public AlarmModel build() {
            return new AlarmModel(this);
        }
    }
}
