package com.example.notdefteri;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Serializable {

    public FloatingActionButton gorevEkle, gorevSil;
    public Intent intent;
    public ArrayList<Gorev> gorevler = new ArrayList<Gorev>();
    public RecyclerView recyclerView;
    public CheckBox checkBox;
    public List<Integer> selectedids;
    public ArrayList<Gorev> currentSelectedItems = new ArrayList<>();
    public static final String TAG = "activity_main";
    public MyAdapter adapter;
    public int selectedidCount = 0;
    public List<Integer> mSelectedItemsIds;
    public Context mContext;
    public LocationManager locationManager;
    public boolean ret=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        //Log.d("onCreate", ": ----onCreate");
        //initRecyclerView();

        gorevEkle = findViewById(R.id.notEkle);
        gorevEkle.setOnClickListener(this);

        gorevSil = findViewById(R.id.notSil);
        gorevSil.hide();
        gorevSil.setOnClickListener(this);

        //recyclerView = findViewById(R.id.recyclerViewGorevler);
        //recyclerView.setOnClickListener(this);

        checkBox = findViewById(R.id.checkBox);
        //checkBox.setOnClickListener(this);

        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
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
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
        //        2000,
        //        1, locationListener);
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 0, locationListener);
        isLocationEnabled();
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // Guncel konumu paylaşan değerler
            double latitude=location.getLatitude();
            double longitude=location.getLongitude();
            Log.v(TAG, "IN ON LOCATION CHANGE, lat=" + latitude + ", lon=" + longitude);
            for (Gorev gorev: gorevler){
                if (gorev.getKonum() != null) {
                    Log.v("Location changedd: ", gorev.getOncelik());
                    Log.v("aaa:", String.valueOf(gorev.getKonum().latitude));
                    Log.v("bbb:", String.valueOf(latitude));
                    Log.v("aaa:", String.valueOf(gorev.getKonum().longitude));
                    Log.v("bbb:", String.valueOf(longitude));
                    Log.v(TAG, "IN ON LOCATION CHANGE, lat=" + gorev.getKonum().latitude + ", lon=" + gorev.getKonum().longitude);
                    Location start = new Location("start");
                    start.setLatitude((gorev.getKonum().latitude));
                    start.setLongitude((gorev.getKonum().longitude));
                    Location end = new Location("end");
                    end.setLatitude(latitude);
                    end.setLongitude(longitude);
                    double distance = start.distanceTo(end);
                    Log.v("distance: ", String.valueOf( distance ));
                    if ( distance <10.0 && ret==false){
                        ret = true;
                        Log.d("distance: : ", "geldin la");
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                        alertDialog.setTitle("Enable Location");
                        alertDialog.setMessage("geldin la");
                        alertDialog.show();
                    }
                }
            }
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

    protected void onResume(){
        super.onResume();
        isLocationEnabled();
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    private void isLocationEnabled() {

        if(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            AlertDialog.Builder alertDialog=new AlertDialog.Builder(mContext);
            alertDialog.setTitle("Enable Location");
            alertDialog.setMessage("Your locations setting is not enabled. Please enabled it in settings menu.");
            alertDialog.setPositiveButton("Location Settings", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    dialog.cancel();
                }
            });
            AlertDialog alert=alertDialog.create();
            alert.show();
        }
        else{
            AlertDialog.Builder alertDialog=new AlertDialog.Builder(mContext);
            //alertDialog.setTitle("Confirm Location");
            //alertDialog.setMessage("Your Location is enabled, please enjoy");
            alertDialog.setNegativeButton("Back to interface",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    dialog.cancel();
                }
            });
            AlertDialog alert=alertDialog.create();
            //alert.show();
        }
    }



    private void initImageBitmaps(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        initRecyclerView();
    }

    public void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recyclerViewGorevler);
        adapter = new MyAdapter(this, gorevler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        selectedids =  adapter.getSelectedIds();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.notEkle){
            Intent intent = new Intent(MainActivity.this, GorevEkle.class);
            intent.putExtra("mainToGorevEkleIntent", gorevler);

            //for (int i=0; i<gorevler.size(); i++)
            //    Log.d("33333: ", gorevler.get(i).getOncelik());
            startActivityForResult(intent, 100);

        }
        else if (v.getId() == R.id.checkBox){
            selectedids = adapter.getSelectedIds();
            Log.d("idss :", String.valueOf(selectedids.size()));
            for (int i=0; i<selectedids.size(); i++){
                Log.d("ids: ", String.valueOf(selectedids.get(i)));
            }

            //if (checkBox.isChecked() == true){
            //    gorevSil.hide();
            //}
            //else if (checkBox.isChecked() == false){
            //    gorevSil.show();
            //}
        }

        // checkBox'tan seçili görevi sile bastığında
        else if (v.getId() == R.id.notSil){
            Log.d("notSil", "e bastı");
            gorevSilFunc();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            if(requestCode==100) {
                //initRecyclerView();
                Log.d("RESULT_OK", "i");
                //Intent intent2 = getIntent();
                gorevler = (ArrayList<Gorev>) data.getSerializableExtra("gorevEkleToMainIntent");
                initRecyclerView();
            }
        }
        else if (resultCode==RESULT_CANCELED){
            Log.d("RESULT_CANCELED", "i");
            Intent intent2 = getIntent();
            initRecyclerView();
            gorevler = (ArrayList<Gorev>) data.getSerializableExtra("gorevEkleToMainIntent");
            for (int i=0; i<gorevler.size(); i++)
                Log.d("44444: ", gorevler.get(i).getOncelik());
        }
        else{
            Log.d("Geldi4", "i");
        }

    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBox:
                if (checked) {
                    selectedidCount += 1;
                }
                else{
                    selectedidCount -= 1;
                }
        }
        if (selectedidCount >0){
            gorevSil.show();
        }
        else if (selectedidCount <= 0){
            gorevSil.hide();
        }
    }

    private void gorevSilFunc(){
        Log.d("sayi: ", String.valueOf(adapter.getSelectedIdCount()));
        mSelectedItemsIds = adapter.getSelectedIds();
        for (int i=mSelectedItemsIds.size()-1; i>=0; i--){
            int var = mSelectedItemsIds.get(i);
            Log.d("var: ", String.valueOf(var));
            //Log.d("varGrev: ", String.valueOf(gorevler.get(var).oncelik));
            gorevler.remove(gorevler.get(var));
        }
        printGorevler();
        if (gorevler.size() == 0){
            gorevSil.hide();
        }

        //for (int i=adapter.getSelectedIdCount(); i>-1; i--){
        //    Log.d("sayi2: ", String.valueOf(gorevler.get(mSelectedItemsIds.get(i))));
        //    gorevler.remove( gorevler.get(mSelectedItemsIds.get(i)) );
        // }
        initRecyclerView();
    }

    private void printGorevler(){
        for (Gorev gorev: gorevler){
            Log.d("gorev: ", String.valueOf(gorev.getOncelik()));
        }
    }
}
