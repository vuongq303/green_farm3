package com.google.android.gms.internal.ads;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class zzaly extends zzav {
    private final /* synthetic */ byte[] zzctk;
    private final /* synthetic */ Map zzctl;
    private final /* synthetic */ zzamy zzctm;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzaly(zzalt zzaltVar, int i, String str, zzz zzzVar, zzy zzyVar, byte[] bArr, Map map, zzamy zzamyVar) {
        super(i, str, zzzVar, zzyVar);
        this.zzctk = bArr;
        this.zzctl = map;
        this.zzctm = zzamyVar;
    }

    @Override // com.google.android.gms.internal.ads.zzr
    public final Map<String, String> getHeaders() {
        return this.zzctl == null ? super.getHeaders() : this.zzctl;
    }

    @Override // com.google.android.gms.internal.ads.zzav, com.google.android.gms.internal.ads.zzr
    protected final /* synthetic */ void zza(String str) {
        zza(str);
    }

    @Override // com.google.android.gms.internal.ads.zzr
    public final byte[] zzg() {
        return this.zzctk == null ? super.zzg() : this.zzctk;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.ads.zzav
    /* JADX INFO: renamed from: zzh */
    public final void zza(String str) {
        this.zzctm.zzdg(str);
        super.zza(str);
    }
}
