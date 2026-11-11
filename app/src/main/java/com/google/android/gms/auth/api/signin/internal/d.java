package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.internal.bf;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class d {
    private static final Lock bHh = new ReentrantLock();

    @GuardedBy("sLk")
    private static d bHi;
    private final Lock bHj = new ReentrantLock();

    @GuardedBy("mLk")
    private final SharedPreferences bHk;

    private d(Context context) {
        this.bHk = context.getSharedPreferences("com.google.android.gms.signin", 0);
    }

    private static String au(String str, String str2) {
        return new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(str2).length()).append(str).append(":").append(str2).toString();
    }

    public static d ay(Context context) {
        bf.checkNotNull(context);
        bHh.lock();
        try {
            if (bHi == null) {
                bHi = new d(context.getApplicationContext());
            }
            return bHi;
        } finally {
            bHh.unlock();
        }
    }

    @Nullable
    private final GoogleSignInAccount hP(String str) {
        String strHR;
        if (TextUtils.isEmpty(str) || (strHR = hR(au("googleSignInAccount", str))) == null) {
            return null;
        }
        try {
            return GoogleSignInAccount.hM(strHR);
        } catch (JSONException e) {
            return null;
        }
    }

    @Nullable
    private final GoogleSignInOptions hQ(String str) {
        String strHR;
        if (TextUtils.isEmpty(str) || (strHR = hR(au("googleSignInOptions", str))) == null) {
            return null;
        }
        try {
            return GoogleSignInOptions.hO(strHR);
        } catch (JSONException e) {
            return null;
        }
    }

    @Nullable
    public GoogleSignInAccount Yt() {
        return hP(hR("defaultGoogleSignInAccount"));
    }

    @Nullable
    public GoogleSignInOptions Yu() {
        return hQ(hR("defaultGoogleSignInAccount"));
    }

    public void a(GoogleSignInAccount googleSignInAccount, GoogleSignInOptions googleSignInOptions) {
        bf.checkNotNull(googleSignInAccount);
        bf.checkNotNull(googleSignInOptions);
        at("defaultGoogleSignInAccount", googleSignInAccount.Yd());
        bf.checkNotNull(googleSignInAccount);
        bf.checkNotNull(googleSignInOptions);
        String strYd = googleSignInAccount.Yd();
        at(au("googleSignInAccount", strYd), googleSignInAccount.Yf());
        at(au("googleSignInOptions", strYd), googleSignInOptions.Ym());
    }

    protected void at(String str, String str2) {
        this.bHj.lock();
        try {
            this.bHk.edit().putString(str, str2).apply();
        } finally {
            this.bHj.unlock();
        }
    }

    @Nullable
    protected String hR(String str) {
        this.bHj.lock();
        try {
            return this.bHk.getString(str, null);
        } finally {
            this.bHj.unlock();
        }
    }
}
