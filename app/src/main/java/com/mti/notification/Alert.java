/*
 * Created by Tareq Islam on 6/30/18 5:46 PM
 *
 *  Last modified 6/30/18 5:46 PM
 */

package com.mti.notification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//this is an extra activity for testing that we can open any activity
public class Alert extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
    }
}
