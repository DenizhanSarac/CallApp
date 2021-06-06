
/*  DENİZHAN SARAÇ
 *   dnzhn.src@outlook.com
 *   Computer Engineer at BİLECİK ŞEYH EDEBALİ UNIVERSITY
 *   CALL APP FOR THEASIS
 *   ALL RIGHTS RESERVED
 *   11.04.2021 17:02
 *   GITHUB:  https://github.com/DenizhanSarac/CallApp*/

package Fragment;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.telecom.Call;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.callapp.R;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Activities.MainActivity;
import CallManager.CallActivity;
import Database.DataBaseHelper;
import Details.Cagri;
import Details.CagriLog;
import RecyclerAdapter.CagriKaydiAdapter;
import RecyclerAdapter.MesajlarRecycler;

import static android.view.View.GONE;

public class CallcenterFragment extends Fragment {
    public static final String MY_NOTIFICATION = "my_notification";
    private static final int TAG_SIMPLE_NOTIFICATION = 1;
    View view;
    boolean takip=false;
    DataBaseHelper dataBaseHelper;
    Switch aSwitch;
    EditText yeniMesajEdt;
    String Mail,Mesaj;
    private int ID;
    Button MesajEklebtn,onaylaBtn,yeniMesajEkleBtn;
    RelativeLayout mesajlar,yeniMesaj;
    Cagri vtCagri,callCagri;
    String gidecekMesaj;

    private Dialog resetPassDialog;
    private Button btnEkle;
    private EditText edt;

