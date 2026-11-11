package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzcf implements zzbt<zzcg> {
    private final zzat zzvi;
    private final zzcg zzzy = new zzcg();

    public zzcf(zzat zzatVar) {
        this.zzvi = zzatVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzbt
    public final void zza(String str, boolean z) {
        if (!"ga_dryRun".equals(str)) {
            this.zzvi.zzbu().zzd("Bool xml configuration name not recognized", str);
        } else {
            this.zzzy.zzaad = z ? 1 : 0;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzbt
    public final void zzb(String str, int i) {
        if ("ga_dispatchPeriod".equals(str)) {
            this.zzzy.zzaac = i;
        } else {
            this.zzvi.zzbu().zzd("Int xml configuration name not recognized", str);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzbt
    public final void zzb(String str, String str2) {
    }

    @Override // com.google.android.gms.internal.measurement.zzbt
    public final void zzc(String str, String str2) {
        if ("ga_appName".equals(str)) {
            this.zzzy.zzzz = str2;
            return;
        }
        if ("ga_appVersion".equals(str)) {
            this.zzzy.zzaaa = str2;
        } else if ("ga_logLevel".equals(str)) {
            this.zzzy.zzaab = str2;
        } else {
            this.zzvi.zzbu().zzd("String xml configuration name not recognized", str);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzbt
    public final /* synthetic */ zzbr zzdr() {
        return this.zzzy;
    }
}
