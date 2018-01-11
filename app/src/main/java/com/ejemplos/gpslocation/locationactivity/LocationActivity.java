package com.ejemplos.gpslocation.locationactivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LocationActivity extends AppCompatActivity {

    private static final String TAG = "gpslog";
    private LocationManager mLocMgr;
    private TextView textViewGPS, label;

    //Minimo tiempo para updates en Milisegundos
    private static final long MIN_CAMBIO_DISTANCIA_PARA_UPDATES = (long) 1; // 1 metro
    //Minimo tiempo para updates en Milisegundos
    private static final long MIN_TIEMPO_ENTRE_UPDATES = 1000; // 1 sg

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        textViewGPS = (TextView) findViewById(R.id.lat);

        mLocMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Requiere permisos para Android 6.0
            Log.e(TAG, "No se tienen permisos necesarios!, se requieren.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 225);
            return;
        }else{
            Log.i(TAG, "Permisos necesarios OK!.");
            mLocMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIEMPO_ENTRE_UPDATES, MIN_CAMBIO_DISTANCIA_PARA_UPDATES, locListener, Looper.getMainLooper());
        }
        textViewGPS.setText("Lat " +    " Long " );

    }

    public LocationListener locListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            Log.i(TAG, "Lat " + location.getLatitude() + " Long " + location.getLongitude());
            textViewGPS.setText("Lat " +   location.getLatitude() + " Long " + location.getLongitude());
        }

        public void onProviderDisabled(String provider) {
            Log.i(TAG, "onProviderDisabled()");
        }

        public void onProviderEnabled(String provider) {
            Log.i(TAG, "onProviderEnabled()");
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.i(TAG, "onStatusChanged()");
        }
    };
}
