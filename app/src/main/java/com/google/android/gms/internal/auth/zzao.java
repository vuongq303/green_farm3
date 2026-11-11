package com.google.android.gms.internal.auth;

import android.app.PendingIntent;
import com.google.android.gms.auth.api.a;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.credentials.b;
import com.google.android.gms.auth.api.credentials.c;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.aa;
import com.google.android.gms.common.api.w;
import com.google.android.gms.common.internal.bf;

/* JADX INFO: loaded from: classes.dex */
public final class zzao implements c {
    public final aa<Status> delete(w wVar, Credential credential) {
        bf.d(wVar, "client must not be null");
        bf.d(credential, "credential must not be null");
        return wVar.b(new zzas(this, wVar, credential));
    }

    public final aa<Status> disableAutoSignIn(w wVar) {
        bf.d(wVar, "client must not be null");
        return wVar.b(new zzat(this, wVar));
    }

    public final PendingIntent getHintPickerIntent(w wVar, HintRequest hintRequest) {
        bf.d(wVar, "client must not be null");
        bf.d(hintRequest, "request must not be null");
        return zzaw.zzd(wVar.getContext(), ((zzax) wVar.a(a.bFa)).zzk(), hintRequest);
    }

    public final aa<b> request(w wVar, CredentialRequest credentialRequest) {
        bf.d(wVar, "client must not be null");
        bf.d(credentialRequest, "request must not be null");
        return wVar.a(new zzap(this, wVar, credentialRequest));
    }

    public final aa<Status> save(w wVar, Credential credential) {
        bf.d(wVar, "client must not be null");
        bf.d(credential, "credential must not be null");
        return wVar.b(new zzar(this, wVar, credential));
    }
}
