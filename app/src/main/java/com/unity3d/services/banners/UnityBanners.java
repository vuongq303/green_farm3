package com.unity3d.services.banners;

import android.app.Activity;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.ads.placement.Placement;
import com.unity3d.services.ads.properties.AdsProperties;
import com.unity3d.services.banners.view.BannerPosition;
import com.unity3d.services.core.log.DeviceLog;
import com.unity3d.services.core.misc.Utilities;
import com.unity3d.services.core.properties.ClientProperties;

/* JADX INFO: loaded from: classes.dex */
public final class UnityBanners {
    public static void loadBanner(Activity activity) {
        loadBanner(activity, Placement.getDefaultBannerPlacement());
    }

    public static void loadBanner(final Activity activity, final String str) {
        DeviceLog.entered();
        if (!UnityAds.isSupported()) {
            sendError("Unity Ads is not supported on this device.");
        }
        if (!UnityAds.isInitialized()) {
            sendError("UnityAds is not initialized.");
        } else if (!UnityAds.isReady(str)) {
            sendError("Banner placement " + str + " is not ready");
        } else {
            new Thread(new Runnable() { // from class: com.unity3d.services.banners.UnityBanners.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        ClientProperties.setActivity(activity);
                        if (!BannerShow.show(str)) {
                            UnityBanners.sendError("Could not show banner in time");
                        }
                    } catch (Exception e) {
                        UnityBanners.sendError(e.getMessage());
                    }
                }
            }).start();
        }
    }

    public static void destroy() {
        new Thread(new Runnable() { // from class: com.unity3d.services.banners.UnityBanners.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (!BannerHide.hide()) {
                        UnityBanners.sendError("Could not hide banner in time");
                    }
                } catch (Exception e) {
                    UnityBanners.sendError(e.getMessage());
                }
            }
        }).start();
    }

    public static void setBannerPosition(BannerPosition bannerPosition) {
        ClientProperties.setBannerDefaultPosition(bannerPosition);
    }

    public static void setBannerListener(IUnityBannerListener iUnityBannerListener) {
        AdsProperties.setBannerListener(iUnityBannerListener);
    }

    public static IUnityBannerListener getBannerListener() {
        return AdsProperties.getBannerListener();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void sendError(final String str) {
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.services.banners.UnityBanners.3
            @Override // java.lang.Runnable
            public void run() {
                IUnityBannerListener bannerListener = UnityBanners.getBannerListener();
                if (bannerListener != null) {
                    bannerListener.onUnityBannerError(str);
                }
            }
        });
    }
}
