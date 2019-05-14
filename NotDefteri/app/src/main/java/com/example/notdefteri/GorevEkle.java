package com.example.notdefteri;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class GorevEkle extends AppCompatActivity implements View.OnClickListener, Serializable {
    EditText baslik, tarih;
    Spinner oncelik;
    Button onayla, tarihSec, konum;
    ArrayList<Gorev> gorevler;
    Intent GorevEkleToMainIntent;
    TimePicker picker;
    Gorev gorev;
    int saat, dakika, ay, yil, gun;
    static Location lastLocation;
    public float[] koor;
    public float lat, lng;
    public LatLng konumLatLng = new LatLng(41.015137, 28.979530);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gorev_ekle);

        gorevler = new ArrayList<Gorev>();

        baslik = (EditText) findViewById(R.id.editTextBaslik);
        tarih = (EditText) findViewById(R.id.editTextTarih);
        oncelik = (Spinner) findViewById(R.id.spinnerOncelik);
        onayla = (Button) findViewById(R.id.buttonGorevEkleOnayla);
        tarihSec = (Button) findViewById(R.id.buttonSaatSec);
        konum = (Button) findViewById(R.id.buttonKonum);


        String[] oncelikIcerik = new String[]{"Yüksek", "Orta", "Düşük"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, oncelikIcerik);
        oncelik.setAdapter(adapter);

        //this.showTimePickerDialog();


        onayla.setOnClickListener(this);
        tarihSec.setOnClickListener(this);
        konum.setOnClickListener(this);
    }

    private void showTimePickerDialog() {
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.buttonGorevEkleOnayla){
            Intent mainToGorevEkleIntent = getIntent();
            gorevler = (ArrayList<Gorev>) mainToGorevEkleIntent.getSerializableExtra("mainToGorevEkleIntent");
            //Log.d("00000: ", gorevler.get(0).getOncelik());
            gorev = new Gorev(baslik.getText().toString(), oncelik.getSelectedItem().toString());
            gorev.setSaat(saat);
            gorev.setDakika(dakika);
            gorev.setLat((double) konumLatLng.latitude);
            gorev.setLng((double) konumLatLng.longitude);
            gorev.makeKonum();
            //gorev.setKonum(konumLatLng);
            gorevler.add(gorev);
            Log.d("uzunluk: ", String.valueOf(gorevler.size()));
            for (Gorev gorevs: gorevler){
                Log.d("uzunluk: ", String.valueOf(gorevs.getKonum().latitude)+String.valueOf(gorevs.getKonum().longitude) );
            }
            //Log.d("1.51.51.5: ", String.valueOf(gorevler.get(0).getKonum().latitude+ gorevler.get(0).getKonum().longitude));


            Intent gorevEkleToMainIntent = new Intent(GorevEkle.this, MainActivity.class);
            gorevEkleToMainIntent.putExtra("gorevEkleToMainIntent",  gorevler);


            Log.d("gorev Get Konum: ", String.valueOf(gorevler.get(0).getKonum().latitude)+ String.valueOf(gorevler.get(0).getKonum().longitude) );
            setResult(Activity.RESULT_OK, gorevEkleToMainIntent);
            finish();
            //startActivity(gorevEkleToMainIntent);
        }

        else if (v.getId()==R.id.buttonSaatSec){
            // Create a new OnTimeSetListener instance. This listener will be invoked when user click ok button in TimePickerDialog.
            TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    saat = hour;
                    dakika = minute;
                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("You select time is ");
                    strBuf.append(hour);
                    strBuf.append(":");
                    strBuf.append(minute);

                }
            };

            Calendar now = Calendar.getInstance();
            int hour = now.get(java.util.Calendar.HOUR_OF_DAY);
            int minute = now.get(java.util.Calendar.MINUTE);

            // Whether show time in 24 hour format or not.
            boolean is24Hour = true;

            TimePickerDialog timePickerDialog = new TimePickerDialog(GorevEkle.this, onTimeSetListener, hour, minute, is24Hour);

            timePickerDialog.show();
        }

        else if (v.getId()==R.id.buttonKonum){
            Intent intent = new Intent(GorevEkle.this, harita.class);
            startActivityForResult(intent, 101);



        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            if(requestCode==101) {
                //initRecyclerView();
                Log.d("RESULT_OK", "i");
                //Intent intent2 = getIntent();
                koor = data.getFloatArrayExtra("koor");
                lat = koor[0];
                lng = koor[1];
                konumLatLng = new LatLng(lat, lng);
            }
            Log.d("Lat: ", String.valueOf(konumLatLng.latitude));
            Log.d("Lngi: ", String.valueOf(konumLatLng.longitude));
        }

    }
}