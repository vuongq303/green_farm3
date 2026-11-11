package com.google.android.gms.internal.ads;

import android.os.IInterface;

/* JADX INFO: loaded from: classes.dex */
public interface zzxt extends IInterface {
    void onAdClicked();

    void onAdClosed();

    void onAdFailedToLoad(int i);

    void onAdImpression();

    void onAdLeftApplication();

    void onAdLoaded();

    void onAdOpened();

    void onAppEvent(String str, String str2);

    void onVideoEnd();

    void zza(zzxw zzxwVar);

    void zzb(zzqs zzqsVar, String str);

    void zzbj(String str);
}
