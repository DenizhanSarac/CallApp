package CallManager;

import android.os.Build;
import android.telecom.Call;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import io.reactivex.Observable;

import io.reactivex.subjects.BehaviorSubject;

public class CallManager {
    private static BehaviorSubject subject;
    private static Call currentCall=null;
    public static CallManager INSTANCE;

    public static Observable updates(){
        BehaviorSubject behaviorSubject=subject;
        return (Observable)behaviorSubject;
    }

    public static void updateCall(@Nullable Call call){
        currentCall = call;
        if(call != null){
            subject.onNext(MappersJava.toGsmCall(call));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void cancelCall() {
        Call call = currentCall;
        if (call != null) {
            if (call.getState() == Call.STATE_RINGING) {
                INSTANCE.rejectCall();
            } else {
                INSTANCE.disconnectCall();
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void acceptCall() {
        Call call = currentCall;
        if (call != null) {
            call.answer(call.getDetails().getVideoState());
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private static void rejectCall() {
        Call call = currentCall;
        if (call != null) {
            call.reject(false, "");
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private static void disconnectCall() {
        Call call = currentCall;
        if (call != null) {
            call.disconnect();
        }

    }

    static {
        CallManager var0 = new CallManager();
        INSTANCE = var0;
        subject = BehaviorSubject.create();
    }

}
