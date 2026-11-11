package com.google.android.gms.internal.auth;

import android.accounts.Account;
import com.google.android.gms.auth.a.a;
import com.google.android.gms.auth.a.b;
import com.google.android.gms.auth.a.c;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.aa;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.w;

/* JADX INFO: loaded from: classes.dex */
public final class zzk implements b {
    private static final Status zzaf = new Status(13);

    public final aa<c> addWorkAccount(w wVar, String str) {
        return wVar.b(new zzm(this, a.API, wVar, str));
    }

    public final aa<ae> removeWorkAccount(w wVar, Account account) {
        return wVar.b(new zzo(this, a.API, wVar, account));
    }

    public final void setWorkAuthenticatorEnabled(w wVar, boolean z) {
        setWorkAuthenticatorEnabledWithResult(wVar, z);
    }

    public final aa<ae> setWorkAuthenticatorEnabledWithResult(w wVar, boolean z) {
        return wVar.b(new zzl(this, a.API, wVar, z));
    }
}
