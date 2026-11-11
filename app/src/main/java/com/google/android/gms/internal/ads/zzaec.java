package com.google.android.gms.internal.ads;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzaec extends zzadz {
    private final Context mContext;

    public zzaec(Context context, zzaol<zzaef> zzaolVar, zzadx zzadxVar) {
        super(zzaolVar, zzadxVar);
        this.mContext = context;
    }

    @Override // com.google.android.gms.internal.ads.zzadz
    public final void zznz() {
    }

    @Override // com.google.android.gms.internal.ads.zzadz
    public final zzaen zzoa() {
        return zzafn.zza(this.mContext, zzafm.zzm(this.mContext));
    }
}
