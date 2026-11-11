package com.google.android.gms.common.api.a;

import android.app.Activity;
import com.google.android.gms.common.ConnectionResult;

/* JADX INFO: loaded from: classes.dex */
public class ak extends cz {
    private final android.support.v4.e.c<cv<?>> bKb;
    private l zzcq;

    private ak(u uVar) {
        super(uVar);
        this.bKb = new android.support.v4.e.c<>();
        this.bJO.a("ConnectionlessLifecycleHelper", this);
    }

    private final void Zt() {
        if (this.bKb.isEmpty()) {
            return;
        }
        this.zzcq.a(this);
    }

    public static void a(Activity activity, l lVar, cv<?> cvVar) {
        u uVarP = p(activity);
        ak akVar = (ak) uVarP.a("ConnectionlessLifecycleHelper", ak.class);
        if (akVar == null) {
            akVar = new ak(uVarP);
        }
        akVar.zzcq = lVar;
        com.google.android.gms.common.internal.bf.d(cvVar, "ApiKey cannot be null");
        akVar.bKb.add(cvVar);
        lVar.a(akVar);
    }

    @Override // com.google.android.gms.common.api.a.cz
    protected final void YX() {
        this.zzcq.YX();
    }

    final android.support.v4.e.c<cv<?>> Zs() {
        return this.bKb;
    }

    @Override // com.google.android.gms.common.api.a.cz
    protected final void b(ConnectionResult connectionResult, int i) {
        this.zzcq.b(connectionResult, i);
    }

    @Override // com.google.android.gms.common.api.a.t
    public final void onResume() {
        super.onResume();
        Zt();
    }

    @Override // com.google.android.gms.common.api.a.cz, com.google.android.gms.common.api.a.t
    public final void onStart() {
        super.onStart();
        Zt();
    }

    @Override // com.google.android.gms.common.api.a.cz, com.google.android.gms.common.api.a.t
    public void onStop() {
        super.onStop();
        this.zzcq.b(this);
    }
}
