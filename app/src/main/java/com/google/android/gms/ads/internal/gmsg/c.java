package com.google.android.gms.ads.internal.gmsg;

import com.google.android.gms.internal.ads.zzadh;

/* JADX INFO: loaded from: classes.dex */
@zzadh
final class c {
    private final d bzT;
    private final boolean bzU;
    private final String bzV;

    public c(HttpClient httpClient, boolean z, d dVar, String str) {
        this.bzU = z;
        this.bzT = dVar;
        this.bzV = str;
    }

    public final boolean Sp() {
        return this.bzU;
    }

    public final d WP() {
        return this.bzT;
    }

    public final String getReason() {
        return this.bzV;
    }
}
