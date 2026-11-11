package com.google.android.gms.internal.measurement;

import android.app.Activity;
import android.content.Intent;
import com.google.android.gms.appinvite.c;
import com.google.android.gms.appinvite.d;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.api.w;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
final class zzdm extends zzdh<c> {
    private final WeakReference<Activity> zzack;
    private final boolean zzacl;
    private final Intent zzacm;

    public zzdm(zzdf zzdfVar, w wVar, Activity activity, boolean z) {
        super(wVar);
        this.zzacl = z;
        this.zzack = new WeakReference<>(activity);
        this.zzacm = activity != null ? activity.getIntent() : null;
    }

    @Override // com.google.android.gms.common.api.a.g
    protected final /* synthetic */ ae createFailedResult(Status status) {
        return new zzdp(status, new Intent());
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(com.google.android.gms.common.api.c cVar) {
        zzdo zzdoVar = (zzdo) cVar;
        if (!d.j(this.zzacm)) {
            zzdoVar.zza(new zzdn(this));
        } else {
            setResult(new zzdp(Status.bIO, this.zzacm));
            zzdoVar.zza((zzdq) null);
        }
    }
}