    private RecyclerView recyclerView,RvlogView;
    private MesajlarRecycler mesajlarRecycler;
    RelativeLayout relativeLayout;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_callcenter, container, false);

        init();

        relativeLayout.setVisibility(View.GONE);

        if (getArguments() != null) {
            Mail = getArguments().getString("params");
        }
        dataBaseHelper = new DataBaseHelper(view.getContext());
        ID=dataBaseHelper.getId(Mail);


        preferences=getContext().getSharedPreferences("com.example.callapp", Context.MODE_PRIVATE);
        gidecekMesaj=preferences.getString("Mesaj",gidecekMesaj);
        takip=preferences.getBoolean("Durum",takip);

        if(takip==true && !TextUtils.isEmpty(gidecekMesaj)){
            aSwitch.setChecked(true);
            sendNotification("Çağrı Engelleme",gidecekMesaj);
        }else{
            aSwitch.setChecked(false);
            ((NotificationManager) view.getContext().getSystemService(Context.NOTIFICATION_SERVICE)).cancel(1);
        }


        listAdapter();
        showLog();



        onaylaBtn.setEnabled(false);
        yeniMesajEkleBtn.setEnabled(false);
        //Etkin-Devredışı özellikleri burada devreye girer.
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    relativeLayout.setVisibility(View.VISIBLE);
                    takip=true;
                    onaylaBtn.setEnabled(true);
                    yeniMesajEkleBtn.setEnabled(true);
                    recyclerView.setVisibility(View.VISIBLE);

                    yeniMesajEkleBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mesajlar.setVisibility(GONE);

                            resetPassDialog=new Dialog(view.getContext());
                            WindowManager.LayoutParams params=new WindowManager.LayoutParams();
                            params.copyFrom(resetPassDialog.getWindow().getAttributes());
                            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                            params.height=WindowManager.LayoutParams.WRAP_CONTENT;
                            resetPassDialog.setCancelable(false);
                            resetPassDialog.setContentView(R.layout.layout_mesaj_ekle);

                            ImageView imgSifreCikis=resetPassDialog.findViewById(R.id.layout_yeni_mesaj_SifreDialogKapat);
                            btnEkle=resetPassDialog.findViewById(R.id.layout_yeni_mesaj_ButtonSifreSifirla);
                            edt=resetPassDialog.findViewById(R.id.layout_yeni_mesaj_EditTxtMail);

                            imgSifreCikis.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    resetPassDialog.dismiss();
                                    refreshMethod();
                                }
                            });

                            btnEkle.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Mesaj=edt.getText().toString();
                                    if(!TextUtils.isEmpty(Mesaj)){
                                        vtCagri=new Cagri(Mesaj,ID);
                                        boolean check=dataBaseHelper.addCagri(vtCagri);
                                        if(check){
                                            Toast.makeText(resetPassDialog.getContext(),"Başarılı",Toast.LENGTH_SHORT).show();
                                            resetPassDialog.dismiss();
                                            refreshMethod();
                                        }
                                        else{
                                            Toast.makeText(resetPassDialog.getContext(),"Hata",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    Toast.makeText(resetPassDialog.getContext(), "Lütfen Geçerli Mesaj Giriniz.", Toast.LENGTH_SHORT).show();
                                }
                            });

                            resetPassDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            resetPassDialog.getWindow().setAttributes(params);
                            resetPassDialog.show();

                        }
                    });


                }else{

                    relativeLayout.setVisibility(View.GONE);
                    takip=false;
                    mesajlar.setVisibility(View.VISIBLE);
                    yeniMesaj.setVisibility(View.GONE);
                    onaylaBtn.setEnabled(false);
                    yeniMesajEkleBtn.setEnabled(false);


                    editor=preferences.edit();
                    editor.putString("Mesaj",null);
                    editor.putBoolean("Durum",takip);
                    editor.apply();
                    ((NotificationManager) view.getContext().getSystemService(Context.NOTIFICATION_SERVICE)).cancel(1);
                    refreshMethod();


                }
            }
        });



        //Onayla butonu
        onaylaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(gidecekMesaj)){
                    editor=preferences.edit();
                    editor.putString("Mesaj",gidecekMesaj);
                    editor.putBoolean("Durum",takip);
                    editor.apply();
                    sendNotification("Çağrı engelleme başlatıldı. ",gidecekMesaj);
                    onaylaBtn.setEnabled(false);
                }else{
                    Toast.makeText(view.getContext(),"Mesaj seçilmedi.",Toast.LENGTH_SHORT).show();

                }


            }
        });

        return view;
    }

    public void init(){
        aSwitch=(Switch)view.findViewById(R.id.fragment_callcenter_switch);
        yeniMesajEdt=(EditText) view.findViewById(R.id.fragment_callcenter_editMesaj);
        MesajEklebtn=(Button)view.findViewById(R.id.fragment_callcenter_ayarlaBtn);
        onaylaBtn=(Button)view.findViewById(R.id.fragment_callcenter_onaylaBtn);
        yeniMesajEkleBtn=(Button)view.findViewById(R.id.fragment_callcenter_yeniMesajBtn);
        mesajlar=(RelativeLayout)view.findViewById(R.id.fragment_callcenter_EkliMesajlariGosterRelativeLayout);
        yeniMesaj=(RelativeLayout)view.findViewById(R.id.fragment_callcenter_YeniMesajEkleRelativeLayout);

        recyclerView=(RecyclerView)view.findViewById(R.id.fragment_callcenter_RecyclerView);
        relativeLayout=view.findViewById(R.id.fragment_callcenter_EkliMesajlariGosterRelativeLayout);

    }

    public void listAdapter() {
        mesajlarRecycler = new MesajlarRecycler(dataBaseHelper.getMesaj(ID), view.getContext());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mesajlarRecycler);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(view.getContext()).build());

        mesajlarRecycler.setOnItemClickListener(new MesajlarRecycler.onItemClickListener() {
            @Override
            public void onItemClick(Cagri cagri, int position) {
                gidecekMesaj = cagri.getMesaj();
                view.setSelected(true);
            }
        });

    }

        private void sendNotification(String messageTitle,String messageBody) {
            NotificationManager notificationManager =
                    (NotificationManager)view.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(view.getContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                @SuppressLint("WrongConstant")
                NotificationChannel notificationChannel=new NotificationChannel(MY_NOTIFICATION,"n_channel",NotificationManager.IMPORTANCE_MAX);
                notificationChannel.setDescription("description");
                notificationChannel.setName("Channel Name");
                assert notificationManager != null;
                notificationManager.createNotificationChannel(notificationChannel);
            }


            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(view.getContext())
                    .setSmallIcon(R.mipmap.ic_application_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_application_launcher))
                    .setContentTitle(messageTitle)
                    .setContentText("Gönderilecek olan mesaj : "+ messageBody)
                    .setAutoCancel(true)
                    .setSound(soundUri)
                    .setContentIntent(pendingIntent)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setOnlyAlertOnce(true)
                    .setChannelId(MY_NOTIFICATION)
                    .setColor(Color.parseColor("#3F5996"));

            //.setProgress(100,50,false);
            assert notificationManager != null;
            int m = 1;
            notificationManager.notify(m, notificationBuilder.build());
        }


        private List<CagriLog> getCallLogs(){
            List<CagriLog> list=new ArrayList<>();

            int MISSED_CALL_TYPE = android.provider.CallLog.Calls.MISSED_TYPE;

            if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_CALL_LOG},1);
            }
            Cursor cursor = getContext().getContentResolver().query(CallLog.Calls.CONTENT_URI,null,null,null,CallLog.Calls.DATE + " DESC ");
            int nameIndex=cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
            int duration=cursor.getColumnIndex(CallLog.Calls.TYPE);
            int date_idx=cursor.getColumnIndex(CallLog.Calls.DATE);


            cursor.moveToFirst();
            while (cursor.moveToNext()){
                Date date=new Date(Long.valueOf(cursor.getString(date_idx)));

                String mnth_date,week_day,month;

                mnth_date= (String) DateFormat.format("dd",date);
                week_day= (String) DateFormat.format("EEEE",date);
                month= (String) DateFormat.format("MMM",date);
                String dir = null;
                String callType = cursor.getString(duration);
                int dircode = Integer.parseInt(callType);
                switch (dircode){
                    case CallLog.Calls.OUTGOING_TYPE:
                        dir = "Giden Çağrı";
                        break;
                    case CallLog.Calls.INCOMING_TYPE:
                        dir = "Gelen Çağrı";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        dir = "Cevapsız Çağrı";
                        break;
                }



                list.add(new CagriLog(cursor.getString(nameIndex),dir,week_day+ " "+ mnth_date + " "+ month ));
            }

            return  list;
        }

        private void LogAdapter(){
            RvlogView=view.findViewById(R.id.fragment_callcenter_CalllogRV);
            LinearLayoutManager linearLayout=new LinearLayoutManager(getContext());
            RecyclerView.LayoutManager layoutManager=linearLayout;
            RvlogView.setLayoutManager(layoutManager);

            CagriKaydiAdapter adapter=new CagriKaydiAdapter(getContext(),getCallLogs());

            RvlogView.setAdapter(adapter);
        }


        public void showLog(){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG}, 1);
                //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
            } else {
                // Android version is lesser than 6.0 or the permission is already granted.
                LogAdapter();
            }
        }

        public void refreshMethod(){
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (Build.VERSION.SDK_INT >= 26) {
                ft.setReorderingAllowed(false);
            }
            ft.detach(this).attach(this).commit();
        }




}






