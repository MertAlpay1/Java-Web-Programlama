
package com.mycompany.mavenwebproje;

public class Yonetici {
    
   int yonetici_id;
   String sifre;

    public Yonetici(int yonetici_id, String sifre) {
        this.yonetici_id = yonetici_id;
        this.sifre = sifre;
    }

    public int getYonetici_id() {
        return yonetici_id;
    }

    public void setYonetici_id(int yonetici_id) {
        this.yonetici_id = yonetici_id;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }
   
    
}
