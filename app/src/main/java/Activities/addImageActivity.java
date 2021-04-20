package Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import Database.DataBaseHelper;
import com.example.callapp.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class addImageActivity extends AppCompatActivity {
    private ImageView imgProfilResmi;
    private int imgIzinAlmaKodu=0, imgIzinVerildiKodu=1;
    private Bitmap secilenResim,kucultulenResim,defaultImage;
    private Button btnIleri;
    private String gelenMail;
    byte[] kayitEdilecekResim;

    public void init()
    {
        imgProfilResmi=(ImageView)findViewById(R.id.activity_add_image_ImageViewResimSec);
        btnIleri=(Button)findViewById(R.id.activity_add_image_ButtonIleri);
    }

    public void setDefaultImage(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.defaultimage);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        kucultulenResim=resimiKucult(bitmap);
        kucultulenResim.compress(Bitmap.CompressFormat.PNG, 75, stream);
        kayitEdilecekResim = stream.toByteArray();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);
        init();

        Intent intent=getIntent();
        gelenMail=intent.getStringExtra("email");
    }

    public void profilResmiSec(View view)
    {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        kucultulenResim=resimiKucult(secilenResim);
        kucultulenResim.compress(Bitmap.CompressFormat.PNG,75,outputStream);
        kayitEdilecekResim=outputStream.toByteArray();


            try{
                DataBaseHelper dataBaseHelper=new DataBaseHelper(this);
                dataBaseHelper.resimEkle(kayitEdilecekResim,gelenMail);
                /*defaultImage= BitmapFactory.decodeResource(this.getResources(),R.drawable.pickimage);
                  imgProfilResmi.setImageBitmap(defaultImage);*/

                Toast.makeText(getApplicationContext(),"Profil resmi Eklendi",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("kullaniciMail",gelenMail);
                startActivity(intent);

            }catch (Exception e)
            {
                e.printStackTrace();
            }



    }

    public void profildefaultSec(View view){

        setDefaultImage();

        try{
            DataBaseHelper dataBaseHelper=new DataBaseHelper(this);
            dataBaseHelper.resimEkle(kayitEdilecekResim,gelenMail);
                /*defaultImage= BitmapFactory.decodeResource(this.getResources(),R.drawable.pickimage);
                  imgProfilResmi.setImageBitmap(defaultImage);*/

            Toast.makeText(getApplicationContext(),"Default resmi Eklendi",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            intent.putExtra("kullaniciMail",gelenMail);
            startActivity(intent);

        }catch (Exception e)
        {
            e.printStackTrace();
        }

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

                    btnIleri.setEnabled(true);

                }catch (IOException e){
                    e.printStackTrace();
                }


            }
        }

        super.onActivityResult(requestCode,resultCode,data);
    }


    @Override
    public void onBackPressed() {
        //Kullanıcının kayıt ol sayfasına dönüşü engellenmiştir.
    }

}