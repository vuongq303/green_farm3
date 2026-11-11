package javax.microedition.midlet;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import com.gameloft.android.ANMP.GloftGF2F.SplashScreenActivity;
import com.gameloft.android.wrapper.au;

/* JADX INFO: compiled from: MIDlet_Child_WelcomeScreen.java */
/* JADX INFO: loaded from: classes.dex */
public class y {
    public static void onCreate(Bundle bundle) {
        SplashScreenActivity.CL = MIDlet.d(au.getActivity(), "GAME-IGP-CODE");
        SplashScreenActivity.bcz = MIDlet.d(au.getActivity(), "MIDlet-Version");
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

    public static void onNewIntent(Intent intent) {
    }

    public static void onWindowFocusChanged(boolean z) {
    }

    public static void onConfigurationChanged(Configuration configuration) {
    }

    public static void onActivityResult(int i, int i2, Intent intent) {
    }
}
