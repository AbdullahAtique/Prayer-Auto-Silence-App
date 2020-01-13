package com.example.prayerautoreply;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

public class ServiceRestarter extends BroadcastReceiver {



    //purpose of his reciever is to restart my TIme checker service when it gets destroyed

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Broadcast Listened", "Service tried to stop");
        Toast.makeText(context, "Service restarted", Toast.LENGTH_SHORT).show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, TimeCheckerService.class));
        } else {
            context.startService(new Intent(context, TimeCheckerService.class));
        }
    }
}
