package com.google.android.gms.internal.ads;

import android.content.Context;
import com.google.android.gms.common.d.t;
import java.util.Map;
import javax.annotation.ParametersAreNonnullByDefault;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzuw implements zzuo, zzuu {
    private final Context mContext;
    private final zzaqw zzbnd;

    public zzuw(Context context, zzang zzangVar, zzci zzciVar, com.google.android.gms.ads.internal.zzw zzwVar) {
        this.mContext = context;
        com.google.android.gms.ads.internal.zzbv.zzel();
        this.zzbnd = zzarc.zza(context, zzasi.zzvq(), "", false, false, zzciVar, zzangVar, null, null, null, zzhs.zzhm());
        this.zzbnd.getView().setWillNotDraw(true);
    }

    private static void runOnUiThread(Runnable runnable) {
        zzkb.zzif();
        if (zzamu.zzsh()) {
            runnable.run();
        } else {
            zzakk.zzcrm.post(runnable);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzuu
    public final void destroy() {
        this.zzbnd.destroy();
    }

    @Override // com.google.android.gms.internal.ads.zzuu
    public final void zza(zzuv zzuvVar) {
        zzasc zzascVarZzuf = this.zzbnd.zzuf();
        zzuvVar.getClass();
        zzascVarZzuf.zza(zzuz.zzb(zzuvVar));
    }

    @Override // com.google.android.gms.internal.ads.zzwb
    public final void zza(String str, com.google.android.gms.ads.internal.gmsg.zzv<? super zzwb> zzvVar) {
        this.zzbnd.zza(str, new zzvd(this, zzvVar));
    }

    @Override // com.google.android.gms.internal.ads.zzue
    public final void zza(String str, Map map) {
        zzup.zza(this, str, map);
    }

    @Override // com.google.android.gms.internal.ads.zzuo, com.google.android.gms.internal.ads.zzue
    public final void zza(String str, JSONObject jSONObject) {
        zzup.zzb(this, str, jSONObject);
    }

    @Override // com.google.android.gms.internal.ads.zzwb
    public final void zzb(String str, final com.google.android.gms.ads.internal.gmsg.zzv<? super zzwb> zzvVar) {
        this.zzbnd.zza(str, new t(zzvVar) { // from class: com.google.android.gms.internal.ads.zzuy
            private final com.google.android.gms.ads.internal.gmsg.zzv zzbps;

            {
                this.zzbps = zzvVar;
            }

            @Override // com.google.android.gms.common.d.t
            public final boolean apply(Object obj) {
                com.google.android.gms.ads.internal.gmsg.zzv zzvVar2 = (com.google.android.gms.ads.internal.gmsg.zzv) obj;
                return (zzvVar2 instanceof zzvd) && ((zzvd) zzvVar2).zzbpw.equals(this.zzbps);
            }
        });
    }

    @Override // com.google.android.gms.internal.ads.zzve
    public final void zzb(String str, JSONObject jSONObject) {
        zzup.zza(this, str, jSONObject);
    }

    @Override // com.google.android.gms.internal.ads.zzuu
    public final void zzbb(String str) {
        runOnUiThread(new zzva(this, String.format("<!DOCTYPE html><html><head><script src=\"%s\"></script></head><body></body></html>", str)));
    }

    @Override // com.google.android.gms.internal.ads.zzuu
    public final void zzbc(String str) {
        runOnUiThread(new zzvb(this, str));
    }

    @Override // com.google.android.gms.internal.ads.zzuu
    public final void zzbd(String str) {
        runOnUiThread(new zzvc(this, str));
    }

    @Override // com.google.android.gms.internal.ads.zzuo, com.google.android.gms.internal.ads.zzve
    public final void zzbe(final String str) {
        runOnUiThread(new Runnable(this, str) { // from class: com.google.android.gms.internal.ads.zzux
            private final zzuw zzbpr;
            private final String zzzo;

            {
                this.zzbpr = this;
                this.zzzo = str;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.zzbpr.zzbi(this.zzzo);
            }
        });
    }

    final /* synthetic */ void zzbi(String str) {
        this.zzbnd.zzbe(str);
    }

    @Override // com.google.android.gms.internal.ads.zzuo
    public final void zzf(String str, String str2) {
        zzup.zza(this, str, str2);
    }

    @Override // com.google.android.gms.internal.ads.zzuu
    public final zzwc zzlw() {
        return new zzwd(this);
    }
}
