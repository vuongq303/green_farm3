package com.google.android.gms.internal.ads;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
final class zzakn implements zzamx {
    private final /* synthetic */ Context val$context;
    private final /* synthetic */ String zzcrv;

    zzakn(zzakk zzakkVar, Context context, String str) {
        this.val$context = context;
        this.zzcrv = str;
    }

    @Override // com.google.android.gms.internal.ads.zzamx
    public final void zzcz(String str) {
        com.google.android.gms.ads.internal.zzbv.zzek();
        zzakk.zzd(this.val$context, this.zzcrv, str);
    }
}
