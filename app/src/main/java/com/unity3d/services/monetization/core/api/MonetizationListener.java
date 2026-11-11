package com.unity3d.services.monetization.core.api;

import com.unity3d.services.core.webview.bridge.WebViewCallback;
import com.unity3d.services.core.webview.bridge.WebViewExposed;
import com.unity3d.services.monetization.IUnityMonetizationListener;
import com.unity3d.services.monetization.UnityMonetization;
import com.unity3d.services.monetization.core.placementcontent.PlacementContentListenerError;
import com.unity3d.services.monetization.core.properties.ClientProperties;

/* JADX INFO: loaded from: classes.dex */
public class MonetizationListener {
    @WebViewExposed
    public static void isMonetizationEnabled(WebViewCallback webViewCallback) {
        webViewCallback.invoke(Boolean.valueOf(ClientProperties.isMonetizationEnabled()));
    }

    @WebViewExposed
    public static void sendPlacementContentReady(String str, WebViewCallback webViewCallback) {
        IUnityMonetizationListener listener = ClientProperties.getListener();
        if (listener != null) {
            try {
                listener.onPlacementContentReady(str, com.unity3d.services.monetization.core.placementcontent.PlacementContents.getPlacementContent(str));
                webViewCallback.invoke(new Object[0]);
                return;
            } catch (Exception e) {
                webViewCallback.error(PlacementContentListenerError.PLACEMENTCONTENT_LISTENER_ERROR, e);
                return;
            }
        }
        webViewCallback.error(PlacementContentListenerError.PLACEMENTCONTENT_LISTENER_NULL, new Object[0]);
    }

    @WebViewExposed
    public static void sendPlacementContentStateChanged(String str, String str2, String str3, WebViewCallback webViewCallback) {
        IUnityMonetizationListener listener = ClientProperties.getListener();
        if (listener != null) {
            try {
                listener.onPlacementContentStateChange(str, com.unity3d.services.monetization.core.placementcontent.PlacementContents.getPlacementContent(str), UnityMonetization.PlacementContentState.valueOf(str2), UnityMonetization.PlacementContentState.valueOf(str3));
                webViewCallback.invoke(new Object[0]);
                return;
            } catch (Exception e) {
                webViewCallback.error(PlacementContentListenerError.PLACEMENTCONTENT_LISTENER_ERROR, e);
                return;
            }
        }
        webViewCallback.error(PlacementContentListenerError.PLACEMENTCONTENT_LISTENER_NULL, new Object[0]);
    }
}
