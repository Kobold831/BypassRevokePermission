package com.saradabar.bypassrevokepermission.data.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BenesseExtension;
import android.provider.Settings;

import com.saradabar.bypassrevokepermission.data.service.BypassService;

public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null) {
            if (intent.getAction().equals(Intent.ACTION_LOCKED_BOOT_COMPLETED) || intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
                BenesseExtension.setDchaState(0);
                Settings.System.putInt(context.getContentResolver(), "hide_navigation_bar", 0);
                context.startService(new Intent(context, BypassService.class));
            }
        }
    }
}
