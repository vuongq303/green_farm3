package com.unity3d.services.monetization.core.api;

import com.unity3d.ads.UnityAds;
import com.unity3d.services.core.webview.bridge.WebViewCallback;
import com.unity3d.services.core.webview.bridge.WebViewExposed;
import com.unity3d.services.monetization.UnityMonetization;
import com.unity3d.services.monetization.core.placementcontent.PlacementContentResultFactory;
import com.unity3d.services.monetization.core.utilities.JSONUtilities;
import com.unity3d.services.monetization.placementcontent.ads.ShowAdPlacementContent;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class PlacementContents {
    @WebViewExposed
    public static void createPlacementContent(String str, JSONObject jSONObject, WebViewCallback webViewCallback) {
        com.unity3d.services.monetization.core.placementcontent.PlacementContents.putPlacementContent(str, PlacementContentResultFactory.create(str, JSONUtilities.jsonObjectToMap(jSONObject)));
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void setPlacementContentState(String str, String str2, WebViewCallback webViewCallback) {
        com.unity3d.services.monetization.core.placementcontent.PlacementContents.setPlacementContentState(str, UnityMonetization.PlacementContentState.valueOf(str2));
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void sendAdFinished(String str, String str2, WebViewCallback webViewCallback) {
        ShowAdPlacementContent.sendAdFinished(str, UnityAds.FinishState.valueOf(str2));
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void sendAdStarted(String str, WebViewCallback webViewCallback) {
        ShowAdPlacementContent.sendAdStarted(str);
        webViewCallback.invoke(new Object[0]);
    }
}
