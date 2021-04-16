package com.example.callapp;

import android.graphics.Bitmap;

public class YeniUyeDetay {
    private String Ad,telNo,eMail,Sifre;

    public YeniUyeDetay(String ad, String telNo, String eMail, String sifre) {
        Ad = ad;
        this.telNo = telNo;
        this.eMail = eMail;
        Sifre = sifre;
    }

    public String getAd() {
        return Ad;
    }

    public void setAd(String ad) {
        Ad = ad;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getSifre() {
        return Sifre;
    }

    public void setSifre(String sifre) {
        Sifre = sifre;
    }
}

