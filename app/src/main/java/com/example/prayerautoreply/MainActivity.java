package com.example.prayerautoreply;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient client;
    Button button;
    Button location;
    Context context;
    Intent intent1;
    TextView textview;

    double longit;
    double latit;
    LocationManager locationManager;
    boolean GpsStatus;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        textview = (TextView) findViewById(R.id.texts);
        button = findViewById(R.id.button);
        location= findViewById(R.id.button2);


        context = getApplicationContext();
//        boolean gpsstatus = CheckGpsStatus();



//        if(gpsstatus== true)
//        {
//            getOfflineloc(loc);
//
////            setLocation();
//        }
//        else
//        {
//            enableLocation();
//        }
////        setLocation();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(CheckGpsStatus()) {
//                      setLocation();

                     Intent home= new Intent(MainActivity.this,PrayerActivity.class);


                    Bundle b = new Bundle();

                     b.putDouble("Longitude" ,longit);
                     b.putDouble("Latitude",latit);
                     home.putExtras(b);
                    startActivity(home);

                    finish();
                }
                else
                {
                    enableLocation();

                }
//

            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(CheckGpsStatus()) {
                    setLocation();

                }
                else
                {
                    enableLocation();

                }
            }
        });

    }
    public boolean CheckGpsStatus(){
        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(GpsStatus == true) {
            return true;
        } else {

        return false;
        }
    }
    public void setLocation()
    {

            client = LocationServices.getFusedLocationProviderClient(this);

            client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location!=null)
                    {
                        textview = (TextView)findViewById(R.id.texts);
                        textview.setText("Latitude: "+location.getLatitude()+"\n"+"Longitude: "+location.getLongitude()+".");

                        double lngt= location.getLongitude();
                        longit= location.getLongitude();
                        latit= location.getLatitude();

                        Toast.makeText(getApplicationContext(),"Longitude"+lngt,Toast.LENGTH_LONG).show();
                    }
                }
            });


    }
    public void enableLocation()
    {

        if(CheckGpsStatus()==false)
        {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enable Location");
            builder.setMessage("Your Location seems to be disabled, do you want to enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        }

    }


}
