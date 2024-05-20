
package com.mycompany.mavenwebproje;

public class Randevu {
    
    int randevu_id;
    String tarih;
    String saat;
    int hasta_id;
    int doktor_id;    

    public Randevu(int randevu_id, String tarih, String saat, int hasta_id, int doktor_id) {
        this.randevu_id = randevu_id;
        this.tarih = tarih;
        this.saat = saat;
        this.hasta_id = hasta_id;
        this.doktor_id = doktor_id;
    }

    public int getRandevu_id() {
        return randevu_id;
    }

    public void setRandevu_id(int randevu_id) {
        this.randevu_id = randevu_id;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getSaat() {
        return saat;
    }

    public void setSaat(String saat) {
        this.saat = saat;
    }

    public int getHasta_id() {
        return hasta_id;
    }

    public void setHasta_id(int hasta_id) {
        this.hasta_id = hasta_id;
    }

    public int getDoktor_id() {
        return doktor_id;
    }

    public void setDoktor_id(int doktor_id) {
        this.doktor_id = doktor_id;
    }
    
    
}
