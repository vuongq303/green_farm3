package javax.microedition.midlet;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import com.gameloft.android.wrapper.au;

/* JADX INFO: compiled from: MIDlet_Child_SoftKeyboard.java */
/* JADX INFO: loaded from: classes.dex */
public class w {
    public static void onCreate(Bundle bundle) {
        com.gameloft.android.wrapper.z.k(au.getActivity());
    }

    public static void onDestroy() {
    }

    public static void onPause() {
        if (com.gameloft.android2d.f.d.bsa != null) {
            com.gameloft.android2d.f.d.bsa.hideSoftInputFromWindow(com.gameloft.android2d.f.d.brY.getWindowToken(), 0);
        }
    }

    public static void onSaveInstanceState(Bundle bundle) {
    }

    public static void onRestart() {
    }

    public static void onResume() {
        if (com.gameloft.android2d.f.d.bsa != null) {
            com.gameloft.android2d.f.d.bsa.hideSoftInputFromWindow(com.gameloft.android2d.f.d.brY.getWindowToken(), 0);
        }
        com.gameloft.android.wrapper.z.k(au.getActivity());
    }

    public static void onStart() {
    }

    static void onStop() {
        if (com.gameloft.android2d.f.d.bsa != null) {
            com.gameloft.android2d.f.d.bsa.hideSoftInputFromWindow(com.gameloft.android2d.f.d.brY.getWindowToken(), 0);
        }
    }

    public static void onNewIntent(Intent intent) {
    }

    public static void onWindowFocusChanged(boolean z) {
        com.gameloft.android.wrapper.z.k(au.getActivity());
    }

    public static void onConfigurationChanged(Configuration configuration) {
    }

    public static void onActivityResult(int i, int i2, Intent intent) {
    }
}
