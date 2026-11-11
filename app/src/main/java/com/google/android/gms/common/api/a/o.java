package com.google.android.gms.common.api.a;

import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Scope;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
final class o implements cg, com.google.android.gms.common.internal.i {
    final /* synthetic */ l bJC;
    private com.google.android.gms.common.internal.ah bJE = null;
    private Set<Scope> bJF = null;
    private boolean bJG = false;
    private final com.google.android.gms.common.api.l bJs;
    private final cv<?> bJu;

    public o(l lVar, com.google.android.gms.common.api.l lVar2, cv<?> cvVar) {
        this.bJC = lVar;
        this.bJs = lVar2;
        this.bJu = cvVar;
    }

    static /* synthetic */ boolean a(o oVar, boolean z) {
        oVar.bJG = true;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzbu() {
        if (!this.bJG || this.bJE == null) {
            return;
        }
        this.bJs.getRemoteService(this.bJE, this.bJF);
    }

    @Override // com.google.android.gms.common.api.a.cg
    public final void a(ConnectionResult connectionResult) {
        ((m) this.bJC.bJn.get(this.bJu)).a(connectionResult);
    }

    @Override // com.google.android.gms.common.api.a.cg
    public final void a(com.google.android.gms.common.internal.ah ahVar, Set<Scope> set) {
        if (ahVar == null || set == null) {
            Log.wtf("GoogleApiManager", "Received null response from onSignInSuccess", new Exception());
            a(new ConnectionResult(4));
        } else {
            this.bJE = ahVar;
            this.bJF = set;
            zzbu();
        }
    }

    @Override // com.google.android.gms.common.internal.i
    public final void d(ConnectionResult connectionResult) {
        this.bJC.handler.post(new bs(this, connectionResult));
    }
}
