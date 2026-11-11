package com.facebook.appevents.internal;

import com.facebook.appevents.AppEvent;
import com.facebook.internal.Utility;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class RestrictiveDataManager {
    private static boolean enabled = false;
    private static final String TAG = RestrictiveDataManager.class.getCanonicalName();
    private static List<RestrictiveParam> restrictiveParams = new ArrayList();
    private static Set<String> restrictiveEvents = new HashSet();

    public static void enable() {
        enabled = true;
    }

    public static synchronized void updateFromSetting(String str) {
        try {
            if (enabled) {
                try {
                    if (!str.isEmpty()) {
                        JSONObject jSONObject = new JSONObject(str);
                        restrictiveParams.clear();
                        restrictiveEvents.clear();
                        Iterator<String> itKeys = jSONObject.keys();
                        while (itKeys.hasNext()) {
                            String next = itKeys.next();
                            JSONObject jSONObject2 = jSONObject.getJSONObject(next);
                            if (jSONObject2 != null) {
                                if (jSONObject2.optBoolean("is_deprecated_event")) {
                                    restrictiveEvents.add(next);
                                } else {
                                    JSONObject jSONObjectOptJSONObject = jSONObject.getJSONObject(next).optJSONObject("restrictive_param");
                                    if (jSONObjectOptJSONObject != null) {
                                        restrictiveParams.add(new RestrictiveParam(next, Utility.convertJSONObjectToStringMap(jSONObjectOptJSONObject)));
                                    }
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    String str2 = TAG;
                } catch (Exception e2) {
                    String str3 = TAG;
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public static void processEvents(List<AppEvent> list) {
        if (enabled) {
            Iterator<AppEvent> it = list.iterator();
            while (it.hasNext()) {
                if (isDeprecatedEvent(it.next().getName())) {
                    it.remove();
                }
            }
        }
    }

    public static void processParameters(Map<String, String> map, String str) {
        if (enabled) {
            HashMap map2 = new HashMap();
            for (String str2 : new ArrayList(map.keySet())) {
                String matchedRuleType = getMatchedRuleType(str, str2);
                if (matchedRuleType != null) {
                    map2.put(str2, matchedRuleType);
                    map.remove(str2);
                }
            }
            if (map2.size() > 0) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    for (Map.Entry entry : map2.entrySet()) {
                        jSONObject.put((String) entry.getKey(), entry.getValue());
                    }
                    map.put("_restrictedParams", jSONObject.toString());
                } catch (JSONException e) {
                    String str3 = TAG;
                }
            }
        }
    }

    private static boolean isDeprecatedEvent(String str) {
        return restrictiveEvents.contains(str);
    }

    private static String getMatchedRuleType(String str, String str2) {
        try {
            for (RestrictiveParam restrictiveParam : new ArrayList(restrictiveParams)) {
                if (restrictiveParam != null && str.equals(restrictiveParam.eventName)) {
                    for (String str3 : restrictiveParam.params.keySet()) {
                        if (str2.equals(str3)) {
                            return restrictiveParam.params.get(str3);
                        }
                    }
                }
            }
        } catch (Exception e) {
            String str4 = TAG;
        }
        return null;
    }

    class RestrictiveParam {
        String eventName;
        Map<String, String> params;

        RestrictiveParam(String str, Map<String, String> map) {
            this.eventName = str;
            this.params = map;
        }
    }
}
