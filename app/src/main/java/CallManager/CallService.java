package CallManager;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.telecom.Call;
import android.telecom.InCallService;

import androidx.annotation.RequiresApi;

import com.example.callapp.R;

import Fragment.CallcenterFragment;

@RequiresApi(api = Build.VERSION_CODES.M)
public class CallService extends InCallService {
    @Override
    public void onCallAdded(Call call) {
        super.onCallAdded(call);
        call.registerCallback(callCallback);
        Intent intent=new Intent(getApplicationContext(),CallActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        CallManager.updateCall(call);
    }



    @Override
    public void onCallRemoved(Call call) {
        super.onCallRemoved(call);
        call.unregisterCallback(callCallback);
        CallManager.updateCall(null);
    }

    private Call.Callback callCallback = new Call.Callback() {
        @Override
        public void onStateChanged(Call call, int state) {
            CallManager.updateCall(call);
        }
    };
}
