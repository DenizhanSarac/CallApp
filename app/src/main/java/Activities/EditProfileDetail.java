package Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.callapp.R;
import Details.setDate;

import Database.DataBaseHelper;
import Details.YeniUyeDetay;
import Fragment.ProfileFragment;

public class EditProfileDetail extends AppCompatActivity {
    private String Mail;
    private String isim,telefon,mail,eskiSifre,yeniSifre,dogumTarih,Meslek,DataSifre;
    //Gerekli değişkenler tanımlanıyor.
    private EditText edtisim,edttel,edtmail,edtesifre,edtysifre,edtDogum,edtMeslek;
    private byte[] resim;
    private Button btnEkle;
    private DataBaseHelper dataBaseHelper;
    YeniUyeDetay yeniUyeDetay;
    int Check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_detail);

        //Email adres yakalayıcı çalışıyor:
        emailCatch();
        init();

        //Edittext içerisine takvim ekleme.
        setDate fromDate = new setDate(edtDogum, this);

        dataBaseHelper=new DataBaseHelper(this);
        ekranaBas(dataBaseHelper);
    }

    //Button OnClick Olayı
    public void btnGuncelle(View v){
        isim=edtisim.getText().toString();
        telefon=edttel.getText().toString();
        mail=edtmail.getText().toString();
        DataSifre=yeniUyeDetay.getSifre();
        eskiSifre=edtesifre.getText().toString();
        yeniSifre=edtysifre.getText().toString();
        dogumTarih=edtDogum.getText().toString();
        Meslek=edtMeslek.getText().toString();
        if(!TextUtils.isEmpty(isim)){
            if(!TextUtils.isEmpty(telefon)){
                    if(TextUtils.isEmpty(edtysifre.getText()))
                    {
                        try{
                            YeniUyeDetay yeniUyeDetay=new YeniUyeDetay(-1,isim,telefon,mail,DataSifre,resim,dogumTarih,Meslek);
                            Check=dataBaseHelper.updateOne(yeniUyeDetay);
                            if(Check == 1)
                            {
                                showToast("Güncelleme Başarılı");
                                ProfileFragment fragment=new ProfileFragment();
                                FragmentManager fragmentManager=getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.fragment_profile,fragment).commit();
                            }
                            else
                            {
                                showToast("Güncelleme Hatalı !! ");
                                ProfileFragment fragment=new ProfileFragment();
                                FragmentManager fragmentManager=getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.fragment_profile,fragment).commit();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                    else{
                        if(dataBaseHelper.checkPass(DataSifre,eskiSifre) && yeniSifre.length() >=8 ){
                            try{
                                YeniUyeDetay yeniUyeDetay=new YeniUyeDetay(-1,isim,telefon,mail,yeniSifre,resim,dogumTarih,Meslek);
                                Check=dataBaseHelper.updateOne(yeniUyeDetay);
                                if(Check == 1)
                                {
                                    showToast("Güncelleme Başarılı");
                                    Intent intent=new Intent(getApplicationContext(), ProfileFragment.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    showToast("Güncelleme Hatalı !! ");
                                    Intent intent=new Intent(getApplicationContext(), ProfileFragment.class);
                                    startActivity(intent);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }else
                            showToast("Eski Şifre yanlış veya Yeni Şifre Uzunluğu 8'den Az.");
                    }
            }showToast("Telefon Boş Bırakılamaz.");
        } showToast("İsim Boş Bırakılamaz.");



    }
    //Ekrana kullanıcının verilerini getiren bölümdür.
    private void ekranaBas(DataBaseHelper veri)
    {
        yeniUyeDetay=dataBaseHelper.getOne(Mail);
        edtisim.setText(yeniUyeDetay.getAd());
        edttel.setText(yeniUyeDetay.getTelNo());
        edtmail.setText(yeniUyeDetay.geteMail());
        edtDogum.setText(yeniUyeDetay.getDogum());
        edtMeslek.setText(yeniUyeDetay.getMeslek());
        resim=yeniUyeDetay.getResim();
    }


    //Email adresini Yakalama.
    private void emailCatch()
    {
        Intent intent=getIntent();
        Mail=intent.getStringExtra("kullaniciMail");
    }


    //Layout bağlantıları yapıldı.
    public void init(){
        edtisim=(EditText)findViewById(R.id.edit_EditTxtIsim);
        edttel=(EditText)findViewById(R.id.edit_EditTxtTelNo);
        edtmail=(EditText)findViewById(R.id.edit_EditTxtEmail);
        edtesifre=(EditText)findViewById(R.id.edit_EditTxtSifre);
        edtysifre=(EditText)findViewById(R.id.edit_Yeni_EditTxtSifre);
        edtDogum=(EditText)findViewById(R.id.edit_EditTxtDTarih);
        btnEkle=(Button)findViewById(R.id.edit_BtnDuzen);
        edtMeslek=(EditText)findViewById(R.id.edit_EditTxtMeslek);
    }
    //Toast methodu.
    private void showToast(String Mesaj)
    {
        Toast.makeText(getApplicationContext(),Mesaj,Toast.LENGTH_SHORT).show();
    }
}