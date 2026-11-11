package com.unity3d.services.analytics;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.unity3d.services.core.webview.WebViewApp;
import com.unity3d.services.core.webview.WebViewEventCategory;
import com.unity3d.services.purchasing.core.TransactionDetailsUtilities;
import java.util.Date;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class UnityAnalytics {
    protected static JSONArray eventQueue = new JSONArray();

    enum AnalyticsEventType {
        POSTEVENT
    }

    private static JSONObject createItemAcquired(String str, Float f, String str2, Float f2, String str3, String str4, String str5, AcquisitionType acquisitionType) {
        HashMap map = new HashMap();
        map.put("currency_type", acquisitionType.toString());
        map.put("transaction_context", str);
        map.put("amount", f);
        map.put(FirebaseAnalytics.Param.ITEM_ID, str2);
        map.put("balance", f2);
        map.put("item_type", str3);
        map.put(FirebaseAnalytics.Param.LEVEL, str4);
        map.put(FirebaseAnalytics.Param.TRANSACTION_ID, str5);
        HashMap map2 = new HashMap();
        map2.put("custom_params", map);
        map2.put("ts", 1533594423477L);
        map2.put("name", "item_acquired");
        HashMap map3 = new HashMap();
        map3.put("type", "analytics.custom.v1");
        map3.put("msg", map2);
        return new JSONObject(map3);
    }

    private static JSONObject createItemSpent(String str, Float f, String str2, Float f2, String str3, String str4, String str5, AcquisitionType acquisitionType) {
        HashMap map = new HashMap();
        map.put("currency_type", acquisitionType.toString());
        map.put("transaction_context", str);
        map.put("amount", f);
        map.put(FirebaseAnalytics.Param.ITEM_ID, str2);
        map.put("balance", f2);
        map.put("item_type", str3);
        map.put(FirebaseAnalytics.Param.LEVEL, str4);
        map.put(FirebaseAnalytics.Param.TRANSACTION_ID, str5);
        HashMap map2 = new HashMap();
        map2.put("custom_params", map);
        map2.put("ts", Long.valueOf(new Date().getTime()));
        map2.put("name", "item_spent");
        HashMap map3 = new HashMap();
        map3.put("type", "analytics.custom.v1");
        map3.put("msg", map2);
        return new JSONObject(map3);
    }

    private static JSONObject createLevelFail(Integer num) {
        HashMap map = new HashMap();
        map.put("level_index", num);
        HashMap map2 = new HashMap();
        map2.put("custom_params", map);
        map2.put("ts", Long.valueOf(new Date().getTime()));
        map2.put("name", "level_fail");
        HashMap map3 = new HashMap();
        map3.put("type", "analytics.custom.v1");
        map3.put("msg", map2);
        return new JSONObject(map3);
    }

    private static JSONObject createLevelUp(Integer num) {
        HashMap map = new HashMap();
        map.put("new_level_index", num);
        HashMap map2 = new HashMap();
        map2.put("custom_params", map);
        map2.put("ts", Long.valueOf(new Date().getTime()));
        map2.put("name", FirebaseAnalytics.Event.LEVEL_UP);
        HashMap map3 = new HashMap();
        map3.put("type", "analytics.custom.v1");
        map3.put("msg", map2);
        return new JSONObject(map3);
    }

    private static JSONObject createAdComplete(String str, String str2, Boolean bool) {
        HashMap map = new HashMap();
        map.put("rewarded", bool);
        map.put("network", str);
        map.put("placement_id", str2);
        HashMap map2 = new HashMap();
        map2.put("custom_params", map);
        map2.put("ts", Long.valueOf(new Date().getTime()));
        map2.put("name", "ad_complete");
        HashMap map3 = new HashMap();
        map3.put("type", "analytics.custom.v1");
        map3.put("msg", map2);
        return new JSONObject(map3);
    }

    private static JSONObject createIapTransaction(String str, Float f, String str2, Boolean bool, String str3) {
        HashMap map = new HashMap();
        map.put("ts", Long.valueOf(new Date().getTime()));
        map.put("name", "ad_complete");
        map.put("productid", str);
        map.put("amount", f);
        map.put("currency", str2);
        map.put("promo", bool);
        map.put(TransactionDetailsUtilities.RECEIPT, str3);
        HashMap map2 = new HashMap();
        map2.put("type", "analytics.transaction.v1");
        map2.put("msg", map);
        return new JSONObject(map2);
    }

    public static void onItemAcquired(String str, Float f, String str2, Float f2, String str3, String str4, String str5, AcquisitionType acquisitionType) {
        postEvent(createItemAcquired(str, f, str2, f2, str3, str4, str5, acquisitionType));
    }

    public static void onItemSpent(String str, Float f, String str2, Float f2, String str3, String str4, String str5, AcquisitionType acquisitionType) {
        postEvent(createItemSpent(str, f, str2, f2, str3, str4, str5, acquisitionType));
    }

    public static void onLevelFail(Integer num) {
        postEvent(createLevelFail(num));
    }

    public static void onLevelUp(Integer num) {
        postEvent(createLevelUp(num));
    }

    public static void onAdComplete(String str, String str2, Boolean bool) {
        postEvent(createAdComplete(str, str2, bool));
    }

    public static void onIapTransaction(String str, Float f, String str2, Boolean bool, String str3) {
        postEvent(createIapTransaction(str, f, str2, bool, str3));
    }

    public static void onEvent(JSONObject jSONObject) {
        postEvent(jSONObject);
    }

    private static synchronized void postEvent(JSONObject jSONObject) {
        if (eventQueue.length() < 200) {
            eventQueue.put(jSONObject);
        }
        WebViewApp currentApp = WebViewApp.getCurrentApp();
        if (currentApp != null && Boolean.valueOf(currentApp.sendEvent(WebViewEventCategory.ANALYTICS, AnalyticsEventType.POSTEVENT, eventQueue.toString())).booleanValue()) {
            eventQueue = new JSONArray();
        }
    }
}
