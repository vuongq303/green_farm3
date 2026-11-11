package com.google.android.gms.ads.internal;

import android.os.RemoteException;
import com.google.android.gms.internal.ads.zzabl;
import com.google.android.gms.internal.ads.zzait;
import com.google.android.gms.internal.ads.zzaji;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzakk;
import com.google.android.gms.internal.ads.zzaqw;
import com.google.android.gms.internal.ads.zzarg;
import com.google.android.gms.internal.ads.zznx;
import com.google.android.gms.internal.ads.zzny;

/* JADX INFO: loaded from: classes.dex */
final class am implements Runnable {
    final /* synthetic */ zzi bDt;
    final /* synthetic */ zzait bDu;
    private final /* synthetic */ zznx bDv;
    final /* synthetic */ zzaji zzwg;

    am(zzi zziVar, zzaji zzajiVar, zzait zzaitVar, zznx zznxVar) {
        this.bDt = zziVar;
        this.zzwg = zzajiVar;
        this.bDu = zzaitVar;
        this.bDv = zznxVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zzwg.zzcos.zzcez && this.bDt.zzvw.bDj != null) {
            String strZzcu = null;
            if (this.zzwg.zzcos.zzbyq != null) {
                zzbv.zzek();
                strZzcu = zzakk.zzcu(this.zzwg.zzcos.zzbyq);
            }
            zzny zznyVar = new zzny(this.bDt, strZzcu, this.zzwg.zzcos.zzceo);
            this.bDt.zzvw.zzadv = 1;
            try {
                this.bDt.zzvu = false;
                this.bDt.zzvw.bDj.zza(zznyVar);
                return;
            } catch (RemoteException e) {
                zzakb.zzd("#007 Could not call remote method.", e);
                this.bDt.zzvu = true;
            }
        }
        zzx zzxVar = new zzx(this.bDt.zzvw.zzrt, this.bDu, this.zzwg.zzcos.zzcfi);
        try {
            zzaqw zzaqwVarA = this.bDt.a(this.zzwg, zzxVar, this.bDu);
            zzaqwVarA.setOnTouchListener(new ao(this, zzxVar));
            zzaqwVarA.setOnClickListener(new ap(this, zzxVar));
            this.bDt.zzvw.zzadv = 0;
            zzbw zzbwVar = this.bDt.zzvw;
            zzbv.zzej();
            zzbwVar.zzacu = zzabl.zza(this.bDt.zzvw.zzrt, this.bDt, this.zzwg, this.bDt.zzvw.bCV, zzaqwVarA, this.bDt.zzwh, this.bDt, this.bDv);
        } catch (zzarg e2) {
            zzakb.zzb("Could not obtain webview.", e2);
            zzakk.zzcrm.post(new an(this));
        }
    }
}
