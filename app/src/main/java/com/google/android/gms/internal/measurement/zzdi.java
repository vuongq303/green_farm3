package com.google.android.gms.internal.measurement;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;

/* JADX INFO: loaded from: classes.dex */
final class zzdi extends zzdh<Status> {
    private final String zzach;

    public zzdi(zzdf zzdfVar, w wVar, String str) {
        super(wVar);
        this.zzach = str;
    }

    @Override // com.google.android.gms.common.api.a.g
    protected final /* synthetic */ ae createFailedResult(Status status) {
        return status;
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(c cVar) {
        zzdo zzdoVar = (zzdo) cVar;
        try {
            ((zzds) zzdoVar.getService()).zza(new zzdj(this), this.zzach);
        } catch (RemoteException e) {
        }
    }
}
