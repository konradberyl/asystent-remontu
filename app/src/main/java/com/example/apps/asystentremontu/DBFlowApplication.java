package com.example.apps.asystentremontu;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by Beryl
 * on 18.04.2016
 */
public class DBFlowApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }

}
