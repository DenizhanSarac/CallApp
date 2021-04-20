package Details;

import android.graphics.Bitmap;

public class YeniUyeDetay {
    private int id;
    private String Ad,telNo,eMail,Sifre;
    private byte[] Resim;

    public YeniUyeDetay(int id,String ad, String telNo, String eMail, String sifre,byte[] Resim) {
        this.id=id;
        Ad = ad;
        this.telNo = telNo;
        this.eMail = eMail;
        Sifre = sifre;
        this.Resim=Resim;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getResim() {
        return Resim;
    }

    public void setResim(byte[] resim) {
        Resim = resim;
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

