package com.calle.david.gpstracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GpsTracker extends AppCompatActivity {
    GpsTrackerService gps;
    GPSChange receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected  void onStart(){
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_gps_tracker);
        GpsTrackerService.mContext = this;
        IntentFilter filter = new IntentFilter(GpsTrackerService.GPS_LOCATION_CHANGE_FILTER);
        receiver = new GPSChange();
        registerReceiver(receiver, filter);
        startService(new Intent(getApplicationContext(), GpsTrackerService.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    public void getCurrentLocation(View v){
        sendBroadcast(new Intent(GpsTrackerService.GPS_GET_LOCATION_FILTER));
    }

    public void updateLocationGui(String lat , String lon){
        TextView txtLat = (TextView) findViewById(R.id.lat_text);
        TextView txtLon = (TextView) findViewById(R.id.lon_text);
        txtLat.setText(lat);
        txtLon.setText(lon);
    }

    class GPSChange extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent){
            String action = intent.getAction();
            String latitude = intent.getStringExtra("Lat");
            String longitude = intent.getStringExtra("Lon");
            updateLocationGui( latitude, longitude );
        }
    }
}

