package com.example.callapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    //Üye ol değişkenleri
    private EditText edtAd,edtTel,edteMail,edtSifre;
    private String yeniuye_Ad,yeniuye_telNo,yeniuye_Email,yeniuye_Sifre;
    private boolean uyeMi=false;
    private ImageView imgCikis;

    //Bağlantı noktaları.
    private void init(){
        edtAd=(EditText)findViewById(R.id.layout_register_EditTxtIsim);
        edtTel=(EditText)findViewById(R.id.layout_register_EditTxtTelNo);
        edteMail=(EditText)findViewById(R.id.layout_register_EditTxtEmail);
        edtSifre=(EditText)findViewById(R.id.layout_register_EditTxtSifre);
        imgCikis=(ImageView)findViewById(R.id.activity_login_CloseApp);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }



    //Register Activity'de sol üstte bulunan çarpı(Çıkış) simgesi için çıkış kodlaması.
    public void imgCikisIcon(View view)
    {
        finish();
        System.exit(0);
    }
    //Toast kodunu her yerde yazmak yerine buraya kısayol oluşturuldu.
    private void showToast(String Mesaj)
    {
        Toast.makeText(getApplicationContext(),Mesaj,Toast.LENGTH_SHORT).show();
    }

    //Layoutta bulunan EditTextleri temizleyen kod.
    private void nesneleriTemizle()
    {
        edtAd.setText("");
        edtTel.setText("");
        edteMail.setText("");
        edtSifre.setText("");
    }

    //Kayıt ol butonuna basıldığında çalışmayan başlayan kayıt edici kod bölümüdür.
    public void btnKayitOl(View view)
    {
        yeniuye_Ad=edtAd.getText().toString();
        yeniuye_telNo=edtTel.getText().toString();
        yeniuye_Email=edteMail.getText().toString();
        yeniuye_Sifre=edtSifre.getText().toString();

        if(!TextUtils.isEmpty(yeniuye_Ad)){
            if (!TextUtils.isEmpty(yeniuye_telNo)){

                if(!TextUtils.isEmpty(yeniuye_Email)){

                    if (!TextUtils.isEmpty(yeniuye_Sifre)&&yeniuye_Sifre.length()>=8){

                       /* try{
                            SQLiteDatabase database=this.openOrCreateDatabase("CallApp",MODE_PRIVATE,null);
                            database.execSQL("CREATE TABLE IF NOT EXISTS uyeler(id INTEGER PRIMARY KEY,uyeAdi VARCHAR,uyeTel VARCHAR,uyeMail VARCHAR,uyeSifre VARCHAR,uyeResim BLOB)");

                            String sqlQuery="INSERT INTO uyeler(uyeAdi,uyeTel,uyeMail,uyeSifre) VALUES(?,?,?,?)";
                            SQLiteStatement statement=database.compileStatement(sqlQuery);
                            statement.bindString(1,yeniuye_Ad);
                            statement.bindString(2,yeniuye_telNo);
                            statement.bindString(3,yeniuye_Email);
                            statement.bindString(4,yeniuye_Sifre);
                            statement.execute();

                            nesneleriTemizle();
                            showToast("Kayıt başarıyla tamamlandı.");
                            Intent intent=new Intent(getApplicationContext(),addImageActivity.class);
                            intent.putExtra("uyeMail",yeniuye_Email);
                            startActivity(intent);

                        }catch (Exception e){
                            e.printStackTrace();
                        }*/


                    }else
                        showToast("Şifre bölümü boş bırakılamaz. Şifre 8 karakterden az olamaz.");
                }else
                    showToast("Mail bölümü boş bırakılamaz.");
            }else
                showToast("Telefon bölümü boş bırakılamaz.");

        }else
            showToast("Ad bölümü boş bırakılamaz.");

    }

    //Register ve Login layoutları arasında geçişi sağlayan kodlama bölümü.
    public void viewLoginPage(View view)
    {
        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
    }

}