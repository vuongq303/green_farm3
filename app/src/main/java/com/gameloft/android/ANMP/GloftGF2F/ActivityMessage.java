package com.gameloft.android.ANMP.GloftGF2F;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import com.unity3d.services.ads.adunit.AdUnitActivity;

/* JADX INFO: loaded from: classes.dex */
public class ActivityMessage extends Activity {
    public static String Af = null;
    public static AlertDialog Ag = null;
    public static String Ai = null;
    public Activity Ah = null;
    private byte[] Ae = {69, 88, 73, 84};

    public void fK() {
        try {
            Ag = new AlertDialog.Builder(this.Ah).create();
            AlertDialog alertDialog = Ag;
            alertDialog.setButton("OK", new a(this));
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setOnKeyListener(new b(this));
            alertDialog.setMessage(Af);
            alertDialog.setCancelable(true);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.Ah = this;
        Bundle extras = getIntent().getExtras();
        Af = extras.getString("warning_message");
        Ai = extras.getString(AdUnitActivity.EXTRA_ORIENTATION);
        if (Ai != null) {
            if (Ai.compareTo("portrait") == 0) {
                setRequestedOrientation(1);
            } else {
                setRequestedOrientation(6);
            }
        }
        fK();
    }

    @Override // android.app.Activity
    public void onPause() {
        super.onPause();
        finish();
        onDestroy();
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }
}
