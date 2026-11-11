package com.google.android.gms.internal.measurement;

import android.app.Activity;
import android.content.Intent;
import com.google.android.gms.appinvite.d;
import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
final class zzdn extends zzdg {
    private final /* synthetic */ zzdm zzacn;

    zzdn(zzdm zzdmVar) {
        this.zzacn = zzdmVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzdg, com.google.android.gms.internal.measurement.zzdq
    public final void zza(Status status, Intent intent) {
        Activity activity;
        this.zzacn.setResult(new zzdp(status, intent));
        if (!d.j(intent) || !this.zzacn.zzacl || this.zzacn.zzack == null || (activity = (Activity) this.zzacn.zzack.get()) == null) {
            return;
        }
        activity.startActivity(intent);
    }
}
