package com.a.a.a.a.e;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.view.WindowManager;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class b {
    private static WindowManager ceF = null;
    private static String[] ceG = {"x", "y", "width", "height"};
    static float ceH = Resources.getSystem().getDisplayMetrics().density;

    /* JADX WARN: Code duplicated, block: B:17:0x0071  */
    private static c N(JSONObject jSONObject) {
        float fMax;
        float fMax2;
        if (Build.VERSION.SDK_INT < 17) {
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("childViews");
            if (jSONArrayOptJSONArray != null) {
                int length = jSONArrayOptJSONArray.length();
                fMax = 0.0f;
                fMax2 = 0.0f;
                for (int i = 0; i < length; i++) {
                    JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i);
                    if (jSONObjectOptJSONObject != null) {
                        double dOptDouble = jSONObjectOptJSONObject.optDouble("x");
                        double dOptDouble2 = jSONObjectOptJSONObject.optDouble("y");
                        double dOptDouble3 = jSONObjectOptJSONObject.optDouble("width");
                        double dOptDouble4 = jSONObjectOptJSONObject.optDouble("height");
                        fMax2 = Math.max(fMax2, (float) (dOptDouble + dOptDouble3));
                        fMax = Math.max(fMax, (float) (dOptDouble2 + dOptDouble4));
                    }
                }
            } else {
                fMax = 0.0f;
                fMax2 = 0.0f;
            }
        } else if (ceF != null) {
            Point point = new Point(0, 0);
            ceF.getDefaultDisplay().getRealSize(point);
            float fPa = pa(point.x);
            fMax = pa(point.y);
            fMax2 = fPa;
        } else {
            fMax = 0.0f;
            fMax2 = 0.0f;
        }
        return new c(fMax2, fMax);
    }

    public static JSONObject O(int i, int i2, int i3, int i4) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("x", pa(i));
            jSONObject.put("y", pa(i2));
            jSONObject.put("width", pa(i3));
            jSONObject.put("height", pa(i4));
        } catch (JSONException e) {
            d.a("Error with creating viewStateObject", e);
        }
        return jSONObject;
    }

    public static void O(JSONObject jSONObject) {
        c cVarN = N(jSONObject);
        try {
            jSONObject.put("width", cVarN.ceH);
            jSONObject.put("height", cVarN.cex);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void a(Context context) {
        if (context != null) {
            ceH = context.getResources().getDisplayMetrics().density;
            ceF = (WindowManager) context.getSystemService("window");
        }
    }

    public static void a(JSONObject jSONObject, List<String> list) {
        JSONArray jSONArray = new JSONArray();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            jSONArray.put(it.next());
        }
        try {
            jSONObject.put("isFriendlyObstructionFor", jSONArray);
        } catch (JSONException e) {
            d.a("Error with setting friendly obstruction", e);
        }
    }

    private static boolean a(JSONArray jSONArray, JSONArray jSONArray2) {
        if (jSONArray == null && jSONArray2 == null) {
            return true;
        }
        if (jSONArray == null || jSONArray2 == null) {
            return false;
        }
        return jSONArray.length() == jSONArray2.length();
    }

    public static void b(JSONObject jSONObject, String str, Object obj) {
        try {
            jSONObject.put(str, obj);
        } catch (JSONException e) {
            d.a("JSONException during JSONObject.put for name [" + str + "]", e);
        }
    }

    public static void b(JSONObject jSONObject, JSONObject jSONObject2) {
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

    public static boolean c(JSONObject jSONObject, JSONObject jSONObject2) {
        if (jSONObject == null && jSONObject2 == null) {
            return true;
        }
        if (jSONObject == null || jSONObject2 == null) {
            return false;
        }
        return d(jSONObject, jSONObject2) && e(jSONObject, jSONObject2) && f(jSONObject, jSONObject2) && g(jSONObject, jSONObject2);
    }

    private static boolean d(JSONObject jSONObject, JSONObject jSONObject2) {
        for (String str : ceG) {
            if (jSONObject.optDouble(str) != jSONObject2.optDouble(str)) {
                return false;
            }
        }
        return true;
    }

    private static boolean e(JSONObject jSONObject, JSONObject jSONObject2) {
        return jSONObject.optString("adSessionId", "").equals(jSONObject2.optString("adSessionId", ""));
    }

    private static boolean f(JSONObject jSONObject, JSONObject jSONObject2) {
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("isFriendlyObstructionFor");
        JSONArray jSONArrayOptJSONArray2 = jSONObject2.optJSONArray("isFriendlyObstructionFor");
        if (jSONArrayOptJSONArray == null && jSONArrayOptJSONArray2 == null) {
            return true;
        }
        if (!a(jSONArrayOptJSONArray, jSONArrayOptJSONArray2)) {
            return false;
        }
        for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
            if (!jSONArrayOptJSONArray.optString(i, "").equals(jSONArrayOptJSONArray2.optString(i, ""))) {
                return false;
            }
        }
        return true;
    }

    private static boolean g(JSONObject jSONObject, JSONObject jSONObject2) {
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("childViews");
        JSONArray jSONArrayOptJSONArray2 = jSONObject2.optJSONArray("childViews");
        if (jSONArrayOptJSONArray == null && jSONArrayOptJSONArray2 == null) {
            return true;
        }
        if (!a(jSONArrayOptJSONArray, jSONArrayOptJSONArray2)) {
            return false;
        }
        for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
            if (!c(jSONArrayOptJSONArray.optJSONObject(i), jSONArrayOptJSONArray2.optJSONObject(i))) {
                return false;
            }
        }
        return true;
    }

    public static void i(JSONObject jSONObject, String str) {
        try {
            jSONObject.put("adSessionId", str);
        } catch (JSONException e) {
            d.a("Error with setting ad session id", e);
        }
    }

    static float pa(int i) {
        return i / ceH;
    }
}
