package com.quanhoang.greenfarm3;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: InGameBrowserActivity.java */
/* JADX INFO: loaded from: classes.dex */
class aj extends Thread {
    final /* synthetic */ InGameBrowserActivity Ex;

    aj(InGameBrowserActivity inGameBrowserActivity) {
        this.Ex = inGameBrowserActivity;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        try {
            InGameBrowserActivity.a(new JSONObject("" + InGameBrowserActivity.g(InGameBrowserActivity.Dy + "?ctg=SUPPORT", InGameBrowserActivity.O(InGameBrowserActivity.Dz))), false);
        } catch (JSONException e) {
        }
    }
}
