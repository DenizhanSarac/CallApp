
/*  DENİZHAN SARAÇ
 *   dnzhn.src@outlook.com
 *   Computer Engineer at BİLECİK ŞEYH EDEBALİ UNIVERSITY
 *   CALL APP FOR THEASIS
 *   ALL RIGHTS RESERVED
 *   11.04.2021 17:02
 *   GITHUB:  https://github.com/DenizhanSarac/CallApp*/

package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import Database.DataBaseHelper;
import com.example.callapp.R;
import Details.YeniUyeDetay;

public class RegisterActivity extends AppCompatActivity {

    //Üye ol değişkenleri
    private EditText edtAd,edtTel,edteMail,edtSifre;
    private String yeniuye_Ad,yeniuye_telNo,yeniuye_Email,yeniuye_Sifre;
    DataBaseHelper dataBaseHelper;
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
        try{

        yeniuye_Ad=edtAd.getText().toString();
        yeniuye_telNo=edtTel.getText().toString();
            yeniuye_telNo=yeniuye_telNo.trim();
        yeniuye_Email=edteMail.getText().toString();
            yeniuye_Email=yeniuye_Email.trim();
        yeniuye_Sifre=edtSifre.getText().toString();
            yeniuye_Sifre=yeniuye_Sifre.trim();

        if(!TextUtils.isEmpty(yeniuye_Ad)){
            if (!TextUtils.isEmpty(yeniuye_telNo)){

                if(!TextUtils.isEmpty(yeniuye_Email)){

                    if (!TextUtils.isEmpty(yeniuye_Sifre)&&yeniuye_Sifre.length()>=8){
                        dataBaseHelper=new DataBaseHelper(this);

                        boolean success=true;
                        YeniUyeDetay yeniUyeDetay;

                        boolean checkMail = dataBaseHelper.checkMail(yeniuye_Email);

                        if(checkMail == false)
                        {
                            try{
                                yeniUyeDetay=new YeniUyeDetay(1,yeniuye_Ad,yeniuye_telNo,yeniuye_Email,yeniuye_Sifre,null,null,null);
                                success = dataBaseHelper.addOne(yeniUyeDetay);

                                Intent intent=new Intent(getApplicationContext(), addImageActivity.class);
                                intent.putExtra("email",yeniuye_Email);
                                startActivity(intent);
                            }catch (Exception e)
                            {

                                e.printStackTrace();
                            }
                        }else{
                            showToast("Bu mail adresi mevcut.");
                        }


                        if(success == true){
                            showToast("İşlem Başarılı.");
                        }else
                        {
                            showToast("İşlem Başarısız.");
                        }


                    }else
                        showToast("Şifre bölümü boş bırakılamaz. Şifre 8 karakterden az olamaz.");
                }else
                    showToast("Mail bölümü boş bırakılamaz.");
            }else
                showToast("Telefon bölümü boş bırakılamaz.");

        }else
            showToast("Ad bölümü boş bırakılamaz.");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //Register ve Login layoutları arasında geçişi sağlayan kodlama bölümü.
    public void viewLoginPage(View view)
    {
        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
    }

}