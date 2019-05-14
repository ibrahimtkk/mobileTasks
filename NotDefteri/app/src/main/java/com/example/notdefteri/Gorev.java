package com.example.notdefteri;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.security.Provider;

public class Gorev implements Serializable {
    public String baslik;
    public String tanim;
    public String oncelik;
    public int saat;
    public int dakika;
    public String yil;
    public String ay;
    public String saatVeDakika;
    public static LatLng konum;
    public double lat, lng;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void makeKonum(){
        konum = new LatLng(lat, lng);
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Gorev(String baslik, String oncelik) {
        this.baslik = baslik;
        this.oncelik = oncelik;
    }

    public LatLng getKonum() {
        return konum;
    }

    public void setKonum(LatLng konum) {
        this.konum = konum;
    }

    public String getSaatVeDakika() {
        return saatVeDakika;
    }

    public void setSaatVeDakika(String saatVeDakika) {
        this.saatVeDakika = saatVeDakika;
    }

    public int getSaat() {
        return saat;
    }

    public void setSaat(int saat) {
        this.saat = saat;
    }

    public int getDakika() {
        return dakika;
    }

    public void setDakika(int dakika) {
        this.dakika = dakika;
    }

    public String getYil() {
        return yil;
    }

    public void setYil(String yil) {
        this.yil = yil;
    }

    public String getAy() {
        return ay;
    }

    public void setAy(String ay) {
        this.ay = ay;
    }

    public String getGun() {
        return gun;
    }

    public void setGun(String gun) {
        this.gun = gun;
    }

    String gun;

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public String getTanim() {
        return tanim;
    }

    public void setTanim(String tanim) {
        this.tanim = tanim;
    }

    public String getOncelik() {
        return oncelik;
    }

    public void setOncelik(String oncelik) {
        this.oncelik = oncelik;
    }


}
