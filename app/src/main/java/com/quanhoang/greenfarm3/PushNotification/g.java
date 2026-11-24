package com.quanhoang.greenfarm3.PushNotification;

import android.util.Log;

/* JADX INFO: compiled from: GCMLogger.java */
/* JADX INFO: loaded from: classes.dex */
class g {
    private final String Fo;
    private final String mTag;

    g(String str, String str2) {
        this.mTag = str;
        this.Fo = str2;
    }

    protected void a(int i, String str, Object... objArr) {
        if (Log.isLoggable(this.mTag, i)) {
            String str2 = String.format(str, objArr);
            String str3 = this.mTag;
            String str4 = this.Fo + str2;
        }
    }
}
