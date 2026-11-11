package com.c.a.a.a.g;

import android.content.Context;
import android.content.res.Resources;
import com.facebook.share.internal.ShareConstants;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: AvidJSONUtil.java */
/* JADX INFO: loaded from: classes.dex */
public class b {
    private static String[] ckk = {"x", "y", "width", "height"};
    static float density = Resources.getSystem().getDisplayMetrics().density;

    public static void init(Context context) {
        if (context != null) {
            density = context.getResources().getDisplayMetrics().density;
        }
    }

    public static JSONObject ajx() {
        return a(P(0, 0, 0, 0), d.ajy());
    }

    public static JSONObject a(JSONObject jSONObject, double d) {
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("timestamp", d);
            jSONObject2.put("rootView", jSONObject);
        } catch (JSONException e) {
            c.b("Error with creating treeJSONObject", e);
        }
        return jSONObject2;
    }

    public static JSONObject P(int i, int i2, int i3, int i4) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("x", pb(i));
            jSONObject.put("y", pb(i2));
            jSONObject.put("width", pb(i3));
            jSONObject.put("height", pb(i4));
        } catch (JSONException e) {
            c.b("Error with creating viewStateObject", e);
        }
        return jSONObject;
    }

    static float pb(int i) {
        return i / density;
    }

    public static void j(JSONObject jSONObject, String str) {
        try {
            jSONObject.put(ShareConstants.WEB_DIALOG_PARAM_ID, str);
        } catch (JSONException e) {
            c.b("Error with setting avid id", e);
        }
    }

    public static void b(JSONObject jSONObject, List<String> list) {
        JSONArray jSONArray = new JSONArray();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            jSONArray.put(it.next());
        }
        try {
            jSONObject.put("isFriendlyObstructionFor", jSONArray);
        } catch (JSONException e) {
            c.b("Error with setting friendly obstruction", e);
        }
    }

    public static void h(JSONObject jSONObject, JSONObject jSONObject2) {
        try {
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("childViews");
            if (jSONArrayOptJSONArray == null) {
                jSONArrayOptJSONArray = new JSONArray();
                jSONObject.put("childViews", jSONArrayOptJSONArray);
            }
            jSONArrayOptJSONArray.put(jSONObject2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void P(JSONObject jSONObject) {
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("childViews");
        if (jSONArrayOptJSONArray != null) {
            int length = jSONArrayOptJSONArray.length();
            int iMax = 0;
            int iMax2 = 0;
            for (int i = 0; i < length; i++) {
                JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i);
                if (jSONObjectOptJSONObject != null) {
                    int iOptInt = jSONObjectOptJSONObject.optInt("x");
                    int iOptInt2 = jSONObjectOptJSONObject.optInt("y");
                    int iOptInt3 = jSONObjectOptJSONObject.optInt("width");
                    int iOptInt4 = jSONObjectOptJSONObject.optInt("height");
                    iMax2 = Math.max(iMax2, iOptInt + iOptInt3);
                    iMax = Math.max(iMax, iOptInt4 + iOptInt2);
                }
            }
            try {
                jSONObject.put("width", iMax2);
                jSONObject.put("height", iMax);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean i(JSONObject jSONObject, JSONObject jSONObject2) {
        return jSONObject2 != null && j(jSONObject, jSONObject2) && k(jSONObject, jSONObject2) && l(jSONObject, jSONObject2) && m(jSONObject, jSONObject2);
    }

    private static boolean j(JSONObject jSONObject, JSONObject jSONObject2) {
        for (String str : ckk) {
            if (jSONObject.optDouble(str) != jSONObject2.optDouble(str)) {
                return false;
            }
        }
        return true;
    }

    private static boolean k(JSONObject jSONObject, JSONObject jSONObject2) {
        return jSONObject.optString(ShareConstants.WEB_DIALOG_PARAM_ID, "").equals(jSONObject2.optString(ShareConstants.WEB_DIALOG_PARAM_ID, ""));
    }

    private static boolean l(JSONObject jSONObject, JSONObject jSONObject2) {
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("isFriendlyObstructionFor");
        JSONArray jSONArrayOptJSONArray2 = jSONObject2.optJSONArray("isFriendlyObstructionFor");
        if (!b(jSONArrayOptJSONArray, jSONArrayOptJSONArray2)) {
            return false;
        }
        if (jSONArrayOptJSONArray == null) {
            return true;
        }
        for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
            if (!jSONArrayOptJSONArray.optString(i, "").equals(jSONArrayOptJSONArray2.optString(i, ""))) {
                return false;
            }
        }
        return true;
    }

    private static boolean m(JSONObject jSONObject, JSONObject jSONObject2) {
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("childViews");
        JSONArray jSONArrayOptJSONArray2 = jSONObject2.optJSONArray("childViews");
        if (!b(jSONArrayOptJSONArray, jSONArrayOptJSONArray2)) {
            return false;
        }
        if (jSONArrayOptJSONArray == null) {
            return true;
        }
        for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
            if (!i(jSONArrayOptJSONArray.optJSONObject(i), jSONArrayOptJSONArray2.optJSONObject(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean b(JSONArray jSONArray, JSONArray jSONArray2) {
        if (jSONArray == null && jSONArray2 == null) {
            return true;
        }
        if ((jSONArray != null || jSONArray2 == null) && (jSONArray == null || jSONArray2 != null)) {
            return jSONArray.length() == jSONArray2.length();
        }
        return false;
    }
}
