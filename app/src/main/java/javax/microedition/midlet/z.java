package javax.microedition.midlet;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import com.gameloft.android.ANMP.GloftGF2F.bz;

/* JADX INFO: compiled from: MIDlet_Child_Zoro.java */
/* JADX INFO: loaded from: classes.dex */
public class z {
    public static void onCreate(Bundle bundle) {
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
        bz bzVarOn = bz.On();
        if (bzVarOn.Oo()) {
            bzVarOn.Op();
        } else {
            bzVarOn.init();
        }
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
