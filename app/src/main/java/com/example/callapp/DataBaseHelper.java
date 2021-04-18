package com.example.callapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {


    public static final String UYELER_TABLE = "UYELER_TABLE";

    public static final String COLUMN_AD = "AD";
    public static final String COLUMN_TELNO = "TELNO";
    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_SIFRE = "SIFRE";
    public static final String COLUMN_RESIM = "RESIM";
    public static final String COLUMN_ID = "ID";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "callapp.db", null, 1);
    }

    //Veritabanının ilk çağrıldığında oluşturan kod. Veritabanı oluşturma kodu burada bulunur.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement= "CREATE TABLE " + UYELER_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_AD + " VARCHAR, " + COLUMN_TELNO + " VARCHAR, " + COLUMN_EMAIL + " VARCHAR, " + COLUMN_SIFRE + " VARCHAR, " + COLUMN_RESIM + " BLOB )";

        db.execSQL(createTableStatement);
    }

    //Veritabanının versiyon numarasını değiştirir.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(YeniUyeDetay yeniUyeDetay){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        yeniUyeDetay.setSifre(md5(yeniUyeDetay.getSifre()));

        cv.put(COLUMN_AD,yeniUyeDetay.getAd());
        cv.put(COLUMN_TELNO,yeniUyeDetay.getTelNo());
        cv.put(COLUMN_EMAIL,yeniUyeDetay.geteMail());
        cv.put(COLUMN_SIFRE,yeniUyeDetay.getSifre());
        cv.put(COLUMN_RESIM,yeniUyeDetay.getResim());

        long insert = db.insert(UYELER_TABLE, null, cv);
        if (insert==-1)
        {
            return  false;
        } else
        {
            return true;
        }

    }

    public boolean deleteOne(YeniUyeDetay yeniUyeDetay){

        SQLiteDatabase db=this.getWritableDatabase();
        String queryString="DELETE FROM "+ UYELER_TABLE + " WHERE " + COLUMN_ID + " = " + yeniUyeDetay.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }

    }


    public List<YeniUyeDetay> getEveryone(){
        List<YeniUyeDetay> returnList=new ArrayList<>();
        //Veritabanında bilgiler alınıyor.

        String queryString="SELECT * FROM "+UYELER_TABLE;

        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString,null);

        if(cursor.moveToNext()){

            do{
                int uye_id=cursor.getInt(0);
                String uye_ad=cursor.getString(1);
                String uye_tel=cursor.getString(2);
                String uye_mail= cursor.getString(3);
                String uye_sifre=cursor.getString(4);
                byte[] uye_resim=cursor.getBlob(5);

                YeniUyeDetay yeniUyeDetay=new YeniUyeDetay(uye_id,uye_ad,uye_tel,uye_mail,uye_sifre,uye_resim);
                returnList.add(yeniUyeDetay);

            }while (cursor.moveToFirst());

        }else {
            //Hata. Listeye bir şey eklenmez.
        }

        cursor.close();
        db.close();
        return returnList;
    }

    public boolean checkMailPass(String email,String pass){
        SQLiteDatabase database=this.getReadableDatabase();
        pass=md5(pass);
        Cursor cursor=database.rawQuery("SELECT * FROM "+ UYELER_TABLE + " WHERE "+ COLUMN_EMAIL +" = ? and "+ COLUMN_SIFRE+" = ?", new String[] {email,pass});
        int sayCursor=cursor.getCount();
        database.close();
        cursor.close();
        if(sayCursor>0){
            return true;
        }else
        {
            return false;
        }

    }


    public boolean checkMail(String email){

        String[] columns={COLUMN_ID};
        SQLiteDatabase database=this.getReadableDatabase();
        String selection=COLUMN_EMAIL + " = ?";
        String[] selectionArgs= {email};

        Cursor cursor = database.query(UYELER_TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        int cursorCount=cursor.getCount();
        cursor.close();
        database.close();

        if(cursorCount > 0){
            return true;
        }

        return false;
    }

    public void resimEkle(byte[] resim,String email){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_RESIM,resim);

        database.update(UYELER_TABLE, values, COLUMN_EMAIL + " = ?",
                new String[] {email});
        database.close();
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
