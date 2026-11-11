package com.unity3d.services.banners.api;

import android.view.ViewGroup;
import android.view.ViewParent;
import com.unity3d.services.banners.properties.BannerProperties;
import com.unity3d.services.banners.view.BannerEvent;
import com.unity3d.services.banners.view.BannerPosition;
import com.unity3d.services.banners.view.BannerView;
import com.unity3d.services.core.log.DeviceLog;
import com.unity3d.services.core.misc.Utilities;
import com.unity3d.services.core.properties.ClientProperties;
import com.unity3d.services.core.webview.WebViewApp;
import com.unity3d.services.core.webview.WebViewEventCategory;
import com.unity3d.services.core.webview.bridge.WebViewCallback;
import com.unity3d.services.core.webview.bridge.WebViewExposed;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class Banner {
    @WebViewExposed
    public static void load(final JSONArray jSONArray, final String str, final Integer num, final Integer num2, WebViewCallback webViewCallback) {
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.services.banners.api.Banner.1
            @Override // java.lang.Runnable
            public synchronized void run() {
                BannerView orCreateInstance = BannerView.getOrCreateInstance();
                BannerPosition bannerPositionFromString = ClientProperties.getbannerDefaultPosition();
                if (bannerPositionFromString == null) {
                    bannerPositionFromString = BannerPosition.fromString(str);
                }
                orCreateInstance.setBannerDimensions(num.intValue(), num2.intValue(), bannerPositionFromString);
                orCreateInstance.setViews(Banner.getArrayFromJSONArray(jSONArray));
                WebViewApp currentApp = WebViewApp.getCurrentApp();
                if (currentApp != null) {
                    currentApp.sendEvent(WebViewEventCategory.BANNER, BannerEvent.BANNER_LOADED, new Object[0]);
                }
            }
        });
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void destroy(WebViewCallback webViewCallback) {
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.services.banners.api.Banner.2
            @Override // java.lang.Runnable
            public synchronized void run() {
                ViewParent parent;
                BannerView bannerView = BannerView.getInstance();
                if (bannerView != null) {
                    bannerView.destroy();
                    if (BannerProperties.getBannerParent() != null && (parent = BannerProperties.getBannerParent().getParent()) != null && (parent instanceof ViewGroup)) {
                        ((ViewGroup) parent).removeView(BannerProperties.getBannerParent());
                    }
                    BannerProperties.setBannerParent(null);
                    WebViewApp currentApp = WebViewApp.getCurrentApp();
                    if (currentApp != null) {
                        currentApp.sendEvent(WebViewEventCategory.BANNER, BannerEvent.BANNER_DESTROYED, new Object[0]);
                    }
                }
            }
        });
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void setViewFrame(final String str, final Integer num, final Integer num2, final Integer num3, final Integer num4, WebViewCallback webViewCallback) {
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.services.banners.api.Banner.3
            @Override // java.lang.Runnable
            public void run() {
                BannerView bannerView = BannerView.getInstance();
                if (bannerView != null) {
                    bannerView.setViewFrame(str, num, num2, num3, num4);
                }
            }
        });
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void setViews(final JSONArray jSONArray, WebViewCallback webViewCallback) {
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.services.banners.api.Banner.4
            @Override // java.lang.Runnable
            public void run() {
                BannerView bannerView = BannerView.getInstance();
                if (bannerView != null) {
                    bannerView.setViews(Banner.getArrayFromJSONArray(jSONArray));
                }
            }
        });
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void setBannerFrame(final String str, final Integer num, final Integer num2, WebViewCallback webViewCallback) {
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.services.banners.api.Banner.5
            @Override // java.lang.Runnable
            public void run() {
                BannerView bannerView = BannerView.getInstance();
                if (bannerView != null) {
                    BannerPosition bannerPositionFromString = ClientProperties.getbannerDefaultPosition();
                    if (bannerPositionFromString == null) {
                        bannerPositionFromString = BannerPosition.fromString(str);
                    }
                    bannerView.setBannerDimensions(num.intValue(), num2.intValue(), bannerPositionFromString);
                    bannerView.setLayoutParams(bannerView.getLayoutParams());
                }
            }
        });
        webViewCallback.invoke(new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<String> getArrayFromJSONArray(JSONArray jSONArray) {
        String[] strArr = new String[jSONArray.length()];
        for (int i = 0; i < strArr.length; i++) {
            try {
                strArr[i] = jSONArray.getString(i);
            } catch (JSONException e) {
                DeviceLog.warning("Exception converting JSON Array to String Array: %s", e);
            }
        }
        return Arrays.asList(strArr);
    }
}
