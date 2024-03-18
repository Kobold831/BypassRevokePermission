package com.saradabar.bypassrevokepermission;

import static android.content.pm.PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
import static android.content.pm.PackageManager.DONT_KILL_APP;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.saradabar.bypassrevokepermission.activity.BypassActivity;
import com.saradabar.bypassrevokepermission.data.service.BypassService;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        startService(new Intent(this, BypassService.class));
        startActivity(new Intent(this, BypassActivity.class));
        getPackageManager().setComponentEnabledSetting(new ComponentName(this, getClass()), COMPONENT_ENABLED_STATE_DISABLED, DONT_KILL_APP);
        Toast.makeText(this, "機能は有効になりました", Toast.LENGTH_LONG).show();
        finish();
    }
}
