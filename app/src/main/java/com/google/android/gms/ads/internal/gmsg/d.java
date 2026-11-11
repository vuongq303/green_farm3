package com.google.android.gms.ads.internal.gmsg;

import com.google.android.gms.internal.ads.zzadh;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@zzadh
final class d {
    private final String bzP;
    private final int bzW;
    private final String zzbhy;
    private final List<a> zzcf;

    d(String str, int i, List<a> list, String str2) {
        this.bzP = str;
        this.bzW = i;
        this.zzcf = list;
        this.zzbhy = str2;
    }

    public final String WL() {
        return this.bzP;
    }

    public final Iterable<a> WQ() {
        return this.zzcf;
    }

    public final String getBody() {
        return this.zzbhy;
    }

    public final int getResponseCode() {
        return this.bzW;
    }
}
