package com.google.android.gms.internal.ads;

import android.os.IInterface;
import com.google.android.gms.c.a;

/* JADX INFO: loaded from: classes.dex */
public interface zzld extends IInterface {
    zzkn createAdLoaderBuilder(a aVar, String str, zzxn zzxnVar, int i);

    zzaap createAdOverlay(a aVar);

    zzks createBannerAdManager(a aVar, zzjn zzjnVar, String str, zzxn zzxnVar, int i);

    zzaaz createInAppPurchaseManager(a aVar);

    zzks createInterstitialAdManager(a aVar, zzjn zzjnVar, String str, zzxn zzxnVar, int i);

    zzqa createNativeAdViewDelegate(a aVar, a aVar2);

    zzqf createNativeAdViewHolderDelegate(a aVar, a aVar2, a aVar3);

    zzagz createRewardedVideoAd(a aVar, zzxn zzxnVar, int i);

    zzks createSearchAdManager(a aVar, zzjn zzjnVar, String str, int i);

    zzlj getMobileAdsSettingsManager(a aVar);

    zzlj getMobileAdsSettingsManagerWithClientJarVersion(a aVar, int i);
}
