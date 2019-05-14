package com.example.notdefteri;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class harita extends AppCompatActivity implements View.OnClickListener, GoogleMap.OnMarkerClickListener, OnMapReadyCallback, Serializable {

    Button haritadanKonumSec;
    private Marker markeri;
    private GoogleMap mMap;
    MapView mapView;
    private LatLng koor;
    String lat, lng;
    private float[] koorList;
    LocationManager mLocationManager;
    double latitude;
    double longitude;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harita);

        koorList = new float[2];
        haritadanKonumSec = (Button) findViewById(R.id.buttonHaritadanKonumSec);
        //mapView = (MapView) findViewById(R.id.mapView);

        haritadanKonumSec.setOnClickListener(this);
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
                0, locationListener);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapHarita);
        mapFragment.getMapAsync(this);

        //mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
        //    @Override
        //    public boolean onMarkerClick(Marker marker) {
        //        markeri = marker;
        //        return false;
        //    }
        //});


        //mapView.setOnClickListener(this);
        //mMap.setOnMarkerClickListener(this);
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8));
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng));

            if (latLng.latitude != 0.0) {
                mMap.addMarker(new MarkerOptions().position(latLng).title("Istanbul"));
                Log.v("guncelledi iste", "asdf");
                Log.v("lattt: ", String.valueOf(latitude + longitude));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8));
                koor = latLng;
            }
            //AlertDialog.Builder alertDialog=new AlertDialog.Builder(mContext);
            //alertDialog.setTitle("Yer degistirdin");
            //alertDialog.setMessage("Gonderme var");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonHaritadanKonumSec) {
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    markeri = marker;
                    return false;
                };
            });

            Intent intent = new Intent(harita.this, GorevEkle.class);
            koorList[0] = (float) koor.latitude;
            koorList[1] = (float) koor.longitude;
            intent.putExtra("koor", koorList);
            Log.d("lngh: ", String.valueOf(koorList[0]+ koorList[1]));
            mLocationManager.removeUpdates(locationListener);
            setResult(Activity.RESULT_OK, intent);
            finish();
            //startActivity(intent);
        }

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        //LatLng ist = new LatLng(41.015137, 28.979530);
        LatLng ist = new LatLng(latitude, longitude);
        Log.v("guncelledi iste", String.valueOf(ist.latitude));
        if (ist.latitude != 0.0) {
            mMap.addMarker(new MarkerOptions().position(ist).title("Istanbul"));
            Log.v("guncelledi iste", "asdf");
            Log.v("lattt: ", String.valueOf(latitude + longitude));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ist, 8));
            koor = ist;
        }


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng));
                koor = latLng;
            }
        });
    }
}




