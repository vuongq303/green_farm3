package javax.microedition.midlet;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import com.facebook.appevents.AppEventsConstants;
import com.gameloft.android.wrapper.au;

/* JADX INFO: compiled from: MIDlet_Child.java */
/* JADX INFO: loaded from: classes.dex */
public class n {
    public static void onCreate(Bundle bundle) {
        com.gameloft.android.ANMP.GloftGF2F.PushNotification.q.i(au.getActivity());
        String strBY = MIDlet.cnz.bY("Has-Push-Notification");
        if (strBY != null) {
            if (strBY.compareTo(AppEventsConstants.EVENT_PARAM_VALUE_YES) == 0) {
                com.gameloft.android.ANMP.GloftGF2F.PushNotification.q.T(true);
            } else {
                com.gameloft.android.ANMP.GloftGF2F.PushNotification.q.T(false);
            }
        }
    }

    public static void onNewIntent(Intent intent) {
    }

    public static void onDestroy() {
    }

    public static void onPause() {
        MIDlet.akG();
    }

    public static void onSaveInstanceState(Bundle bundle) {
    }

    public static void onRestart() {
    }

    public static void onResume() {
    }

    public static void onStart() {
    }

    static void onStop() {
    }

    public static void onWindowFocusChanged(boolean z) {
    }

    public static void onConfigurationChanged(Configuration configuration) {
    }

    public static void onActivityResult(int i, int i2, Intent intent) {
    }
}
