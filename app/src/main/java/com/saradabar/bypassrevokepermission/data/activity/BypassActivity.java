package com.saradabar.bypassrevokepermission.data.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.BenesseExtension;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import com.saradabar.bypassrevokepermission.data.service.BypassService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import jp.co.benesse.dcha.dchaservice.IDchaService;

public class BypassActivity extends Activity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        run(this);
        Toast.makeText(this, "実行しました", Toast.LENGTH_SHORT).show();
        finishAndRemoveTask();
    }

    public static void run(Context context) {
        BenesseExtension.setDchaState(3);
        copyAssetsFile(context);
        context.bindService(new Intent("jp.co.benesse.dcha.dchaservice.DchaService").setPackage("jp.co.benesse.dcha.dchaservice"), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                IDchaService mDchaService = IDchaService.Stub.asInterface(iBinder);
                try {
                    mDchaService.hideNavigationBar(false);
                    mDchaService.installApp(context.getExternalFilesDir("") + "/" + "base.apk", 2);
                    mDchaService.uninstallApp("a.a", 1);
                } catch (Exception ignored) {
                }
                context.unbindService(this);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
            }
        }, Context.BIND_AUTO_CREATE);

        Runnable runnable = () -> BenesseExtension.setDchaState(0);
        new Handler().postDelayed(runnable, 800);

        if (!isActiveBypassService(context)) {
            context.startService(new Intent(context, BypassService.class));
        }
    }

    public static void copyAssetsFile(Context context) {
        try {
            if (new File(context.getExternalFilesDir("") + "/" + "base.apk").exists()) {
                return;
            }
            InputStream inputStream = context.getAssets().open("base.apk");
            FileOutputStream fileOutputStream = new FileOutputStream(context.getExternalFilesDir("") + "/" + "base.apk", false);
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

    public static boolean isActiveBypassService(Context context) {
        for (ActivityManager.RunningServiceInfo serviceInfo : ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningServices(Integer.MAX_VALUE)) {
            if (BypassService.class.getName().equals(serviceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}