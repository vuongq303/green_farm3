package com.google.android.gms.internal.ads;

import javax.annotation.ParametersAreNonnullByDefault;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zznv {
    private final long zzbgj;
    private final String zzbgk;
    private final zznv zzbgl;

    public zznv(long j, String str, zznv zznvVar) {
        this.zzbgj = j;
        this.zzbgk = str;
        this.zzbgl = zznvVar;
    }

    public final long getTime() {
        return this.zzbgj;
    }

    public final String zzjg() {
        return this.zzbgk;
    }

    public final zznv zzjh() {
        return this.zzbgl;
    }
}
