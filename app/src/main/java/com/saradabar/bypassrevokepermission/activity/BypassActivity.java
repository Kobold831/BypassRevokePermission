package com.saradabar.bypassrevokepermission.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.BenesseExtension;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import com.saradabar.bypassrevokepermission.data.service.BypassService;
import com.saradabar.bypassrevokepermission.util.CommonUtil;

import jp.co.benesse.dcha.dchaservice.IDchaService;

public class BypassActivity extends Activity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        new Handler().postDelayed(this::run, 800);
    }

    private void run() {
        BenesseExtension.setDchaState(3);
        CommonUtil.copyAssetsFile(this);
        bindService(new Intent("jp.co.benesse.dcha.dchaservice.DchaService").setPackage("jp.co.benesse.dcha.dchaservice"), new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                IDchaService mDchaService = IDchaService.Stub.asInterface(iBinder);
                try {
                    mDchaService.hideNavigationBar(false);
                    mDchaService.installApp(getExternalCacheDir() + "/" + "base.apk", 2);
                    mDchaService.uninstallApp("a.a", 0);
                } catch (Exception ignored) {
                }
                unbindService(this);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
            }
        }, Context.BIND_AUTO_CREATE);

        new Handler().postDelayed(() -> BenesseExtension.setDchaState(0), 800);

        if (CommonUtil.isDeactivateReceiverService(this)) {
            startService(new Intent(this, BypassService.class));
        }

        finishAndRemoveTask();
    }

    @Override
    public void onBackPressed() {
    }
}
