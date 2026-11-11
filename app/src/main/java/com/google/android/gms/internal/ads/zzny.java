package com.google.android.gms.internal.ads;

import android.view.View;
import com.google.android.gms.c.a;
import com.google.android.gms.c.d;
import javax.annotation.ParametersAreNonnullByDefault;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzny extends zzob {
    private final com.google.android.gms.ads.internal.zzaf zzbgs;
    private final String zzbgt;
    private final String zzbgu;

    public zzny(com.google.android.gms.ads.internal.zzaf zzafVar, String str, String str2) {
        this.zzbgs = zzafVar;
        this.zzbgt = str;
        this.zzbgu = str2;
    }

    @Override // com.google.android.gms.internal.ads.zzoa
    public final String getContent() {
        return this.zzbgu;
    }

    @Override // com.google.android.gms.internal.ads.zzoa
    public final void recordClick() {
        this.zzbgs.zzcn();
    }

    @Override // com.google.android.gms.internal.ads.zzoa
    public final void recordImpression() {
        this.zzbgs.zzco();
    }

    @Override // com.google.android.gms.internal.ads.zzoa
    public final void zzg(a aVar) {
        if (aVar == null) {
            return;
        }
        this.zzbgs.zzh((View) d.b(aVar));
    }

    @Override // com.google.android.gms.internal.ads.zzoa
    public final String zzjn() {
        return this.zzbgt;
    }
}
