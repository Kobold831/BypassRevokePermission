package com.saradabar.bypassrevokepermission.data.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.BenesseExtension;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import jp.co.benesse.dcha.dchaservice.IDchaService;

public class BypassService extends Service {

    IDchaService mDchaService;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bindService(new Intent("jp.co.benesse.dcha.dchaservice.DchaService").setPackage("jp.co.benesse.dcha.dchaservice"), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                mDchaService = IDchaService.Stub.asInterface(iBinder);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
            }
        }, Context.BIND_AUTO_CREATE);

        Runnable runnable = () -> {
            BenesseExtension.setDchaState(3);
        };
        new Handler().postDelayed(runnable, 1000);

        copyAssetsFile();

        Runnable runnable2 = () -> {
            try {
                mDchaService.installApp(Environment.getExternalStorageDirectory() + "/" + "base.apk", 1);
            } catch (Exception ignored) {
            }
        };
        new Handler().postDelayed(runnable2, 1000);

        Runnable runnable3 = () -> {
            BenesseExtension.setDchaState(0);
        };
        new Handler().postDelayed(runnable3, 1000);
        return START_NOT_STICKY;
    }

    private void copyAssetsFile() {
        try {
            InputStream inputStream = getAssets().open("base.apk");
            FileOutputStream fileOutputStream = new FileOutputStream(Environment.getExternalStorageDirectory() + "/" + "base.apk", false);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) >= 0) {
                fileOutputStream.write(buffer, 0, length);
            }
            fileOutputStream.close();
            inputStream.close();
        } catch (IOException ignored) {
        }
    }
}
