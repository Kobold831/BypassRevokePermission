package com.saradabar.bypassrevokepermission.data.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.BenesseExtension;
import android.os.PowerManager;

import java.lang.reflect.InvocationTargetException;

public class ShutdownActivity extends Activity {
    private static final String SHUTDOWN = "shutdown";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        finishAndRemoveTask();
        PowerManager powerManager = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        try {
            BenesseExtension.setDchaState(3);
            PowerManager.class.getMethod(SHUTDOWN, boolean.class, String.class, boolean.class)
                    .invoke(powerManager, false, SHUTDOWN, false);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException ignored) {
        }
    }
}
