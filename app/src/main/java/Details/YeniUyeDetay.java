
/*  DENİZHAN SARAÇ
 *   dnzhn.src@outlook.com
 *   Computer Engineer at BİLECİK ŞEYH EDEBALİ UNIVERSITY
 *   CALL APP FOR THEASIS
 *   ALL RIGHTS RESERVED
 *   11.04.2021 17:02
 *   GITHUB:  https://github.com/DenizhanSarac/CallApp*/

package Details;
import android.graphics.Bitmap;
//Veritabanına veri ekleme ve veri çekmek için kullanılan class burasıdır.
public class YeniUyeDetay {
    private int id;
    private String Ad;
    private String telNo;
    private String eMail;
    private String Sifre;
    private String Dogum;
    private String Meslek;
    private byte[] Resim;

    public YeniUyeDetay(int id,String ad, String telNo, String eMail, String sifre,byte[] Resim,String Dogum,String Meslek) {
        this.id=id;
        Ad = ad;
        this.telNo = telNo;
        this.eMail = eMail;
        Sifre = sifre;
        this.Resim=Resim;
        this.Dogum=Dogum;
        this.Meslek=Meslek;
    }
    //Getter ve Setter bölümü.
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
    public String getDogum() {
        return Dogum;
    }

    public void setDogum(String dogum) {
        Dogum = dogum;
    }
    public String getMeslek() {
        return Meslek;
    }

    public void setMeslek(String meslek) {
        Meslek = meslek;
    }
}

