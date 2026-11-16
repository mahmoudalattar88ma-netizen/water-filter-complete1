package com.example.waterfilter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context context, Intent intent) {
        if (intent == null) return;
        String action = intent.getAction();
        if (!Intent.ACTION_BOOT_COMPLETED.equals(action)
                && !Intent.ACTION_MY_PACKAGE_REPLACED.equals(action)
                && !Intent.ACTION_TIME_CHANGED.equals(action)
                && !Intent.ACTION_TIMEZONE_CHANGED.equals(action)) return;
        SharedPreferences p = context.getSharedPreferences(MainActivity.PREFS, Context.MODE_PRIVATE);
        if (!p.getBoolean(MainActivity.ENABLED_KEY, true)) return;
        String savedFilters = p.getString(MainActivity.DATA_KEY, "[]");
        try {
            MainActivity.scheduleAll(context, savedFilters);
            Log.i("WaterFilter", "Reminder alarms restored after: " + action);
        } catch (Exception e) { Log.e("WaterFilter", "Could not restore reminder alarms", e); }
    }
}
