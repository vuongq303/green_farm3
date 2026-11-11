package com.google.android.gms.appinvite;

import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public class d {
    public static boolean j(Intent intent) {
        return (intent == null || intent.getBundleExtra("com.google.android.gms.appinvite.REFERRAL_BUNDLE") == null) ? false : true;
    }
}
