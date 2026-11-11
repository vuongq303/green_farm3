package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

/* JADX INFO: loaded from: classes.dex */
public final class i {
    private static i bHp = null;
    private d bHq;
    private GoogleSignInAccount bHr;
    private GoogleSignInOptions bHs;

    private i(Context context) {
        this.bHq = d.ay(context);
        this.bHr = this.bHq.Yt();
        this.bHs = this.bHq.Yu();
    }

    private static synchronized i aA(Context context) {
        if (bHp == null) {
            bHp = new i(context);
        }
        return bHp;
    }

    public static synchronized i az(Context context) {
        return aA(context.getApplicationContext());
    }

    public final synchronized void a(GoogleSignInOptions googleSignInOptions, GoogleSignInAccount googleSignInAccount) {
        this.bHq.a(googleSignInAccount, googleSignInOptions);
        this.bHr = googleSignInAccount;
        this.bHs = googleSignInOptions;
    }
}
