package com.unity3d.services.ads.properties;

import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.services.banners.IUnityBannerListener;

/* JADX INFO: loaded from: classes.dex */
public class AdsProperties {
    private static IUnityAdsListener _listener;
    private static int _showTimeout = 5000;
    private static IUnityBannerListener bannerListener;

    public static IUnityAdsListener getListener() {
        return _listener;
    }

    public static void setListener(IUnityAdsListener iUnityAdsListener) {
        _listener = iUnityAdsListener;
    }

    public static void setShowTimeout(int i) {
        _showTimeout = i;
    }

    public static int getShowTimeout() {
        return _showTimeout;
    }

    public static void setBannerListener(IUnityBannerListener iUnityBannerListener) {
        bannerListener = iUnityBannerListener;
    }

    public static IUnityBannerListener getBannerListener() {
        return bannerListener;
    }
}
