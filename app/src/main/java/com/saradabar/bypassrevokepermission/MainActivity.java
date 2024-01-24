package com.saradabar.bypassrevokepermission;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setMessage("機能は有効になりました")
                .setPositiveButton("OK", (dialog, which) -> finish())
                .show();
    }
}