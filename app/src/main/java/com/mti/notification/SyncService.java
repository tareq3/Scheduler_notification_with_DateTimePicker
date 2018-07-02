/*
 * Created by Tareq Islam on 7/1/18 1:09 AM
 *
 *  Last modified 7/1/18 1:09 AM
 */

package com.mti.notification;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/***
 * Created by Tareq on 01,July,2018.
 */
public class SyncService extends IntentService {


    public SyncService() {
        super("SyncService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
