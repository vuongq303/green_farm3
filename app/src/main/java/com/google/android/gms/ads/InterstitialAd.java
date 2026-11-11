package com.google.android.gms.ads;

import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.ads.reward.zza;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.internal.ads.zzjd;
import com.google.android.gms.internal.ads.zzma;

/* JADX INFO: loaded from: classes.dex */
public final class InterstitialAd {
    private final zzma bzt;

    public InterstitialAd(Context context) {
        this.bzt = new zzma(context);
        bf.d(context, "Context cannot be null");
    }

    public final AdListener getAdListener() {
        return this.bzt.getAdListener();
    }

    public final String getAdUnitId() {
        return this.bzt.getAdUnitId();
    }

    public final String getMediationAdapterClassName() {
        return this.bzt.getMediationAdapterClassName();
    }

    public final boolean isLoaded() {
        return this.bzt.isLoaded();
    }

    public final boolean isLoading() {
        return this.bzt.isLoading();
    }

    public final void loadAd(AdRequest adRequest) {
        this.bzt.zza(adRequest.zzay());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void setAdListener(AdListener adListener) {
        this.bzt.setAdListener(adListener);
        if (adListener != 0 && (adListener instanceof zzjd)) {
            this.bzt.zza((zzjd) adListener);
        } else if (adListener == 0) {
            this.bzt.zza((zzjd) null);
        }
    }

    public final void setAdUnitId(String str) {
        this.bzt.setAdUnitId(str);
    }

    public final void setImmersiveMode(boolean z) {
        this.bzt.setImmersiveMode(z);
    }

    public final void setRewardedVideoAdListener(RewardedVideoAdListener rewardedVideoAdListener) {
        this.bzt.setRewardedVideoAdListener(rewardedVideoAdListener);
    }

    public final void show() {
        this.bzt.show();
    }

    public final void zza(zza zzaVar) {
        this.bzt.zza(zzaVar);
    }

    public final void zza(boolean z) {
        this.bzt.zza(true);
    }

    public final Bundle zzba() {
        return this.bzt.zzba();
    }
}
