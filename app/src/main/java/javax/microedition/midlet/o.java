package javax.microedition.midlet;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import com.gameloft.adsmanager.AdsManager;
import com.gameloft.android.wrapper.au;
import com.gameloft.glads.GLAdsV2;

/* JADX INFO: compiled from: MIDlet_Child_AdsManager.java */
/* JADX INFO: loaded from: classes.dex */
public class o {
    static {
        System.loadLibrary("AdsManager");
    }

    public static void onCreate(Bundle bundle) {
        GLAdsV2.SetActivity(au.getActivity());
        GLAdsV2.SetParentView(au.OZ());
        AdsManager.Init(au.getActivity(), au.OZ());
    }

    public static void onDestroy() {
    }

    public static void onPause() {
        AdsManager.OnPause();
    }

    public static void onSaveInstanceState(Bundle bundle) {
    }

    public static void onRestart() {
    }

    public static void onResume() {
        AdsManager.OnResume();
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
        GLAdsV2.onActivityResult(i, i2, intent);
    }
}
