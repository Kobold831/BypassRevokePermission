package com.saradabar.bypassrevokepermission.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.BenesseExtension;
import android.os.PowerManager;

public class RebootActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PowerManager powerManager = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        BenesseExtension.setDchaState(3);
        powerManager.reboot(null);
        finishAndRemoveTask();
    }
}
