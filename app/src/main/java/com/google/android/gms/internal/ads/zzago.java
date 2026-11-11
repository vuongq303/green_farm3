package com.google.android.gms.internal.ads;

import android.content.Context;
import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.gms.c.d;
import com.google.android.gms.common.internal.bf;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzago {
    private static final zzxm zzcku = new zzxm();
    private final zzxn zzckv;
    private final com.google.android.gms.ads.internal.zzbw zzckw;
    private final Map<String, zzaib> zzckx = new HashMap();
    private final zzahu zzcky;
    private final com.google.android.gms.ads.internal.gmsg.zzb zzckz;
    private final zzabm zzcla;

    public zzago(com.google.android.gms.ads.internal.zzbw zzbwVar, zzxn zzxnVar, zzahu zzahuVar, com.google.android.gms.ads.internal.gmsg.zzb zzbVar, zzabm zzabmVar) {
        this.zzckw = zzbwVar;
        this.zzckv = zzxnVar;
        this.zzcky = zzahuVar;
        this.zzckz = zzbVar;
        this.zzcla = zzabmVar;
    }

    public static boolean zza(zzajh zzajhVar, zzajh zzajhVar2) {
        return true;
    }

    public final void destroy() {
        bf.hV("destroy must be called on the main UI thread.");
        Iterator<String> it = this.zzckx.keySet().iterator();
        while (it.hasNext()) {
            try {
                zzaib zzaibVar = this.zzckx.get(it.next());
                if (zzaibVar != null && zzaibVar.zzpe() != null) {
                    zzaibVar.zzpe().destroy();
                }
            } catch (RemoteException e) {
                zzakb.zzd("#007 Could not call remote method.", e);
            }
        }
    }

    public final void onContextChanged(Context context) {
        Iterator<zzaib> it = this.zzckx.values().iterator();
        while (it.hasNext()) {
            try {
                it.next().zzpe().zzi(d.F(context));
            } catch (RemoteException e) {
                zzakb.zzb("Unable to call Adapter.onContextChanged.", e);
            }
        }
    }

    public final void pause() {
        bf.hV("pause must be called on the main UI thread.");
        Iterator<String> it = this.zzckx.keySet().iterator();
        while (it.hasNext()) {
            try {
                zzaib zzaibVar = this.zzckx.get(it.next());
                if (zzaibVar != null && zzaibVar.zzpe() != null) {
                    zzaibVar.zzpe().pause();
                }
            } catch (RemoteException e) {
                zzakb.zzd("#007 Could not call remote method.", e);
            }
        }
    }

    public final void resume() {
        bf.hV("resume must be called on the main UI thread.");
        Iterator<String> it = this.zzckx.keySet().iterator();
        while (it.hasNext()) {
            try {
                zzaib zzaibVar = this.zzckx.get(it.next());
                if (zzaibVar != null && zzaibVar.zzpe() != null) {
                    zzaibVar.zzpe().resume();
                }
            } catch (RemoteException e) {
                zzakb.zzd("#007 Could not call remote method.", e);
            }
        }
    }

    public final zzaib zzca(String str) {
        zzaib zzaibVar;
        zzaib zzaibVar2 = this.zzckx.get(str);
        if (zzaibVar2 != null) {
            return zzaibVar2;
        }
        try {
            zzaibVar = new zzaib(("com.google.ads.mediation.admob.AdMobAdapter".equals(str) ? zzcku : this.zzckv).zzbm(str), this.zzcky);
            try {
                this.zzckx.put(str, zzaibVar);
                return zzaibVar;
            } catch (Exception e) {
                e = e;
                String strValueOf = String.valueOf(str);
                zzakb.zzc(strValueOf.length() != 0 ? "Fail to instantiate adapter ".concat(strValueOf) : new String("Fail to instantiate adapter "), e);
                return zzaibVar;
            }
        } catch (Exception e2) {
            e = e2;
            zzaibVar = zzaibVar2;
        }
    }

    public final zzaig zzd(zzaig zzaigVar) {
        if (this.zzckw.zzacw != null && this.zzckw.zzacw.zzcod != null && !TextUtils.isEmpty(this.zzckw.zzacw.zzcod.zzbsv)) {
            zzaigVar = new zzaig(this.zzckw.zzacw.zzcod.zzbsv, this.zzckw.zzacw.zzcod.zzbsw);
        }
        if (this.zzckw.zzacw != null && this.zzckw.zzacw.zzbtw != null) {
            com.google.android.gms.ads.internal.zzbv.zzfd();
            zzxg.zza(this.zzckw.zzrt, this.zzckw.zzacr.zzcw, this.zzckw.zzacw.zzbtw.zzbsd, this.zzckw.zzadr, zzaigVar);
        }
        return zzaigVar;
    }

    public final com.google.android.gms.ads.internal.gmsg.zzb zzos() {
        return this.zzckz;
    }

    public final zzabm zzot() {
        return this.zzcla;
    }

    public final void zzou() {
        this.zzckw.zzadv = 0;
        com.google.android.gms.ads.internal.zzbw zzbwVar = this.zzckw;
        com.google.android.gms.ads.internal.zzbv.zzej();
        zzahx zzahxVar = new zzahx(this.zzckw.zzrt, this.zzckw.zzacx, this);
        String strValueOf = String.valueOf(zzahxVar.getClass().getName());
        zzakb.zzck(strValueOf.length() != 0 ? "AdRenderer: ".concat(strValueOf) : new String("AdRenderer: "));
        zzahxVar.zznt();
        zzbwVar.zzacu = zzahxVar;
    }

    public final void zzov() {
        if (this.zzckw.zzacw == null || this.zzckw.zzacw.zzbtw == null) {
            return;
        }
        com.google.android.gms.ads.internal.zzbv.zzfd();
        zzxg.zza(this.zzckw.zzrt, this.zzckw.zzacr.zzcw, this.zzckw.zzacw, this.zzckw.zzacp, false, this.zzckw.zzacw.zzbtw.zzbsc);
    }

    public final void zzow() {
        if (this.zzckw.zzacw == null || this.zzckw.zzacw.zzbtw == null) {
            return;
        }
        com.google.android.gms.ads.internal.zzbv.zzfd();
        zzxg.zza(this.zzckw.zzrt, this.zzckw.zzacr.zzcw, this.zzckw.zzacw, this.zzckw.zzacp, false, this.zzckw.zzacw.zzbtw.zzbse);
    }

    public final void zzw(boolean z) {
        zzaib zzaibVarZzca = zzca(this.zzckw.zzacw.zzbty);
        if (zzaibVarZzca == null || zzaibVarZzca.zzpe() == null) {
            return;
        }
        try {
            zzaibVarZzca.zzpe().setImmersiveMode(z);
            zzaibVarZzca.zzpe().showVideo();
        } catch (RemoteException e) {
            zzakb.zzd("#007 Could not call remote method.", e);
        }
    }
}
