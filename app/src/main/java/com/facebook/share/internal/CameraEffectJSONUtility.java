package com.facebook.share.internal;

import com.facebook.share.model.CameraEffectArguments;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class CameraEffectJSONUtility {
    private static final Map<Class<?>, Setter> SETTERS = new HashMap();

    public interface Setter {
        void setOnArgumentsBuilder(CameraEffectArguments.Builder builder, String str, Object obj);

        void setOnJSON(JSONObject jSONObject, String str, Object obj);
    }

    static {
        SETTERS.put(String.class, new Setter() { // from class: com.facebook.share.internal.CameraEffectJSONUtility.1
            @Override // com.facebook.share.internal.CameraEffectJSONUtility.Setter
            public void setOnArgumentsBuilder(CameraEffectArguments.Builder builder, String str, Object obj) {
                builder.putArgument(str, (String) obj);
            }

            @Override // com.facebook.share.internal.CameraEffectJSONUtility.Setter
            public void setOnJSON(JSONObject jSONObject, String str, Object obj) throws JSONException {
                jSONObject.put(str, obj);
            }
        });
        SETTERS.put(String[].class, new Setter() { // from class: com.facebook.share.internal.CameraEffectJSONUtility.2
            @Override // com.facebook.share.internal.CameraEffectJSONUtility.Setter
            public void setOnArgumentsBuilder(CameraEffectArguments.Builder builder, String str, Object obj) {
                throw new IllegalArgumentException("Unexpected type from JSON");
            }

            @Override // com.facebook.share.internal.CameraEffectJSONUtility.Setter
            public void setOnJSON(JSONObject jSONObject, String str, Object obj) throws JSONException {
                JSONArray jSONArray = new JSONArray();
                for (String str2 : (String[]) obj) {
                    jSONArray.put(str2);
                }
                jSONObject.put(str, jSONArray);
            }
        });
        SETTERS.put(JSONArray.class, new Setter() { // from class: com.facebook.share.internal.CameraEffectJSONUtility.3
            @Override // com.facebook.share.internal.CameraEffectJSONUtility.Setter
            public void setOnArgumentsBuilder(CameraEffectArguments.Builder builder, String str, Object obj) throws JSONException {
                JSONArray jSONArray = (JSONArray) obj;
                String[] strArr = new String[jSONArray.length()];
                int i = 0;
                while (true) {
                    int i2 = i;
                    if (i2 < jSONArray.length()) {
                        Object obj2 = jSONArray.get(i2);
                        if (obj2 instanceof String) {
                            strArr[i2] = (String) obj2;
                            i = i2 + 1;
                        } else {
                            throw new IllegalArgumentException("Unexpected type in an array: " + obj2.getClass());
                        }
                    } else {
                        builder.putArgument(str, strArr);
                        return;
                    }
                }
            }

            @Override // com.facebook.share.internal.CameraEffectJSONUtility.Setter
            public void setOnJSON(JSONObject jSONObject, String str, Object obj) {
                throw new IllegalArgumentException("JSONArray's are not supported in bundles.");
            }
        });
    }

    public static JSONObject convertToJSON(CameraEffectArguments cameraEffectArguments) {
        if (cameraEffectArguments == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        for (String str : cameraEffectArguments.keySet()) {
            Object obj = cameraEffectArguments.get(str);
            if (obj != null) {
                Setter setter = SETTERS.get(obj.getClass());
                if (setter == null) {
                    throw new IllegalArgumentException("Unsupported type: " + obj.getClass());
                }
                setter.setOnJSON(jSONObject, str, obj);
            }
        }
        return jSONObject;
    }

    public static CameraEffectArguments convertToCameraEffectArguments(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return null;
        }
        CameraEffectArguments.Builder builder = new CameraEffectArguments.Builder();
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            Object obj = jSONObject.get(next);
            if (obj != null && obj != JSONObject.NULL) {
                Setter setter = SETTERS.get(obj.getClass());
                if (setter == null) {
                    throw new IllegalArgumentException("Unsupported type: " + obj.getClass());
                }
                setter.setOnArgumentsBuilder(builder, next, obj);
            }
        }
        return builder.build();
    }
}
