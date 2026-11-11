package com.unity3d.services.monetization.placementcontent.core;

import com.facebook.share.internal.ShareConstants;
import com.unity3d.services.core.log.DeviceLog;
import com.unity3d.services.core.webview.WebViewApp;
import com.unity3d.services.monetization.UnityMonetization;
import com.unity3d.services.monetization.core.placementcontent.PlacementContentEvent;
import com.unity3d.services.monetization.core.webview.WebViewEventCategory;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class PlacementContent {
    private Map<String, Object> extras = new HashMap();
    protected String placementId;
    private UnityMonetization.PlacementContentState state;
    private String type;

    public PlacementContent(String str, Map<String, Object> map) {
        this.placementId = str;
        this.type = (String) map.get("type");
        this.extras.putAll(map);
    }

    public Object getExtra(String str) {
        return this.extras.get(str);
    }

    public Map<String, Object> getExtras() {
        return this.extras;
    }

    public String getType() {
        return this.type;
    }

    public void setState(UnityMonetization.PlacementContentState placementContentState) {
        this.state = placementContentState;
    }

    public void sendCustomEvent(CustomEvent customEvent) {
        if (customEvent.getCategory() == null) {
            customEvent.setCategory(getDefaultEventCategory());
        }
        if (WebViewApp.getCurrentApp() == null) {
            DeviceLog.warning("Could not send custom event due to app being null");
        } else {
            WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.PLACEMENT_CONTENT, PlacementContentEvent.CUSTOM, this.placementId, getJsonForCustomEvent(customEvent));
        }
    }

    private JSONObject getJsonForCustomEvent(CustomEvent customEvent) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("category", customEvent.getCategory());
            jSONObject.put("type", customEvent.getType());
            jSONObject.put(ShareConstants.WEB_DIALOG_PARAM_DATA, customEvent.getData());
        } catch (JSONException e) {
            DeviceLog.warning("Error creating json for custom event: ", e.getMessage());
        }
        return jSONObject;
    }

    public boolean isReady() {
        return this.state == UnityMonetization.PlacementContentState.READY;
    }

    public void sendCustomEvent(String str, Map<String, Object> map) {
        sendCustomEvent(new CustomEvent(str, map));
    }

    public void sendCustomEvent(String str, String str2, Map<String, Object> map) {
        sendCustomEvent(new CustomEvent(str, str2, map));
    }

    public UnityMonetization.PlacementContentState getState() {
        return this.state;
    }

    protected String getDefaultEventCategory() {
        return "PLACEMENT_CONTENT";
    }
}
