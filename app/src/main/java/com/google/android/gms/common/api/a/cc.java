package com.google.android.gms.common.api.a;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.signin.internal.SignInResponse;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class cc extends com.google.android.gms.signin.internal.b implements com.google.android.gms.common.api.y, com.google.android.gms.common.api.z {
    private static com.google.android.gms.common.api.b<? extends com.google.android.gms.signin.c, com.google.android.gms.signin.d> bLx = com.google.android.gms.signin.a.CLIENT_BUILDER;
    private final com.google.android.gms.common.api.b<? extends com.google.android.gms.signin.c, com.google.android.gms.signin.d> bIq;
    private com.google.android.gms.signin.c bKo;
    private cg bLy;
    private final Context mContext;
    private final Handler mHandler;
    private Set<Scope> mScopes;
    private com.google.android.gms.common.internal.r zzgf;

    public cc(Context context, Handler handler, com.google.android.gms.common.internal.r rVar) {
        this(context, handler, rVar, bLx);
    }

    public cc(Context context, Handler handler, com.google.android.gms.common.internal.r rVar, com.google.android.gms.common.api.b<? extends com.google.android.gms.signin.c, com.google.android.gms.signin.d> bVar) {
        this.mContext = context;
        this.mHandler = handler;
        this.zzgf = (com.google.android.gms.common.internal.r) com.google.android.gms.common.internal.bf.d(rVar, "ClientSettings must not be null");
        this.mScopes = rVar.aaf();
        this.bIq = bVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void c(SignInResponse signInResponse) {
        ConnectionResult connectionResultZM = signInResponse.ZM();
        if (connectionResultZM.Sp()) {
            ResolveAccountResponse resolveAccountResponseAgm = signInResponse.agm();
            ConnectionResult connectionResultZM2 = resolveAccountResponseAgm.ZM();
            if (!connectionResultZM2.Sp()) {
                String strValueOf = String.valueOf(connectionResultZM2);
                Log.wtf("SignInCoordinator", new StringBuilder(String.valueOf(strValueOf).length() + 48).append("Sign-in succeeded with resolve account failure: ").append(strValueOf).toString(), new Exception());
                this.bLy.a(connectionResultZM2);
                this.bKo.disconnect();
                return;
            }
            this.bLy.a(resolveAccountResponseAgm.aaC(), this.mScopes);
        } else {
            this.bLy.a(connectionResultZM);
        }
        this.bKo.disconnect();
    }

    public final void ZG() {
        if (this.bKo != null) {
            this.bKo.disconnect();
        }
    }

    public final com.google.android.gms.signin.c Zg() {
        return this.bKo;
    }

    public final void a(cg cgVar) {
        if (this.bKo != null) {
            this.bKo.disconnect();
        }
        this.zzgf.c(Integer.valueOf(System.identityHashCode(this)));
        this.bKo = (com.google.android.gms.signin.c) this.bIq.a(this.mContext, this.mHandler.getLooper(), this.zzgf, this.zzgf.aal(), this, this);
        this.bLy = cgVar;
        if (this.mScopes == null || this.mScopes.isEmpty()) {
            this.mHandler.post(new cd(this));
        } else {
            this.bKo.connect();
        }
    }

    @Override // com.google.android.gms.signin.internal.b, com.google.android.gms.signin.internal.d
    public final void b(SignInResponse signInResponse) {
        this.mHandler.post(new cf(this, signInResponse));
    }

    @Override // com.google.android.gms.common.api.y
    public final void onConnected(Bundle bundle) {
        this.bKo.a(this);
    }

    @Override // com.google.android.gms.common.api.z
    public final void onConnectionFailed(ConnectionResult connectionResult) {
        this.bLy.a(connectionResult);
    }

    @Override // com.google.android.gms.common.api.y
    public final void onConnectionSuspended(int i) {
        this.bKo.disconnect();
    }
}
