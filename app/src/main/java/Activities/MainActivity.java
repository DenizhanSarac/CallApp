
/*  DENİZHAN SARAÇ
 *   dnzhn.src@outlook.com
 *   Computer Engineer at BİLECİK ŞEYH EDEBALİ UNIVERSITY
 *   CALL APP FOR THEASIS
 *   ALL RIGHTS RESERVED
 *   11.04.2021 17:02
 *   GITHUB:  https://github.com/DenizhanSarac/CallApp*/

package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import Fragment.ContactFragment;
import Fragment.CallcenterFragment;
import Fragment.ProfileFragment;

import com.example.callapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_FRAGMENT_TO_LAUNCH = "fragment_to_launch";
    public static final boolean TAG_NOTIFICATION_FRAGMENT = true;


    //Gerekli değişkenler.
    BottomNavigationView bottombar;
    private String gelenMail;
    Bundle bundle,bundle2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Kayıt ol yada giriş yap sayfasından gelen mail burada yakalanıyor.
        Intent intent=getIntent();
        gelenMail=intent.getStringExtra("kullaniciMail");

        //Profil sayfasına gönderilen mail adresidir.
        bundle = new Bundle();
        bundle.putString("params", gelenMail);
        bottombar=(BottomNavigationView)findViewById(R.id.activity_main_bottombar);
        bottombar.setOnNavigationItemSelectedListener(navListener);

        //Uygulama açıldığında kullanıcının anasayfaya yönlendirmesi için yazılan koddur.
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_fragmentContain,
                new ContactFragment()).commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    //Bottombar'da seçilen sayfanın hangisi olduğunu bulan kod parçacığıdır.
    private BottomNavigationView.OnNavigationItemSelectedListener navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment=null;

                    switch (item.getItemId()){
                        case R.id.bottom_navigation_contact:
                            selectedFragment = new ContactFragment();
                            break;
                        case R.id.bottom_navigation_message:
                            selectedFragment = new CallcenterFragment();
                            selectedFragment.setArguments(bundle);
                            break;
                        case R.id.bottom_navigation_profile:
                            selectedFragment = new ProfileFragment();
                            selectedFragment.setArguments(bundle);
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_fragmentContain,
                            selectedFragment,gelenMail).commit();

                    return true;
                }
            };


    /*public void aramaTakip(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_STATE);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
        }
        }*/

        /*public void aramaLog(){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG}, 1);
                //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
            } else {
                // Android version is lesser than 6.0 or the permission is already granted.
            }
        }*/



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(getApplicationContext(), "İzin verilmedikçe kişileri göremezsiniz.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*public void addNotificaction(){
        NotificationManager manager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder=new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Çağrı Engelleme")
                .setContentText("Çağrı Engelleme Etkin")
                .setTicker("AAAA")
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentIntent(PendingIntent.getActivity(this,0,new Intent(this, MainActivity.class),0));

        Notification notification=builder.build();
        manager.notify(0,notification);

    }

    public void removeNotificaction(){
        ((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE)).cancel(0);
    }*/
}