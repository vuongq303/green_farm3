package com.google.android.gms.internal.ads;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.internal.f;
import java.util.concurrent.Future;

/* JADX INFO: loaded from: classes.dex */
final class zzso implements f {
    final /* synthetic */ zzsm zzbnn;
    private final /* synthetic */ zzaoj zzbno;
    private final /* synthetic */ zzsg zzbnp;

    zzso(zzsm zzsmVar, zzaoj zzaojVar, zzsg zzsgVar) {
        this.zzbnn = zzsmVar;
        this.zzbno = zzaojVar;
        this.zzbnp = zzsgVar;
    }

    @Override // com.google.android.gms.common.internal.f
    public final void onConnected(Bundle bundle) {
        synchronized (this.zzbnn.mLock) {
            if (this.zzbnn.zzbnm) {
                return;
            }
            zzsm.zza(this.zzbnn, true);
            final zzsf zzsfVar = this.zzbnn.zzbnl;
            if (zzsfVar == null) {
                return;
            }
            final zzaoj zzaojVar = this.zzbno;
            final zzsg zzsgVar = this.zzbnp;
            final zzanz<?> zzanzVarZzb = zzaki.zzb(new Runnable(this, zzsfVar, zzaojVar, zzsgVar) { // from class: com.google.android.gms.internal.ads.zzsp
                private final zzso zzbnq;
                private final zzsf zzbnr;
                private final zzaoj zzbns;
                private final zzsg zzbnt;

                {
                    this.zzbnq = this;
                    this.zzbnr = zzsfVar;
                    this.zzbns = zzaojVar;
                    this.zzbnt = zzsgVar;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    zzso zzsoVar = this.zzbnq;
                    zzsf zzsfVar2 = this.zzbnr;
                    zzaoj zzaojVar2 = this.zzbns;
                    try {
                        zzaojVar2.set(zzsfVar2.zzlb().zza(this.zzbnt));
                    } catch (RemoteException e) {
                        zzakb.zzb("Unable to obtain a cache service instance.", e);
                        zzaojVar2.setException(e);
                        zzsoVar.zzbnn.disconnect();
                    }
                }
            });
            zzaoj zzaojVar2 = this.zzbno;
            final zzaoj zzaojVar3 = this.zzbno;
            zzaojVar2.zza(new Runnable(zzaojVar3, zzanzVarZzb) { // from class: com.google.android.gms.internal.ads.zzsq
                private final zzaoj zzbnu;
                private final Future zzbnv;

                {
                    this.zzbnu = zzaojVar3;
                    this.zzbnv = zzanzVarZzb;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    zzaoj zzaojVar4 = this.zzbnu;
                    Future future = this.zzbnv;
                    if (zzaojVar4.isCancelled()) {
                        future.cancel(true);
                    }
                }
            }, zzaoe.zzcvz);
        }
    }

    @Override // com.google.android.gms.common.internal.f
    public final void onConnectionSuspended(int i) {
    }
}
