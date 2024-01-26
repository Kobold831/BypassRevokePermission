package com.saradabar.bypassrevokepermission.data.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.saradabar.bypassrevokepermission.data.receiver.DynamicReceiver;

public class BypassService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SHUTDOWN);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(new DynamicReceiver(), intentFilter);
        return START_STICKY;
    }
}