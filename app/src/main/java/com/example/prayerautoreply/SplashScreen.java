package com.example.prayerautoreply;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
private static int timeout=4000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeintent= new Intent(SplashScreen.this,MainActivity.class);
                startActivity(homeintent);
                finish();
            }
        },timeout);

       // startActivity(new Intent(this,MainActivity.class));
    }
}
