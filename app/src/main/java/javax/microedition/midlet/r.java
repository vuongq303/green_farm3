package javax.microedition.midlet;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import com.gameloft.android.wrapper.au;

/* JADX INFO: compiled from: MIDlet_Child_IAP.java */
/* JADX INFO: loaded from: classes.dex */
public class r {
    public static void onCreate(Bundle bundle) {
        if (MIDlet.cnS) {
            com.gameloft.android2d.iap.utils.v.an(au.getActivity());
            new Thread(new s()).start();
        }
    }

    public static void onDestroy() {
        String str = MIDlet.cnZ;
        if (MIDlet.cnS && str != null && (str.equalsIgnoreCase("USE_IAP_GOOGLE_BILLING") || str.equalsIgnoreCase("USE_IAP_BAZAAR_BILLING") || str.equalsIgnoreCase("USE_IAP_SAMSUNG_BILLING") || str.equalsIgnoreCase("USE_IAP_YANDEX_BILLING"))) {
            com.gameloft.android2d.iap.b.onDestroy();
        }
        com.gameloft.android2d.iap.b.Rl();
    }

    public static void onPause() {
        com.gameloft.android2d.iap.b.Rh();
    }

    public static void onSaveInstanceState(Bundle bundle) {
    }

    public static void onRestart() {
        com.gameloft.android2d.iap.b.Rk();
    }

    public static void onResume() {
        com.gameloft.android2d.iap.b.Ri();
    }

    public static void onStart() {
    }

    static void onStop() {
        com.gameloft.android2d.iap.b.Rj();
    }

    public static void onNewIntent(Intent intent) {
    }

    public static void onWindowFocusChanged(boolean z) {
    }

    public static void onConfigurationChanged(Configuration configuration) {
    }

    public static void onActivityResult(int i, int i2, Intent intent) {
        String str = MIDlet.cnZ;
        if (MIDlet.cnS && str != null) {
            if (str.equalsIgnoreCase("USE_IAP_GOOGLE_BILLING") || str.equalsIgnoreCase("USE_IAP_BAZAAR_BILLING") || str.equalsIgnoreCase("USE_IAP_YANDEX_BILLING")) {
                String str2 = "onActivityResult(" + i + "," + i2 + "," + intent;
                if (!com.gameloft.android2d.iap.b.a(i, i2, intent)) {
                }
            }
        }
    }
}
