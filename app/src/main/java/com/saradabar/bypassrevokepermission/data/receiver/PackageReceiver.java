package com.saradabar.bypassrevokepermission.data.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.saradabar.bypassrevokepermission.activity.BypassActivity;
import com.saradabar.bypassrevokepermission.data.service.BypassService;
import com.saradabar.bypassrevokepermission.util.CommonUtil;

import java.util.Objects;

public class PackageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction()) && !Objects.requireNonNull(intent.getExtras()).getBoolean(Intent.EXTRA_REPLACING)) {
            run(context, intent);
        }

        if (Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {
            if (Objects.requireNonNull(intent.getExtras()).getBoolean(Intent.EXTRA_DATA_REMOVED) && intent.getExtras().getBoolean(Intent.EXTRA_REPLACING)) {
                run(context, intent);
            }

            if (!intent.getExtras().getBoolean(Intent.EXTRA_DATA_REMOVED) && intent.getExtras().getBoolean(Intent.EXTRA_REPLACING)) {
                run(context, intent);
            }
        }

        if (Intent.ACTION_PACKAGE_FULLY_REMOVED.equals(intent.getAction())) {
            run(context, intent);
        }
    }

    private void run(Context context, Intent intent) {
        if (!Objects.requireNonNull(intent.getData()).toString().replace("package:", "").equals("a.a")) {
            context.startActivity(new Intent(context, BypassActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }

        if (CommonUtil.isDeactivateReceiverService(context)) {
            context.startService(new Intent(context, BypassService.class));
        }
    }
}