package com.gameloft.android.ANMP.GloftGF2F;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: InGameBrowserActivity.java */
/* JADX INFO: loaded from: classes.dex */
final class ap extends Thread {
    ap() {
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        try {
            InGameBrowserActivity.a(new JSONObject("" + InGameBrowserActivity.g(InGameBrowserActivity.DE.replace("LAST_ID", InGameBrowserActivity.gM()), InGameBrowserActivity.O(InGameBrowserActivity.Dz))), true);
        } catch (JSONException e) {
        }
        Thread unused = InGameBrowserActivity.En = null;
    }
}
