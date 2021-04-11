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

    private EditText etxtEmail,etxtPassword,etxtsifreSifirlaMail;
    private CheckBox beniHatirla;
    private String eMail,Pass,getEmail;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Button  sifreSifirlaBtn;
    private boolean check;
    private Dialog resetPassDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Beni hatırla bölümü çalışması için mail-şifre-checkbox burada bağlandı.
        etxtEmail=(EditText)findViewById(R.id.layout_login_editTextEmail);
        etxtPassword = (EditText) findViewById(R.id.layout_login_editTextPassword);
        beniHatirla=(CheckBox)findViewById(R.id.layout_login_ChckRememberMe);

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
    public void btnGirisYap(View v)
    {
        switch (v.getId())
        {
            //Giriş Yap butonu bölümü:
            case R.id.layout_login_buttonGirisYap:
                System.out.println("Giriş");
                //TRY-CATCH ile hatalar yakalanmaya çalışıyor.
                try{
                    eMail=etxtEmail.getText().toString();
                    Pass= etxtPassword.getText().toString();
                    //Email ve şifre bölümleri boş bırakılamaz olayı koşullanıyor.
                    if(!TextUtils.isEmpty(eMail) && !TextUtils.isEmpty(Pass))
                    {
                        //Giriş kontrol kodları buraya eklenecek. //

                        /* **************


                         */

                        if(beniHatirla.isChecked())
                        {
                            //Dahili belleğe bilgiler kaydediliyor.
                            editor=preferences.edit();
                            editor.putString("email",eMail);
                            editor.putBoolean("checkbox",true);
                            editor.apply();
                        }
                        else
                        {
                            //Dahili belleğe checkbox seçili olmadığı için veri boş giriliyor.
                            editor=preferences.edit();
                            editor.putString("email",null);
                            editor.putBoolean("checkbox",false);
                            editor.apply();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Email veya Şifre Boş olamaz",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;

            //Kayıt olma bölümü yakalaması.
            case R.id.layout_register_ButtonUyeOl:
                System.out.println("Üyelik");
                Toast.makeText(getApplicationContext(),"Boş",Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }

    }

    //Login Activity'de sol üstte bulunan çarpı(Çıkış) simgesi için çıkış kodlaması.
    public void imgCikisIcon(View view)
    {
        ImageView imgCikis;
        imgCikis=(ImageView)findViewById(R.id.activity_login_CloseApp);

        imgCikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroy();
            }
        });
    }


    //Register ve Login layoutları arasında geçişi sağlayan kodlama bölümü.
    public void viewLoginPage(View view)
    {
        TextView txtLogin=(TextView)findViewById(R.id.layout_register_SignIn);
        TextView txtRegister=(TextView)findViewById(R.id.layout_login_SignUp);

        View includedLogin = findViewById(R.id.activity_login_LoginPage);
        View includedRegister = findViewById(R.id.activity_login_RegisterPage);

        //Bu bölümde hesabı olmayan kişilerin kaydolması için Register penceresine geçmesi sağlanır.
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                includedRegister.setVisibility(view.VISIBLE);
                includedLogin.setVisibility(view.GONE);
            }
        });

        //Bu bölümde hesabı olan kişilerin giriş yapması için login penceresine geçmesi sağlanır.
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                includedRegister.setVisibility(view.GONE);
                includedLogin.setVisibility(view.VISIBLE);
            }
        });

    }

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




}