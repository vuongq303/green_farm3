package com.google.android.gms.internal.games;

import android.os.RemoteException;
import com.google.android.gms.common.api.a.ad;
import com.google.android.gms.common.api.c;
import com.google.android.gms.f.g;
import com.google.android.gms.games.internal.i;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzah<TResult> extends ad<i, TResult> {
    @Override // com.google.android.gms.common.api.a.ad
    protected /* synthetic */ void doExecute(c cVar, g gVar) {
        try {
            zza((i) cVar, gVar);
        } catch (RemoteException | SecurityException e) {
            gVar.h(e);
        }
    }

    protected abstract void zza(i iVar, g<TResult> gVar);
}
