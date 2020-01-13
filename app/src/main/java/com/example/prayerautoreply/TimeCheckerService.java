package com.example.prayerautoreply;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class TimeCheckerService  extends Service {


    AudioManager am;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {


        Toast.makeText(this,"OnBind invoked",Toast.LENGTH_SHORT).show();
        return null;
    }


    String mytime="05:22 pm";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hh:mm a", Locale.getDefault());

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"Service Started",Toast.LENGTH_SHORT).show();


        String currentDateandTime = sdf.format(new Date());

            String []time = currentDateandTime.split("_");
            Toast.makeText(this,time[1],Toast.LENGTH_SHORT).show();
            if(time[1].equals(mytime))
            {

                am= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);

//For Normal mode
                am.setRingerMode(AudioManager.RINGER_MODE_SILENT);

            }


            //do something




        return START_STICKY;
    }




    @Override
    public void onDestroy() {

//        super.onDestroy();

    Toast.makeText(this,"Service Destroyed",Toast.LENGTH_SHORT).show();


    }



}
