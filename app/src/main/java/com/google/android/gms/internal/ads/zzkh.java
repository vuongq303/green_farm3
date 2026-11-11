package com.google.android.gms.internal.ads;

import android.os.IInterface;

/* JADX INFO: loaded from: classes.dex */
public interface zzkh extends IInterface {
    void onAdClicked();

    void onAdClosed();

    void onAdFailedToLoad(int i);

    void onAdImpression();

    void onAdLeftApplication();

    void onAdLoaded();

    void onAdOpened();
}
