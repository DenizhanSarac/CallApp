package Fragment;

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

import java.util.ArrayList;
import java.util.List;

import Database.DataBaseHelper;
import Details.YeniUyeDetay;

public class ProfileFragment extends Fragment {
    private String Mail;
    private TextView textIsim,textMail,textMailUst,textTelno,textSifre;
    private ImageView imgResim;
    private View view;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_profile,container,false);

        if (getArguments() != null) {
            Mail = getArguments().getString("params");
            System.out.println(Mail);
        }
        init();

        if(Mail != null)
        {
            DataBaseHelper dataBaseHelper=new DataBaseHelper(view.getContext());
            //List<YeniUyeDetay>Bilgiler=dataBaseHelper.getOne(Mail);

            

        }
        return view;
    }



    //Profil fragment'in xml sayfasındaki elementler bağlanıyor.
    public void init()
    {
        textIsim=(TextView)view.findViewById(R.id.fragment_profile_IsimUst);
        textMail=(TextView)view.findViewById(R.id.fragment_profile_mailAdresi);
        textMailUst=(TextView)view.findViewById(R.id.fragment_profile_ustMail);
        textTelno=(TextView)view.findViewById(R.id.fragment_profile_telNo);
        //Şifre sonra eklenecektir.

    }
}
