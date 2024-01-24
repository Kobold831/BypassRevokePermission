package com.saradabar.bypassrevokepermission.data.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.saradabar.bypassrevokepermission.data.service.BypassService;

import java.util.Objects;

public class PackageAddedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!Objects.requireNonNull(intent.getData()).toString().replace("package:", "").equals("com.saradabar.empty")) {
            switch (Objects.requireNonNull(intent.getAction())) {
                case Intent.ACTION_PACKAGE_ADDED:
                case Intent.ACTION_PACKAGE_REMOVED:
                case Intent.ACTION_PACKAGE_CHANGED:
                case Intent.ACTION_PACKAGE_REPLACED:
                    context.startService(new Intent(context, BypassService.class));
                    break;
            }
        }
    }
}