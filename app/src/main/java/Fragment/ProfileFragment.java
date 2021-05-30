/*  DENİZHAN SARAÇ
 *   dnzhn.src@outlook.com
 *   Computer Engineer at BİLECİK ŞEYH EDEBALİ UNIVERSITY
 *   CALL APP FOR THEASIS
 *   ALL RIGHTS RESERVED
 *   11.04.2021 17:02
 *   GITHUB:  https://github.com/DenizhanSarac/CallApp*/


package Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.callapp.R;

import java.io.ByteArrayInputStream;

import Activities.EditProfileDetail;
import Database.DataBaseHelper;
import Details.YeniUyeDetay;

public class ProfileFragment extends Fragment {
    //Gerekli değişkenler
    private String Mail;
    private TextView textIsim,textMail,textMailUst,textTelno,textSifre,textMeslek,textDogumTarihi;
    private ImageView imgResim,imgEditbtn;
    private View view;



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


        return view;
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

        //Şifre sonra eklenecektir.

    }
}
