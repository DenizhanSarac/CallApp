package CallManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.telecom.Call;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.callapp.R;

import java.util.concurrent.TimeUnit;

import Activities.MainActivity;
import Details.Cagri;
import Fragment.CallcenterFragment;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static CallManager.GsmCall.Status.ACTIVE;
import static CallManager.GsmCall.Status.DISCONNECTED;
import static CallManager.GsmCall.Status.RINGING;

public class CallActivity extends AppCompatActivity{

    private Disposable updatesDisposable;
    private Disposable timerDisposable;
    private TextView textDuration;
    private TextView textStatus;
    private ImageView buttonAnswer;
    private ImageView buttonHangup;
    private TextView textDisplayName;
    String gidecekMesaj;
    boolean takip;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    String Number;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        preferences=this.getSharedPreferences("com.example.callapp", Context.MODE_PRIVATE);
        gidecekMesaj=preferences.getString("Mesaj",gidecekMesaj);
        takip=preferences.getBoolean("Durum",takip);

        hideBottomNavigationBar();


        textDuration = findViewById(R.id.textDuration);
        textStatus = findViewById(R.id.textStatus);
        buttonAnswer = findViewById(R.id.buttonAnswer);
        buttonHangup = findViewById(R.id.buttonHangup);
        textDisplayName = findViewById(R.id.textDisplayName);

            buttonHangup.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View view) {
                    CallManager.cancelCall();
                }
            });

        buttonAnswer.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                CallManager.acceptCall();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();
        updatesDisposable = CallManager.updates()
                .doOnError (throwable ->{ Log.e("LOG_TAG", "Error processing call");})
                .subscribe (it->{ updateView((GsmCall)it); });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void updateView(GsmCall gsmCall) {
        if(!gsmCall.getStatus().equals(ACTIVE)){
            textStatus.setVisibility(View.VISIBLE);
            textDuration.setVisibility(View.GONE);
        }
        switch (gsmCall.getStatus()){
            case CONNECTING:textStatus.setText("Connecting…");break;
            case DIALING:textStatus.setText("Calling…");break;
            case RINGING:
                textStatus.setText("Incoming call");
                buttonAnswer.setVisibility(View.VISIBLE);
                break;
            case ACTIVE:
                textStatus.setText("");
                textStatus.setVisibility(View.GONE);
                textDuration.setVisibility(View.VISIBLE);
                startTimer();
                break;
            case DISCONNECTED:
                textStatus.setText("Finished call");
                buttonHangup.setVisibility(View.GONE);
                buttonHangup.postDelayed(() -> finish(), 3000);
                stopTimer();
                break;
            case UNKNOWN:textStatus.setText("");break;
        }

        if(!gsmCall.getStatus().equals(DISCONNECTED)){
            buttonHangup.setVisibility(View.VISIBLE);
        }
        if(!gsmCall.getStatus().equals(RINGING)){
            buttonAnswer.setVisibility(View.GONE);
        }
        if(gsmCall.getDisplayName().equals(null)){
            textDisplayName.setText("Unknown");
        }else{
            textDisplayName.setText(gsmCall.getDisplayName());
            Number=textDisplayName.getText().toString();
        }

        if(takip){
            CallManager.cancelCall();
            SmsManager smgr = SmsManager.getDefault();
            smgr.sendTextMessage(Number,null,gidecekMesaj,null,null);
        }else{}
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void hideBottomNavigationBar(){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // only for gingerbread and newer versions
            getWindow().setDecorFitsSystemWindows(false);
        }else{
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }

    private void startTimer() {
        timerDisposable = Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(longTimeInterval -> {
                    textDuration.setText(toDurationString(longTimeInterval ));
                });
    }

    private void stopTimer() {
        timerDisposable.dispose();
    }

    private String toDurationString(Long l){
        return String.format("%02d:%02d:%02d", l / 3600, (l % 3600) / 60, (l % 60));
    }





}