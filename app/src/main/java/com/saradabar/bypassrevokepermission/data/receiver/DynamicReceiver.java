package com.saradabar.bypassrevokepermission.data.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BenesseExtension;

import com.saradabar.bypassrevokepermission.data.service.BypassService;
import com.saradabar.bypassrevokepermission.util.CommonUtil;

import java.util.Objects;

public class DynamicReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.requireNonNull(intent.getAction()).equals(Intent.ACTION_SHUTDOWN)) {
            BenesseExtension.setDchaState(3);
        }

        if (Objects.requireNonNull(intent.getAction()).equals(Intent.ACTION_SCREEN_ON)) {
            if (CommonUtil.isDeactivateReceiverService(context)) {
                context.startService(new Intent(context, BypassService.class));
            }
        }
    }
}