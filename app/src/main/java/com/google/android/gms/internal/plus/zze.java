package com.google.android.gms.internal.plus;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.aa;
import com.google.android.gms.common.api.w;
import com.google.android.gms.plus.a;
import com.google.android.gms.plus.d;
import com.google.android.gms.plus.internal.f;

/* JADX INFO: loaded from: classes.dex */
public final class zze implements a {
    @Override // com.google.android.gms.plus.a
    public final void clearDefaultAccount(w wVar) {
        f fVarC = d.c(wVar, false);
        if (fVarC != null) {
            fVarC.zza();
        }
    }

    @Override // com.google.android.gms.plus.a
    public final String getAccountName(w wVar) {
        return d.c(wVar, true).aad();
    }

    public final aa<Status> revokeAccessAndDisconnect(w wVar) {
        return wVar.b(new zzf(this, wVar));
    }
}
