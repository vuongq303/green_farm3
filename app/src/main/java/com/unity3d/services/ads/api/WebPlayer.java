package com.unity3d.services.ads.api;

import android.view.View;
import com.unity3d.services.ads.adunit.IAdUnitViewHandler;
import com.unity3d.services.ads.webplayer.WebPlayerError;
import com.unity3d.services.banners.view.BannerView;
import com.unity3d.services.core.log.DeviceLog;
import com.unity3d.services.core.misc.Utilities;
import com.unity3d.services.core.webview.bridge.WebViewCallback;
import com.unity3d.services.core.webview.bridge.WebViewExposed;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class WebPlayer {
    private static JSONObject _webSettings = null;
    private static JSONObject _webPlayerSettings = null;
    private static JSONObject _webPlayerEventSettings = null;

    public static JSONObject getWebPlayerSettings() {
        return _webPlayerSettings;
    }

    public static JSONObject getWebSettings() {
        return _webSettings;
    }

    public static JSONObject getWebPlayerEventSettings() {
        return _webPlayerEventSettings;
    }

    @WebViewExposed
    public static void setUrl(final String str, String str2, WebViewCallback webViewCallback) {
        final com.unity3d.services.ads.webplayer.WebPlayer webPlayer = getWebPlayer(str2);
        if (webPlayer != null) {
            Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.services.ads.api.WebPlayer.1
                @Override // java.lang.Runnable
                public void run() {
                    webPlayer.loadUrl(str);
                }
            });
            webViewCallback.invoke(new Object[0]);
        } else {
            webViewCallback.error(WebPlayerError.WEBPLAYER_NULL, new Object[0]);
        }
    }

    @WebViewExposed
    public static void setData(final String str, final String str2, final String str3, String str4, WebViewCallback webViewCallback) {
        final com.unity3d.services.ads.webplayer.WebPlayer webPlayer = getWebPlayer(str4);
        if (webPlayer != null) {
            Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.services.ads.api.WebPlayer.2
                @Override // java.lang.Runnable
                public void run() {
                    webPlayer.loadData(str, str2, str3);
                }
            });
            webViewCallback.invoke(new Object[0]);
        } else {
            webViewCallback.error(WebPlayerError.WEBPLAYER_NULL, new Object[0]);
        }
    }

    @WebViewExposed
    public static void setDataWithUrl(final String str, final String str2, final String str3, final String str4, String str5, WebViewCallback webViewCallback) {
        final com.unity3d.services.ads.webplayer.WebPlayer webPlayer = getWebPlayer(str5);
        if (webPlayer != null) {
            Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.services.ads.api.WebPlayer.3
                @Override // java.lang.Runnable
                public void run() {
                    webPlayer.loadDataWithBaseURL(str, str2, str3, str4, null);
                }
            });
            webViewCallback.invoke(new Object[0]);
        } else {
            webViewCallback.error(WebPlayerError.WEBPLAYER_NULL, new Object[0]);
        }
    }

    @WebViewExposed
    public static void setSettings(final JSONObject jSONObject, final JSONObject jSONObject2, String str, WebViewCallback webViewCallback) {
        switch (str) {
            case "bannerplayer":
                Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.services.ads.api.WebPlayer.4
                    @Override // java.lang.Runnable
                    public void run() {
                        com.unity3d.services.ads.webplayer.WebPlayer bannerWebPlayer = WebPlayer.getBannerWebPlayer();
                        if (bannerWebPlayer != null) {
                            bannerWebPlayer.setSettings(jSONObject, jSONObject2);
                        } else {
                            BannerView.setWebPlayerSettings(jSONObject, jSONObject2);
                        }
                    }
                });
                break;
            case "webplayer":
                _webSettings = jSONObject;
                _webPlayerSettings = jSONObject2;
                break;
        }
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void setEventSettings(final JSONObject jSONObject, String str, WebViewCallback webViewCallback) {
        if (str.equals("webplayer")) {
            _webPlayerEventSettings = jSONObject;
        } else {
            Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.services.ads.api.WebPlayer.5
                @Override // java.lang.Runnable
                public void run() {
                    com.unity3d.services.ads.webplayer.WebPlayer bannerWebPlayer = WebPlayer.getBannerWebPlayer();
                    if (bannerWebPlayer != null) {
                        bannerWebPlayer.setEventSettings(jSONObject);
                    } else {
                        BannerView.setWebPlayerEventSettings(jSONObject);
                    }
                }
            });
        }
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void clearSettings(WebViewCallback webViewCallback) {
        _webSettings = null;
        _webPlayerSettings = null;
        _webPlayerEventSettings = null;
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void getErroredSettings(String str, WebViewCallback webViewCallback) {
        com.unity3d.services.ads.webplayer.WebPlayer webPlayer = getWebPlayer(str);
        if (webPlayer != null) {
            Map<String, String> erroredSettings = webPlayer.getErroredSettings();
            JSONObject jSONObject = new JSONObject();
            try {
                for (Map.Entry<String, String> entry : erroredSettings.entrySet()) {
                    jSONObject.put(entry.getKey(), entry.getValue());
                }
            } catch (Exception e) {
                DeviceLog.exception("Error forming JSON object", e);
            }
            webViewCallback.invoke(jSONObject);
            webViewCallback.invoke(new Object[0]);
            return;
        }
        webViewCallback.error(WebPlayerError.WEBPLAYER_NULL, new Object[0]);
    }

    @WebViewExposed
    public static void sendEvent(JSONArray jSONArray, String str, WebViewCallback webViewCallback) {
        com.unity3d.services.ads.webplayer.WebPlayer webPlayer = getWebPlayer(str);
        if (webPlayer != null) {
            webPlayer.sendEvent(jSONArray);
            webViewCallback.invoke(new Object[0]);
        } else {
            webViewCallback.error(WebPlayerError.WEBPLAYER_NULL, new Object[0]);
        }
    }

    private static com.unity3d.services.ads.webplayer.WebPlayer getWebPlayer(String str) {
        switch (str) {
            case "webplayer":
                return getAdUnitWebPlayer();
            case "bannerplayer":
                return getBannerWebPlayer();
            default:
                return null;
        }
    }

    private static com.unity3d.services.ads.webplayer.WebPlayer getAdUnitWebPlayer() {
        IAdUnitViewHandler viewHandler;
        View view;
        if (AdUnit.getAdUnitActivity() == null || (viewHandler = AdUnit.getAdUnitActivity().getViewHandler("webplayer")) == null || (view = viewHandler.getView()) == null) {
            return null;
        }
        return (com.unity3d.services.ads.webplayer.WebPlayer) view;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.unity3d.services.ads.webplayer.WebPlayer getBannerWebPlayer() {
        if (BannerView.getInstance() == null) {
            return null;
        }
        return BannerView.getInstance().getWebPlayer();
    }
}
