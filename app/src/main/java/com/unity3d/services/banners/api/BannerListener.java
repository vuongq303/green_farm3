package com.unity3d.services.banners.api;

import com.unity3d.services.banners.UnityBanners;
import com.unity3d.services.banners.view.BannerView;
import com.unity3d.services.core.misc.Utilities;
import com.unity3d.services.core.webview.bridge.WebViewCallback;
import com.unity3d.services.core.webview.bridge.WebViewExposed;

/* JADX INFO: loaded from: classes.dex */
public class BannerListener {
    @WebViewExposed
    public static void sendShowEvent(final String str, WebViewCallback webViewCallback) {
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.services.banners.api.BannerListener.1
            @Override // java.lang.Runnable
            public void run() {
                if (UnityBanners.getBannerListener() != null) {
                    UnityBanners.getBannerListener().onUnityBannerShow(str);
                }
            }
        });
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void sendClickEvent(final String str, WebViewCallback webViewCallback) {
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.services.banners.api.BannerListener.2
            @Override // java.lang.Runnable
            public void run() {
                if (UnityBanners.getBannerListener() != null) {
                    UnityBanners.getBannerListener().onUnityBannerClick(str);
                }
            }
        });
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void sendHideEvent(final String str, WebViewCallback webViewCallback) {
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.services.banners.api.BannerListener.3
            @Override // java.lang.Runnable
            public void run() {
                if (UnityBanners.getBannerListener() != null) {
                    UnityBanners.getBannerListener().onUnityBannerHide(str);
                }
            }
        });
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void sendErrorEvent(final String str, WebViewCallback webViewCallback) {
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.services.banners.api.BannerListener.4
            @Override // java.lang.Runnable
            public void run() {
                if (UnityBanners.getBannerListener() != null) {
                    UnityBanners.getBannerListener().onUnityBannerError(str);
                }
            }
        });
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void sendLoadEvent(final String str, WebViewCallback webViewCallback) {
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.services.banners.api.BannerListener.5
            @Override // java.lang.Runnable
            public void run() {
                if (UnityBanners.getBannerListener() != null) {
                    UnityBanners.getBannerListener().onUnityBannerLoaded(str, BannerView.getInstance());
                }
            }
        });
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void sendUnloadEvent(final String str, WebViewCallback webViewCallback) {
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.services.banners.api.BannerListener.6
            @Override // java.lang.Runnable
            public void run() {
                if (UnityBanners.getBannerListener() != null) {
                    UnityBanners.getBannerListener().onUnityBannerUnloaded(str);
                }
            }
        });
        webViewCallback.invoke(new Object[0]);
    }
}
