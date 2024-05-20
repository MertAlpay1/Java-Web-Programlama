package com.mycompany.mavenwebproje;

public class TibbiRapor {
    private String tarih;
    private String icerik;
    private String url;
    private String raporJSON;
    private String hastaId;

    public TibbiRapor(String tarih, String icerik, String url,String hastaId, String raporJSON) {
        this.tarih = tarih;
        this.icerik = icerik;
        this.url = url;
        this.hastaId = hastaId;
        this.raporJSON = raporJSON;
    }
    public TibbiRapor(String tarih, String icerik, String url,String hastaId) {
        this.tarih = tarih;
        this.icerik = icerik;
        this.url = url;
        this.hastaId = hastaId;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getIcerik() {
        return icerik;
    }

    public void setIcerik(String icerik) {
        this.icerik = icerik;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRaporJSON() {
        return raporJSON;
    }

    public void setRaporJSON(String raporJSON) {
        this.raporJSON = raporJSON;
    }

    public String getHastaId() {
        return hastaId;
    }

    public void setHastaId(String hastaId) {
        this.hastaId = hastaId;
    }
    
}
