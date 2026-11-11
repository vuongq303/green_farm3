package com.google.android.gms.internal.ads;

import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzfb implements zzfo {
    private final zzet zzafq;
    private final zzaqw zzafr;
    private final com.google.android.gms.ads.internal.gmsg.zzv<zzaqw> zzafs = new zzfc(this);
    private final com.google.android.gms.ads.internal.gmsg.zzv<zzaqw> zzaft = new zzfd(this);
    private final com.google.android.gms.ads.internal.gmsg.zzv<zzaqw> zzafu = new zzfe(this);

    public zzfb(zzet zzetVar, zzaqw zzaqwVar) {
        this.zzafq = zzetVar;
        this.zzafr = zzaqwVar;
        zzaqw zzaqwVar2 = this.zzafr;
        zzaqwVar2.zza("/updateActiveView", this.zzafs);
        zzaqwVar2.zza("/untrackActiveViewUnit", this.zzaft);
        zzaqwVar2.zza("/visibilityChanged", this.zzafu);
        String strValueOf = String.valueOf(this.zzafq.zzaet.zzfy());
        zzakb.zzck(strValueOf.length() != 0 ? "Custom JS tracking ad unit: ".concat(strValueOf) : new String("Custom JS tracking ad unit: "));
    }

    @Override // com.google.android.gms.internal.ads.zzfo
    public final void zzb(JSONObject jSONObject, boolean z) {
        if (z) {
            this.zzafq.zzb(this);
        } else {
            this.zzafr.zzb("AFMA_updateActiveView", jSONObject);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzfo
    public final boolean zzgk() {
        return true;
    }

    @Override // com.google.android.gms.internal.ads.zzfo
    public final void zzgl() {
        zzaqw zzaqwVar = this.zzafr;
        zzaqwVar.zzb("/visibilityChanged", this.zzafu);
        zzaqwVar.zzb("/untrackActiveViewUnit", this.zzaft);
        zzaqwVar.zzb("/updateActiveView", this.zzafs);
    }
}
