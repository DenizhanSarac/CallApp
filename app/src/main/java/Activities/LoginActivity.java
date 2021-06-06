/*  DENİZHAN SARAÇ
*   dnzhn.src@outlook.com
*   Computer Engineer at BİLECİK ŞEYH EDEBALİ UNIVERSITY
*   CALL APP FOR THEASIS
*   ALL RIGHTS RESERVED
*   11.04.2021 17:02
*   GITHUB:  https://github.com/DenizhanSarac/CallApp*/

package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import Database.DataBaseHelper;
import com.example.callapp.R;

public class LoginActivity extends AppCompatActivity {



    //Giriş yap değişkenleri
    private EditText etxtEmail,etxtPassword,etxtsifreSifirlaMail,edtYeniSifre1,edtYeniSifre2;
    private CheckBox beniHatirla;
    private String eMail,Pass,getEmail,yeniSifre1,yeniSifre2;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    //Şifre Sıfırlama değişkenleri
    private Button  sifreSifirlaBtn,sifreSifirlaOnayla;
    private boolean check;
    private Dialog resetPassDialog;
    LinearLayout MailLayout,SifreLayout;

    //Çıkış Butonu değişkeni
    private ImageView imgCikis;

    //Veritabanı bağlanıyor.

    private DataBaseHelper dataBaseHelper;



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
                    eMail=eMail.trim();
                    Pass = etxtPassword.getText().toString();
                    //Email ve şifre bölümleri boş bırakılamaz olayı koşullanıyor.
                    if (!TextUtils.isEmpty(eMail) && !TextUtils.isEmpty(Pass)) {

                        //SQL Komutları burada başlıyor.
                        dataBaseHelper=new DataBaseHelper(LoginActivity.this);
                        boolean check=dataBaseHelper.checkMailPass(eMail,Pass);
                        if(check==true)
                        {
                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            intent.putExtra("kullaniciMail",eMail);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Mail veya Şifre Hatalı",Toast.LENGTH_SHORT).show();
                        }


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
        //Mail Bölümü
        ImageView imgSifreCikis=(ImageView)resetPassDialog.findViewById(R.id.sifre_sifirla_SifreDialogKapat);
        sifreSifirlaBtn=(Button)resetPassDialog.findViewById(R.id.layout_sifre_sifirla_ButtonSifreSifirla);
        etxtsifreSifirlaMail=(EditText)resetPassDialog.findViewById(R.id.layout_sifre_sifirla_EditTxtMail);
        MailLayout=(LinearLayout)resetPassDialog.findViewById(R.id.layout_sifre_sifirla_mailLinear);
        //Şifre Bölümü
        ImageView imgSifreCikis2=(ImageView)resetPassDialog.findViewById(R.id.sifre_sifirla_YeniSifreDialogKapat);
        edtYeniSifre1=(EditText)resetPassDialog.findViewById(R.id.layout_sifre_sifirla_YeniSifre1);
        edtYeniSifre2=(EditText)resetPassDialog.findViewById(R.id.layout_sifre_sifirla_YeniSifre2);
        SifreLayout=(LinearLayout)resetPassDialog.findViewById(R.id.layout_sifre_sifirla_YeniSifreLinear);
        sifreSifirlaOnayla=(Button)resetPassDialog.findViewById(R.id.layout_sifre_sifirla_onaylaSifre);
        //Veriler Çekildi.

        //Dialogda bulunan kapatma ImageView yakalanma olayı.
        imgSifreCikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassDialog.dismiss();
            }
        });
        imgSifreCikis2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassDialog.dismiss();
            }
        });
        //Sifre Gönderme olayı başlıyor.
        sifreSifirlaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(etxtsifreSifirlaMail.getText().toString().trim()))
                {
                    DataBaseHelper dataBaseHelper=new DataBaseHelper(resetPassDialog.getContext());
                    if(dataBaseHelper.checkMail(etxtsifreSifirlaMail.getText().toString()))
                    {
                        MailLayout.setVisibility(View.GONE);
                        SifreLayout.setVisibility(View.VISIBLE);
                    }else{
                        Toast.makeText(getApplicationContext(),"Mail adresi hatalı.",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //Şifre gönderme olayı bitiyor.
        sifreSifirlaOnayla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yeniSifre1=edtYeniSifre1.getText().toString();
                yeniSifre2=edtYeniSifre2.getText().toString();
                if(!TextUtils.isEmpty(yeniSifre1) && yeniSifre1.length() >= 8){
                    if(!TextUtils.isEmpty(yeniSifre2) && yeniSifre2.length() >=8 ){
                        if(yeniSifre1.equals(yeniSifre2)){
                            boolean sonuc;
                            DataBaseHelper dataBaseHelper=new DataBaseHelper(getApplicationContext());
                            sonuc=dataBaseHelper.sifreSifirla(etxtsifreSifirlaMail.getText().toString(),yeniSifre1);
                            if(sonuc){
                                Toast.makeText(getApplicationContext(),"İşlem başarılı.",Toast.LENGTH_SHORT).show();
                                resetPassDialog.dismiss();
                            }else
                                Toast.makeText(getApplicationContext(),"İşlem başarısız.",Toast.LENGTH_SHORT).show();

                        }else
                            Toast.makeText(getApplicationContext(),"Şifreler uyuşmuyor.",Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(getApplicationContext(),"Yeni şifre tekrar boş olamaz.",Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getApplicationContext(),"Yeni şifre boş olamaz.",Toast.LENGTH_SHORT).show();
            }
        });


        //Dialog ekrana getiriyor.
        resetPassDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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