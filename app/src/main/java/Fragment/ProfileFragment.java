/*  DENİZHAN SARAÇ
 *   dnzhn.src@outlook.com
 *   Computer Engineer at BİLECİK ŞEYH EDEBALİ UNIVERSITY
 *   CALL APP FOR THEASIS
 *   ALL RIGHTS RESERVED
 *   11.04.2021 17:02
 *   GITHUB:  https://github.com/DenizhanSarac/CallApp*/


package Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.callapp.R;

import java.io.ByteArrayInputStream;

import Activities.EditProfileDetail;
import Activities.addImageActivity;
import Database.DataBaseHelper;
import Details.YeniUyeDetay;

public class ProfileFragment extends Fragment {
    //Gerekli değişkenler
    private String Mail;
    private TextView textIsim,textMail,textMailUst,textTelno,textSifre,textMeslek,textDogumTarihi;
    private ImageView imgResim,imgEditbtn;
    private View view;


    private EditText etxtsifreSifirlaMail,edtYeniSifre1,edtYeniSifre2;
    private String yeniSifre1,yeniSifre2;
    //Şifre Sıfırlama değişkenleri
    private Button  sifreSifirlaBtn,sifreSifirlaOnayla;
    private Dialog resetPassDialog;
    LinearLayout MailLayout,SifreLayout;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Profil fragment xml dosyasının içeriğine erişmek için view kullanılıyor.
        view= inflater.inflate(R.layout.fragment_profile,container,false);

        //Mail adresi alınıyor.
        if (getArguments() != null) {
            Mail = getArguments().getString("params");

        }
        init();
        //Burada Login yada Register penceresinden gelen mail adresinin dolu olup olmadığı kontrol ediliyor.
        if(Mail != null)
        {
            //Veritabanından kullanıcının verileri çekiliyor.
            DataBaseHelper dataBaseHelper=new DataBaseHelper(view.getContext());
            YeniUyeDetay yeniUyeDetay=dataBaseHelper.getOne(Mail);

            //Veriler Profil sayfasına ekleniyor.
            textIsim.setText(yeniUyeDetay.getAd());
            textMail.setText(yeniUyeDetay.geteMail());
            textMailUst.setText(yeniUyeDetay.geteMail());
            textTelno.setText(yeniUyeDetay.getTelNo());
            if(yeniUyeDetay.getMeslek() == null){textMeslek.setText("Meslek");}
            else{textMeslek.setText(yeniUyeDetay.getMeslek());}
            if(yeniUyeDetay.getDogum() == null){textDogumTarihi.setText("Doğum Tarihi");}
            else{textDogumTarihi.setText(yeniUyeDetay.getDogum());}

            //Byte olan resim burada Bitmap formatına dönüşüyor.
            ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(yeniUyeDetay.getResim());
            Bitmap bitmap = BitmapFactory.decodeStream(arrayInputStream);
            imgResim.setImageBitmap(bitmap);

        }

        //Mail adresine tıklandığında posta uygulamasını açan kod parçacığıdır.
        textMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, textMail.getText().toString());
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        imgEditbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(view.getContext(), EditProfileDetail.class);
                intent.putExtra("kullaniciMail",Mail);
                startActivity(intent);
            }
        });

        imgResim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(view.getContext(), addImageActivity.class);
                intent.putExtra("email",Mail);
                startActivity(intent);
            }
        });

        textSifre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPass();
            }
        });
        return view;
    }



    public void resetPass(){
        //Dialog oluşturuluyor.
        resetPassDialog=new Dialog(view.getContext());
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
                        Toast.makeText(view.getContext(),"Mail adresi hatalı.",Toast.LENGTH_SHORT).show();
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
                            DataBaseHelper dataBaseHelper=new DataBaseHelper(view.getContext());
                            sonuc=dataBaseHelper.sifreSifirla(etxtsifreSifirlaMail.getText().toString(),yeniSifre1);
                            if(sonuc){
                                Toast.makeText(view.getContext(),"İşlem başarılı.",Toast.LENGTH_SHORT).show();
                                resetPassDialog.dismiss();
                            }else
                                Toast.makeText(view.getContext(),"İşlem başarısız.",Toast.LENGTH_SHORT).show();

                        }else
                            Toast.makeText(view.getContext(),"Şifreler uyuşmuyor.",Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(view.getContext(),"Yeni şifre tekrar boş olamaz.",Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(view.getContext(),"Yeni şifre boş olamaz.",Toast.LENGTH_SHORT).show();
            }
        });


        //Dialog ekrana getiriyor.
        resetPassDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        resetPassDialog.getWindow().setAttributes(params);
        resetPassDialog.show();
        //Dialog ekrana verildi.
    }
    //Profil fragment'in xml sayfasındaki elementler bağlanıyor.
    public void init()
    {
        textIsim=(TextView)view.findViewById(R.id.fragment_profile_IsimUst);
        textMail=(TextView)view.findViewById(R.id.fragment_profile_mailAdresi);
        textMailUst=(TextView)view.findViewById(R.id.fragment_profile_ustMail);
        textTelno=(TextView)view.findViewById(R.id.fragment_profile_telNo);
        textMeslek=(TextView)view.findViewById(R.id.fragment_profile_meslekIsmi);
        textDogumTarihi=(TextView)view.findViewById(R.id.fragment_profile_dogumTarihi);
        imgResim=(ImageView)view.findViewById(R.id.fragment_profile_ImageResim);
        imgEditbtn=(ImageView)view.findViewById(R.id.fragment_profile_imgEdit);
        textSifre=(TextView) view.findViewById(R.id.fragment_profile_sifreSifirla);
        //Şifre sonra eklenecektir.

    }
}
