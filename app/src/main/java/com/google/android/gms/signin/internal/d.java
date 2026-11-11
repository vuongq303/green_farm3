package com.google.android.gms.signin.internal;

import android.os.IInterface;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
public interface d extends IInterface {
    void a(ConnectionResult connectionResult, AuthAccountResult authAccountResult);

    void a(Status status, GoogleSignInAccount googleSignInAccount);

    void b(SignInResponse signInResponse);

    void f(Status status);

    void g(Status status);
}
