package com.saradabar.bypassrevokepermission.util;

import android.app.ActivityManager;
import android.content.Context;

import com.saradabar.bypassrevokepermission.data.service.BypassService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CommonUtil {

    public static void copyAssetsFile(Context context) {
        try {
            if (new File(context.getExternalCacheDir() + "/" + "base.apk").exists()) {
                return;
            }

            InputStream inputStream = context.getAssets().open("base.apk");
            FileOutputStream fileOutputStream = new FileOutputStream(context.getExternalCacheDir() + "/" + "base.apk", false);
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

    @SuppressWarnings("deprecation")
    public static boolean isDeactivateReceiverService(Context context) {
        for (ActivityManager.RunningServiceInfo serviceInfo : ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningServices(Integer.MAX_VALUE)) {
            if (BypassService.class.getName().equals(serviceInfo.service.getClassName())) {
                return false;
            }
        }
        return true;
    }
}
