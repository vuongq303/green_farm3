package com.unity3d.services.monetization.core.utilities;

import com.unity3d.services.core.log.DeviceLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class JSONUtilities {
    public static Map<String, Object> jsonObjectToMap(JSONObject jSONObject) {
        HashMap map = new HashMap(jSONObject.length());
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            try {
                map.put(next, mapTypeFromJSON(jSONObject.get(next)));
            } catch (JSONException e) {
                DeviceLog.error("Could not put value in map: %s, %s", next, e.getMessage());
            }
        }
        return map;
    }

    public static List<Object> jsonArrayToList(JSONArray jSONArray) {
        ArrayList arrayList = new ArrayList(jSONArray.length());
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                arrayList.add(mapTypeFromJSON(jSONArray.get(i)));
            } catch (JSONException e) {
                DeviceLog.error("Could not put value into list: %s", e.getMessage());
            }
        }
        return arrayList;
    }

    private static Object mapTypeFromJSON(Object obj) {
        if (obj instanceof JSONObject) {
            return jsonObjectToMap((JSONObject) obj);
        }
        if (obj instanceof JSONArray) {
            return jsonArrayToList((JSONArray) obj);
        }
        return obj;
    }

    public static JSONObject mapToJsonObject(Map<String, Object> map) {
        JSONObject jSONObject = new JSONObject();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            try {
                jSONObject.put(entry.getKey(), wrap(entry.getValue()));
            } catch (JSONException e) {
                DeviceLog.error("Could not map entry to object: %s, %s", entry.getKey(), entry.getValue());
            }
        }
        return jSONObject;
    }

    public static Object wrap(Object obj) {
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof JSONArray) && !(obj instanceof JSONObject) && !obj.equals(JSONObject.NULL)) {
            try {
                if (obj instanceof Collection) {
                    obj = new JSONArray((Collection) obj);
                } else if (obj.getClass().isArray()) {
                    obj = new JSONArray((Collection) Arrays.asList(obj));
                } else if (obj instanceof Map) {
                    obj = new JSONObject((Map) obj);
                } else if (!(obj instanceof Boolean) && !(obj instanceof Byte) && !(obj instanceof Character) && !(obj instanceof Double) && !(obj instanceof Float) && !(obj instanceof Integer) && !(obj instanceof Long) && !(obj instanceof Short) && !(obj instanceof String)) {
                    if ((obj instanceof Enum) || obj.getClass().getPackage().getName().startsWith("java.")) {
                        obj = obj.toString();
                    }
                    return null;
                }
                return obj;
            } catch (Exception e) {
            }
        } else {
            return obj;
        }
    }
}
