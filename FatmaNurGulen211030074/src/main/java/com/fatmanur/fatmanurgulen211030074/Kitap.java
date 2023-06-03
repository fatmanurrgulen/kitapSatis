package com.fatmanur.fatmanurgulen211030074;

import java.io.Serializable;
import java.time.LocalDate;

/*
    Kitap sınıfı
    Disk dosya iişlemleri olacağından
    Serializable arayüzünün kalıtlandı.
 */
public class Kitap implements Serializable {
    private String Ad;
    private String ISBN;
    private String Yazar;
    private String Dil;
    private String Tur;
    private LocalDate Yil;
    private String Ev;
    private Integer BasimYil;

    public String getAd() {
        return Ad;
    }

    public void setAd(String ad) {
        Ad = ad;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getYazar() {
        return Yazar;
    }

    public void setYazar(String yazar) {
        Yazar = yazar;
    }

    public String getDil() {
        return Dil;
    }

    public void setDil(String dil) {
        Dil = dil;
    }

    public String getTur() {
        return Tur;
    }

    public void setTur(String tur) {
        Tur = tur;
    }

    public LocalDate getYil() {
        return Yil;
    }

    public void setYil(LocalDate yil) {
        Yil = yil;
    }

    public String getEv() {
        return Ev;
    }

    public void setEv(String ev) {
        Ev = ev;
    }


    public Integer getBasimYil() {
        return BasimYil;
    }

    public void setBasimYil(Integer basimYil) {
        BasimYil = basimYil;
    }

    public Kitap(String ad, String ISBN, String yazar, String dil, String tur, LocalDate yil, String ev, Integer basimYil) {
        super();
        Ad = ad;
        this.ISBN = ISBN;
        Yazar = yazar;
        Dil = dil;
        Tur = tur;
        Yil = yil;
        Ev = ev;
        BasimYil = basimYil;
    }
}
