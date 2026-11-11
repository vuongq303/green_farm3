package com.gameloft.android.ANMP.GloftGF2F.installer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import java.net.URI;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes.dex */
public class IReferrerReceiver extends BroadcastReceiver {
    private static final ReentrantLock beB = new ReentrantLock();

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String strDZ = dZ(intent.getStringExtra("referrer"));
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("rsend_referrer", 0).edit();
        if (strDZ != null) {
            editorEdit.putString("referrer", strDZ);
            editorEdit.commit();
        } else {
            editorEdit.putString("referrer", "");
            editorEdit.commit();
        }
    }

    public static String al(Context context) {
        return context != null ? context.getSharedPreferences("rsend_referrer", 0).getString("referrer", "") : "";
    }

    private String dZ(String str) {
        if (str == null) {
            return "";
        }
        String str2 = "";
        try {
            str2 = new URI(str).getQuery().split("referrer=")[1];
        } catch (Exception e) {
        }
        if (str2.equals("")) {
            return (str.contains("utm_source") || str.indexOf("utm_source") > -1) ? str : str2;
        }
        return str2;
    }

    public static void am(Context context) {
        Intent intent = new Intent("com.android.vending.INSTALL_REFERRER");
        intent.setPackage(context.getPackageName());
        intent.putExtra("referrer", "utm_source=source+test&utm_medium=medium+test&utm_term=term+test&utm_content=content+test&utm_campaign=campaign+test");
        context.sendBroadcast(intent);
    }

    public static void Oq() {
        new Thread(new a()).start();
    }
}
