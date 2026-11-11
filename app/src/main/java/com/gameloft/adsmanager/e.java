package com.gameloft.adsmanager;

/* JADX INFO: compiled from: UnityAdsIncentivized.java */
/* JADX INFO: loaded from: classes.dex */
class e implements Runnable {
    final /* synthetic */ UnityAdsIncentivized Ac;

    e(UnityAdsIncentivized unityAdsIncentivized) {
        this.Ac = unityAdsIncentivized;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.Ac.OnShowError(JavaUtils.Errors.ERROR_EXCEPTION_RAISED, this.Ac.sdkLocation);
    }
}
