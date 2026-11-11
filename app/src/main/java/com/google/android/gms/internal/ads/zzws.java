package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzws implements zzaom {
    private final /* synthetic */ zzvs zzbrl;
    private final /* synthetic */ zzaoj zzbrn;

    zzws(zzwq zzwqVar, zzaoj zzaojVar, zzvs zzvsVar) {
        this.zzbrn = zzaojVar;
        this.zzbrl = zzvsVar;
    }

    @Override // com.google.android.gms.internal.ads.zzaom
    public final void run() {
        this.zzbrn.setException(new zzwe("Unable to obtain a JavascriptEngine."));
        this.zzbrl.release();
    }
}
