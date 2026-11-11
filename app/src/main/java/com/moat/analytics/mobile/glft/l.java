package com.moat.analytics.mobile.glft;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
class l extends Exception {
    l() {
    }

    static void j(Exception exc) {
        if (u.ajL().b()) {
            Log.e("MoatException", Log.getStackTraceString(exc));
        }
    }
}
