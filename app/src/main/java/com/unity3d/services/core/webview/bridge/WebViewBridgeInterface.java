package com.unity3d.services.core.webview.bridge;

import android.webkit.JavascriptInterface;
import com.unity3d.services.core.log.DeviceLog;
import org.json.JSONArray;

/* JADX INFO: loaded from: classes.dex */
public class WebViewBridgeInterface {
    @JavascriptInterface
    public void handleInvocation(String str) {
        DeviceLog.debug("handleInvocation " + str);
        JSONArray jSONArray = new JSONArray(str);
        Invocation invocation = new Invocation();
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONArray jSONArray2 = (JSONArray) jSONArray.get(i);
            invocation.addInvocation((String) jSONArray2.get(0), (String) jSONArray2.get(1), getParameters((JSONArray) jSONArray2.get(2)), new WebViewCallback((String) jSONArray2.get(3), invocation.getId()));
        }
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            invocation.nextInvocation();
        }
        invocation.sendInvocationCallback();
    }

    @JavascriptInterface
    public void handleCallback(String str, String str2, String str3) {
        DeviceLog.debug("handleCallback " + str + " " + str2 + " " + str3);
        JSONArray jSONArray = new JSONArray(str3);
        Object[] objArr = null;
        if (jSONArray.length() > 0) {
            Object[] objArr2 = new Object[jSONArray.length()];
            for (int i = 0; i < jSONArray.length(); i++) {
                objArr2[i] = jSONArray.get(i);
            }
            objArr = objArr2;
        }
        WebViewBridge.handleCallback(str, str2, objArr);
    }

    private Object[] getParameters(JSONArray jSONArray) {
        Object[] objArr = new Object[jSONArray.length()];
        for (int i = 0; i < jSONArray.length(); i++) {
            objArr[i] = jSONArray.get(i);
        }
        return objArr;
    }
}
