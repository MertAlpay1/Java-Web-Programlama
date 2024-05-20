package com.mycompany.mavenwebproje;

public class Doktor {
    
    int doktor_id;
    String ad;
    String soyad;
    String sifre;
    String uzmanlık_alan;
    String calıstıgı_hastane;

    public Doktor(int doktor_id, String ad, String soyad, String sifre, String uzmanlık_alan, String calıstıgı_hastane) {
        this.doktor_id = doktor_id;
        this.ad = ad;
        this.soyad = soyad;
        this.sifre = sifre;
        this.uzmanlık_alan = uzmanlık_alan;
        this.calıstıgı_hastane = calıstıgı_hastane;
    }

    public int getDoktor_id() {
        return doktor_id;
    }

    public void setDoktor_id(int doktor_id) {
        this.doktor_id = doktor_id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getUzmanlık_alan() {
        return uzmanlık_alan;
    }

    public void setUzmanlık_alan(String uzmanlık_alan) {
        this.uzmanlık_alan = uzmanlık_alan;
    }

    public String getCalıstıgı_hastane() {
        return calıstıgı_hastane;
    }

    public void setCalıstıgı_hastane(String calıstıgı_hastane) {
        this.calıstıgı_hastane = calıstıgı_hastane;
    }
    
    
    
}
