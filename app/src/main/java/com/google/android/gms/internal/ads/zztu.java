package com.google.android.gms.internal.ads;

import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
final class zztu extends zzki {
    private final zzkh zzboi;

    zztu(zzkh zzkhVar) {
        this.zzboi = zzkhVar;
    }

    @Override // com.google.android.gms.internal.ads.zzkh
    public final void onAdClicked() {
        this.zzboi.onAdClicked();
    }

    @Override // com.google.android.gms.internal.ads.zzkh
    public final void onAdClosed() {
        if (zzud.zzlv()) {
            int iIntValue = ((Integer) zzkb.zzik().zzd(zznk.zzazg)).intValue();
            int iIntValue2 = ((Integer) zzkb.zzik().zzd(zznk.zzazh)).intValue();
            if (iIntValue <= 0 || iIntValue2 < 0) {
                com.google.android.gms.ads.internal.zzbv.zzex().zzld();
            } else {
                zzakk.zzcrm.postDelayed(zztv.zzboj, new Random().nextInt(iIntValue2 + 1) + iIntValue);
            }
        }
        this.zzboi.onAdClosed();
    }

    @Override // com.google.android.gms.internal.ads.zzkh
    public final void onAdFailedToLoad(int i) {
        this.zzboi.onAdFailedToLoad(i);
    }

    @Override // com.google.android.gms.internal.ads.zzkh
    public final void onAdImpression() {
        this.zzboi.onAdImpression();
    }

    @Override // com.google.android.gms.internal.ads.zzkh
    public final void onAdLeftApplication() {
        this.zzboi.onAdLeftApplication();
    }

    @Override // com.google.android.gms.internal.ads.zzkh
    public final void onAdLoaded() {
        this.zzboi.onAdLoaded();
    }

    @Override // com.google.android.gms.internal.ads.zzkh
    public final void onAdOpened() {
        this.zzboi.onAdOpened();
    }
}
