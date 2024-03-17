package com.saradabar.bypassrevokepermission.activity;

import android.app.Activity;
import android.content.Context;
import android.os.BenesseExtension;
import android.os.Bundle;
import android.os.PowerManager;

public class ShutdownActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PowerManager powerManager = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        try {
            BenesseExtension.setDchaState(3);
            String SHUTDOWN = "shutdown";
            PowerManager.class.getMethod(SHUTDOWN, boolean.class, String.class, boolean.class).invoke(powerManager, false, SHUTDOWN, false);
        } catch (Exception ignored) {
        }
        finishAndRemoveTask();
    }
}
