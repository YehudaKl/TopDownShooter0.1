package com.example.TopDownShooter.classes.systems;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.widget.Toast;


public class PremiumAuthenticator {

    public static boolean authenticate(Context context){
        if(!isBattery(context)){
            Toast.makeText(context, "Not enough battery for premium", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private static boolean isBattery(Context context){
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, intentFilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level * 100 / (float)scale;

        return (batteryPct > 20);

    }

    private static boolean isBarcode(){
        return true;
    }
}
