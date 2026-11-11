package com.google.android.gms.signin.internal;

import android.accounts.Account;
import android.os.IInterface;
import com.google.android.gms.common.internal.AuthAccountRequest;
import com.google.android.gms.common.internal.ResolveAccountRequest;
import com.google.android.gms.common.internal.ah;
import com.google.android.gms.common.internal.az;

/* JADX INFO: loaded from: classes.dex */
public interface g extends IInterface {
    void a(int i, Account account, d dVar);

    void a(AuthAccountRequest authAccountRequest, d dVar);

    void a(ResolveAccountRequest resolveAccountRequest, az azVar);

    void a(ah ahVar, int i, boolean z);

    void a(CheckServerAuthResult checkServerAuthResult);

    void a(RecordConsentRequest recordConsentRequest, d dVar);

    void a(SignInRequest signInRequest, d dVar);

    void b(d dVar);

    void cd(boolean z);

    void ce(boolean z);

    void ot(int i);
}
