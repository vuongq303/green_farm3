package com.google.android.gms.ads.internal;

import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzjj;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
final class z implements Runnable {
    private final /* synthetic */ WeakReference bCb;
    private final /* synthetic */ zzbl bCc;

    z(zzbl zzblVar, WeakReference weakReference) {
        this.bCc = zzblVar;
        this.bCb = weakReference;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzbl.a(this.bCc, false);
        zza zzaVar = (zza) this.bCb.get();
        if (zzaVar != null) {
            zzjj zzjjVar = this.bCc.zzaao;
            if (zzaVar.zzc(zzjjVar)) {
                zzaVar.zzb(zzjjVar);
            } else {
                zzakb.zzdj("Ad is not visible. Not refreshing ad.");
                zzaVar.zzvv.zzg(zzjjVar);
            }
        }
    }
}
