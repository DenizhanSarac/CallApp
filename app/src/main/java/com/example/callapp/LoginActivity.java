/*  DENİZHAN SARAÇ
*   dnzhn.src@outlook.com
*   Computer Engineer at BİLECİK ŞEYH EDEBALİ UNIVERSITY
*   CALL APP FOR THEASIS
*   ALL RIGHTS RESERVED
*   11.04.2021 17:02
*   GITHUB:  https://github.com/DenizhanSarac/CallApp*/

package com.example.callapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {



    //Giriş yap değişkenleri
    private EditText etxtEmail,etxtPassword,etxtsifreSifirlaMail;
    private CheckBox beniHatirla;
    private String eMail,Pass,getEmail;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    //Şifre Sıfırlama değişkenleri
    private Button  sifreSifirlaBtn;
    private boolean check;
    private Dialog resetPassDialog;

    //Çıkış Butonu değişkeni
    private ImageView imgCikis;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //Beni hatırla bölümü çalışması için mail-şifre-checkbox burada bağlandı.
        etxtEmail=(EditText)findViewById(R.id.layout_login_editTextEmail);
        etxtPassword = (EditText) findViewById(R.id.layout_login_editTextPassword);
        beniHatirla=(CheckBox)findViewById(R.id.layout_login_ChckRememberMe);

        //Çıkış simgesi burada bağlandı.
        imgCikis=(ImageView)findViewById(R.id.activity_login_CloseApp);

        //Beni hatırla için kaydedilen bilgiler bellekten çekiliyor.
        preferences=this.getSharedPreferences("com.example.callapp", Context.MODE_PRIVATE);
        getEmail=preferences.getString("email",null);
        check=preferences.getBoolean("checkbox",false);
        if(check && !TextUtils.isEmpty(getEmail))
        {
            etxtEmail.setText(getEmail);
            beniHatirla.setChecked(check);
        }
    }

    //Giriş yap butonu kodlaması.
    public void btnGirisYap(View v) {

                //TRY-CATCH ile hatalar yakalanmaya çalışıyor.
                try {
                    eMail = etxtEmail.getText().toString();
                    Pass = etxtPassword.getText().toString();
                    //Email ve şifre bölümleri boş bırakılamaz olayı koşullanıyor.
                    if (!TextUtils.isEmpty(eMail) && !TextUtils.isEmpty(Pass)) {

                        //SQL KOMUTLARI EKLENECEK YER

                        if (beniHatirla.isChecked()) {
                            //Dahili belleğe bilgiler kaydediliyor.
                            editor = preferences.edit();
                            editor.putString("email", eMail);
                            editor.putBoolean("checkbox", true);
                            editor.apply();
                        } else {
                            //Dahili belleğe checkbox seçili olmadığı için veri boş giriliyor.
                            editor = preferences.edit();
                            editor.putString("email", null);
                            editor.putBoolean("checkbox", false);
                            editor.apply();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Email veya Şifre Boş olamaz", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

    }



    //Login Activity'de sol üstte bulunan çarpı(Çıkış) simgesi için çıkış kodlaması.
    public void imgCikisIcon(View view)
    {
        finish();
        System.exit(0);
    }


    //Şifremi unuttum bölümü için ekrana şifremi unuttum bölümü ekrana getiriliyor.
    public void viewForgotPassword(View view)
    {
        showResetPassDialog();
    }


    //Ekrana Custom Dialog getirmek için kullanılan ve sifre sıfırlayan programcık.
    public void showResetPassDialog()
    {
        //Dialog oluşturuluyor.
        resetPassDialog=new Dialog(this);
        WindowManager.LayoutParams params=new WindowManager.LayoutParams();
        params.copyFrom(resetPassDialog.getWindow().getAttributes());
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height=WindowManager.LayoutParams.WRAP_CONTENT;
        resetPassDialog.setCancelable(false);
        resetPassDialog.setContentView(R.layout.layout_sifre_yenile);
        //Dialog oluşturuldu.

        //Veriler çekiliyor.
        ImageView imgSifreCikis=(ImageView)resetPassDialog.findViewById(R.id.sifre_sifirla_SifreDialogKapat);
        sifreSifirlaBtn=(Button)resetPassDialog.findViewById(R.id.layout_sifre_sifirla_ButtonSifreSifirla);
        etxtsifreSifirlaMail=(EditText)resetPassDialog.findViewById(R.id.layout_sifre_sifirla_EditTxtMail);
        //Veriler Çekildi.

        //Dialogda bulunan kapatma ImageView yakalanma olayı.
        imgSifreCikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassDialog.dismiss();
            }
        });


        //Sifre Gönderme olayı başlıyor.
        sifreSifirlaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(etxtsifreSifirlaMail.getText().toString()))
                {
                    /* Sifre Sıfırlama Kodları buraya Eklenecek. */
                    Toast.makeText(getApplicationContext(),"Şifre Sıfırlama Gönderildi",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Şifre gönderme olayı bitiyor.

        //Dialog ekrana getiriyor.
        resetPassDialog.getWindow().setAttributes(params);
        resetPassDialog.show();
        //Dialog ekrana verildi.
    }


    //Register ve Login layoutları arasında geçişi sağlayan kodlama bölümü.
    public void viewRegisterPage(View view)
    {
        Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
        startActivity(intent);
    }




}