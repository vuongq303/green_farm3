package com.google.android.gms.signin.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.z;
import com.google.android.gms.common.internal.ResolveAccountRequest;
import com.google.android.gms.common.internal.ah;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.internal.r;
import com.google.android.gms.common.internal.y;

/* JADX INFO: loaded from: classes.dex */
public class k extends y<g> implements com.google.android.gms.signin.c {
    private Integer bNH;
    private final boolean bYA;
    private final Bundle bYj;
    private final r zzgf;

    public k(Context context, Looper looper, boolean z, r rVar, Bundle bundle, com.google.android.gms.common.api.y yVar, z zVar) {
        super(context, looper, 44, rVar, yVar, zVar);
        this.bYA = z;
        this.zzgf = rVar;
        this.bYj = bundle;
        this.bNH = rVar.aam();
    }

    public k(Context context, Looper looper, boolean z, r rVar, com.google.android.gms.signin.d dVar, com.google.android.gms.common.api.y yVar, z zVar) {
        this(context, looper, z, rVar, a(rVar), yVar, zVar);
    }

    public static Bundle a(r rVar) {
        com.google.android.gms.signin.d dVarAal = rVar.aal();
        Integer numAam = rVar.aam();
        Bundle bundle = new Bundle();
        bundle.putParcelable("com.google.android.gms.signin.internal.clientRequestedAccount", rVar.getAccount());
        if (numAam != null) {
            bundle.putInt("com.google.android.gms.common.internal.ClientSettings.sessionId", numAam.intValue());
        }
        if (dVarAal != null) {
            bundle.putBoolean("com.google.android.gms.signin.internal.offlineAccessRequested", dVarAal.agd());
            bundle.putBoolean("com.google.android.gms.signin.internal.idTokenRequested", dVarAal.XS());
            bundle.putString("com.google.android.gms.signin.internal.serverClientId", dVarAal.XT());
            bundle.putBoolean("com.google.android.gms.signin.internal.usePromptModeForAuthCode", true);
            bundle.putBoolean("com.google.android.gms.signin.internal.forceCodeForRefreshToken", dVarAal.Yj());
            bundle.putString("com.google.android.gms.signin.internal.hostedDomain", dVarAal.Yk());
            bundle.putBoolean("com.google.android.gms.signin.internal.waitForAccessTokenRefresh", dVarAal.age());
            if (dVarAal.agf() != null) {
                bundle.putLong("com.google.android.gms.signin.internal.authApiSignInModuleVersion", dVarAal.agf().longValue());
            }
            if (dVarAal.agg() != null) {
                bundle.putLong("com.google.android.gms.signin.internal.realClientLibraryVersion", dVarAal.agg().longValue());
            }
        }
        return bundle;
    }

    @Override // com.google.android.gms.signin.c
    public void a(ah ahVar, boolean z) {
        try {
            ((g) getService()).a(ahVar, this.bNH.intValue(), z);
        } catch (RemoteException e) {
            Log.w("SignInClientImpl", "Remote service probably died when saveDefaultAccount is called");
        }
    }

    @Override // com.google.android.gms.signin.c
    public void a(d dVar) {
        bf.d(dVar, "Expecting a valid ISignInCallbacks");
        try {
            Account accountOrDefault = this.zzgf.getAccountOrDefault();
            ((g) getService()).a(new SignInRequest(new ResolveAccountRequest(accountOrDefault, this.bNH.intValue(), com.google.android.gms.common.internal.e.DEFAULT_ACCOUNT.equals(accountOrDefault.name) ? com.google.android.gms.auth.api.signin.internal.d.ay(getContext()).Yt() : null)), dVar);
        } catch (RemoteException e) {
            Log.w("SignInClientImpl", "Remote service probably died when signIn is called");
            try {
                dVar.b(new SignInResponse(8));
            } catch (RemoteException e2) {
                Log.wtf("SignInClientImpl", "ISignInCallbacks#onSignInComplete should be executed from the same process, unexpected RemoteException.", e);
            }
        }
    }

    @Override // com.google.android.gms.signin.c
    public void agc() {
        try {
            ((g) getService()).ot(this.bNH.intValue());
        } catch (RemoteException e) {
            Log.w("SignInClientImpl", "Remote service probably died when clearAccountFromSessionStore is called");
        }
    }

    @Override // com.google.android.gms.signin.c
    public void connect() {
        connect(new com.google.android.gms.common.internal.l(this));
    }

    @Override // com.google.android.gms.common.internal.e
    protected Bundle getGetServiceRequestExtraArgs() {
        if (!getContext().getPackageName().equals(this.zzgf.aai())) {
            this.bYj.putString("com.google.android.gms.signin.internal.realClientPackageName", this.zzgf.aai());
        }
        return this.bYj;
    }

    @Override // com.google.android.gms.common.internal.y, com.google.android.gms.common.internal.e, com.google.android.gms.common.api.l
    public int getMinApkVersion() {
        return 12451000;
    }

    @Override // com.google.android.gms.common.internal.e
    protected String getServiceDescriptor() {
        return "com.google.android.gms.signin.internal.ISignInService";
    }

    @Override // com.google.android.gms.common.internal.e
    protected String getStartServiceAction() {
        return "com.google.android.gms.signin.service.START";
    }

    @Override // com.google.android.gms.common.internal.e, com.google.android.gms.common.api.l
    public boolean requiresSignIn() {
        return this.bYA;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.common.internal.e
    /* JADX INFO: renamed from: u, reason: merged with bridge method [inline-methods] */
    public g createServiceInterface(IBinder iBinder) {
        return h.t(iBinder);
    }
}
