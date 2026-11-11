package com.google.android.gms.internal.p001authapiphone;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a.ad;
import com.google.android.gms.common.api.a.af;
import com.google.android.gms.common.api.c;
import com.google.android.gms.f.g;

/* JADX INFO: loaded from: classes.dex */
abstract class zzm extends ad<zzi, Void> {
    private g<Void> zzf;

    private zzm() {
    }

    /* synthetic */ zzm(zzk zzkVar) {
        this();
    }

    @Override // com.google.android.gms.common.api.a.ad
    protected /* synthetic */ void doExecute(c cVar, g<Void> gVar) {
        this.zzf = gVar;
        zza((zze) ((zzi) cVar).getService());
    }

    protected abstract void zza(zze zzeVar);

    protected final void zzb(Status status) {
        af.a(status, this.zzf);
    }
}
