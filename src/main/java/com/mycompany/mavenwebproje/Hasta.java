package com.mycompany.mavenwebproje;

import java.util.List;

public class Hasta {
    
 int hasta_id;
 String ad;
 String soyad;
 String sifre;
 String cinsiyet;
 List<String> adres;
 List<String> telefon;

    public Hasta(int hasta_id, String ad, String soyad, String sifre, String cinsiyet, List<String> adres, List<String> telefon) {
        this.hasta_id = hasta_id;
        this.ad = ad;
        this.soyad = soyad;
        this.sifre = sifre;
        this.cinsiyet = cinsiyet;
        this.adres = adres;
        this.telefon = telefon;
    }

    public Hasta(int hasta_id, String ad, String soyad, String sifre, String cinsiyet) {
        this.hasta_id = hasta_id;
        this.ad = ad;
        this.soyad = soyad;
        this.sifre = sifre;
        this.cinsiyet = cinsiyet;
    }

    
    public int getHasta_id() {
        return hasta_id;
    }

    public void setHasta_id(int hasta_id) {
        this.hasta_id = hasta_id;
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

    public String getCinsiyet() {
        return cinsiyet;
    }

    public void setCinsiyet(String cinsiyet) {
        this.cinsiyet = cinsiyet;
    }

    public List<String> getAdres() {
        return adres;
    }

    public void setAdres(List<String> adres) {
        this.adres = adres;
    }

    public List<String> getTelefon() {
        return telefon;
    }

    public void setTelefon(List<String> telefon) {
        this.telefon = telefon;
    }
    
    
    
}
