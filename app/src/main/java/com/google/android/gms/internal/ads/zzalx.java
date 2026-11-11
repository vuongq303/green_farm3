package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzalx implements zzy {
    private final /* synthetic */ String zzcce;
    private final /* synthetic */ zzama zzctj;

    zzalx(zzalt zzaltVar, String str, zzama zzamaVar) {
        this.zzcce = str;
        this.zzctj = zzamaVar;
    }

    @Override // com.google.android.gms.internal.ads.zzy
    public final void zzd(zzae zzaeVar) {
        String str = this.zzcce;
        String string = zzaeVar.toString();
        zzakb.zzdk(new StringBuilder(String.valueOf(str).length() + 21 + String.valueOf(string).length()).append("Failed to load URL: ").append(str).append("\n").append(string).toString());
        this.zzctj.zzb(null);
    }
}
