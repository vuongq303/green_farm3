package com.google.android.gms.internal.ads;

import com.google.android.gms.internal.ads.zzarr;
import com.google.android.gms.internal.ads.zzarz;
import com.google.android.gms.internal.ads.zzasb;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzaro<WebViewT extends zzarr & zzarz & zzasb> {
    private final zzarq zzdem;
    private final WebViewT zzden;

    private zzaro(WebViewT webviewt, zzarq zzarqVar) {
        this.zzdem = zzarqVar;
        this.zzden = webviewt;
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.google.android.gms.internal.ads.zzarp, com.google.android.gms.internal.ads.zzarq] */
    public static zzaro<zzaqw> zzk(final zzaqw zzaqwVar) {
        return new zzaro<>(zzaqwVar, new Object(zzaqwVar) { // from class: com.google.android.gms.internal.ads.zzarp
            private final zzaqw zzcbh;

            {
                this.zzcbh = zzaqwVar;
            }
        });
    }
}
