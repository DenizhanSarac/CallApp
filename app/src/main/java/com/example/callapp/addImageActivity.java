package com.example.callapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.spec.ECField;

public class addImageActivity extends AppCompatActivity {
    private ImageView imgProfilResmi;
    private int imgIzinAlmaKodu=0, imgIzinVerildiKodu=1;
    private Bitmap secilenResim,kucultulenResim,defaultImage;
    private String gelenMail;

    public void init()
    {
        imgProfilResmi=(ImageView)findViewById(R.id.activity_add_image_ImageViewResimSec);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);
        init();

        Intent intent=getIntent();
        gelenMail=intent.getStringExtra("uyeMail");
    }

    public void profilResmiSec(View view)
    {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        kucultulenResim=resimiKucult(secilenResim);
        kucultulenResim.compress(Bitmap.CompressFormat.PNG,75,outputStream);
        byte[] kayitEdilecekResim=outputStream.toByteArray();

       /* try{
            SQLiteDatabase database=this.openOrCreateDatabase("CallApp",MODE_PRIVATE,null);

            String sqlQuery="UPDATE uyeler SET uyeResim=? where uyeMail= ?";
            SQLiteStatement statement=database.compileStatement(sqlQuery);
            statement.bindBlob(1,kayitEdilecekResim);
            statement.bindString(2,gelenMail);
            statement.execute();

            defaultImage= BitmapFactory.decodeResource(this.getResources(),R.drawable.pickimage);
            imgProfilResmi.setImageBitmap(defaultImage);
            Toast.makeText(getApplicationContext(),"Profil resmi Eklendi",Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            e.printStackTrace();
        }*/
    }




    private Bitmap resimiKucult(Bitmap resim){
        return Bitmap.createScaledBitmap(resim,120,150,true); // REsim Boyutu Ayarlama
    }

    public void resimSec(View view)
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},imgIzinAlmaKodu);
        }
        else
        {
            Intent resimiAl=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(resimiAl,imgIzinVerildiKodu);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==imgIzinVerildiKodu){
            if(resultCode==RESULT_OK && data !=null){
                Uri resimUri=data.getData();

                try{
                    if(Build.VERSION.SDK_INT>=28)
                    {
                        ImageDecoder.Source resimSource=ImageDecoder.createSource(this.getContentResolver(),resimUri);
                        secilenResim=ImageDecoder.decodeBitmap(resimSource);
                        imgProfilResmi.setImageBitmap(secilenResim);
                    }
                    else{
                        secilenResim=MediaStore.Images.Media.getBitmap(this.getContentResolver(),resimUri);
                        imgProfilResmi.setImageBitmap(secilenResim);
                    }

                }catch (IOException e){
                    e.printStackTrace();
                }


            }
        }

        super.onActivityResult(requestCode,resultCode,data);
    }


    @Override
    public void onBackPressed() {
        Intent geriIntent=new Intent(this,MainActivity.class);
        finish();
        startActivity(geriIntent);
    }

}