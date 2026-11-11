package javax.microedition.midlet;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import com.gameloft.android.ANMP.GloftGF2F.installer.IReferrerReceiver;
import com.gameloft.android.wrapper.ar;
import com.gameloft.android.wrapper.au;

/* JADX INFO: compiled from: MIDlet_Child_USE_INSTALL_REFERRER.java */
/* JADX INFO: loaded from: classes.dex */
public class x {
    public static void onCreate(Bundle bundle) {
        String str = Environment.getExternalStorageDirectory() + "/Android/obb/com.gameloft.android.ANMP.GloftGF2F";
        boolean zW = au.W(("/sdcard/gameloft/games//") + "qaTestingConfigs.txt", "INSTALL_REFERRER_TEST");
        if (!zW) {
            zW = au.W(str + "/qaTestingConfigs.txt", "INSTALL_REFERRER_TEST");
        }
        if (zW) {
            IReferrerReceiver.am(MIDlet.cnz);
        }
        ar.a(MIDlet.cnz);
        IReferrerReceiver.Oq();
    }

    public static void onNewIntent(Intent intent) {
    }

    public static void onDestroy() {
    }

    public static void onPause() {
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
