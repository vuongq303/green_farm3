package com.moat.analytics.mobile.glft;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class q extends b implements NativeDisplayTracker {
    private final Map<String, String> e;

    class a implements v.c<NativeDisplayTracker> {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private static final com.moat.analytics.mobile.glft.a.b.a<Method> f15a;
        private static final com.moat.analytics.mobile.glft.a.b.a<Method> b;
        private static final com.moat.analytics.mobile.glft.a.b.a<Method> c;

        static {
            com.moat.analytics.mobile.glft.a.b.a<Method> aVarA = com.moat.analytics.mobile.glft.a.b.a.a();
            com.moat.analytics.mobile.glft.a.b.a<Method> aVarA2 = com.moat.analytics.mobile.glft.a.b.a.a();
            com.moat.analytics.mobile.glft.a.b.a<Method> aVarA3 = com.moat.analytics.mobile.glft.a.b.a.a();
            try {
                Method method = NativeDisplayTracker.class.getMethod("startTracking", new Class[0]);
                Method method2 = NativeDisplayTracker.class.getMethod("stopTracking", new Class[0]);
                Method method3 = NativeDisplayTracker.class.getMethod("setActivity", Activity.class);
                aVarA = com.moat.analytics.mobile.glft.a.b.a.a(method);
                aVarA2 = com.moat.analytics.mobile.glft.a.b.a.a(method2);
                aVarA3 = com.moat.analytics.mobile.glft.a.b.a.a(method3);
            } catch (NoSuchMethodException e) {
                l.j(e);
            }
            f15a = aVarA;
            b = aVarA2;
            c = aVarA3;
        }

        a() {
        }

        @Override // com.moat.analytics.mobile.glft.v.c
        public Class<NativeDisplayTracker> a() {
            return NativeDisplayTracker.class;
        }
    }

    q(View view, Map<String, String> map) {
        super(view, true, false);
        n.a(3, "MoatNativeDispTracker", this, "Initializing.");
        this.e = map;
        f fVar = ((j) j.getInstance()).ckY;
        if (fVar != null) {
            super.a(fVar.ckX);
            super.b(fVar.cet);
        }
        super.a(d());
    }

    private static String a(Map<String, String> map) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (int i = 0; i < 8; i++) {
            String str = "moatClientLevel" + i;
            if (map.containsKey(str)) {
                linkedHashMap.put(str, map.get(str));
            }
        }
        for (int i2 = 0; i2 < 8; i2++) {
            String str2 = "moatClientSlicer" + i2;
            if (map.containsKey(str2)) {
                linkedHashMap.put(str2, map.get(str2));
            }
        }
        for (String str3 : map.keySet()) {
            if (!linkedHashMap.containsKey(str3)) {
                linkedHashMap.put(str3, map.get(str3));
            }
        }
        return new JSONObject(linkedHashMap).toString();
    }

    private String d() {
        try {
            String strA = a(this.e);
            n.a(3, "MoatNativeDispTracker", this, "Parsed ad ids = " + strA);
            return "{\"adIds\":" + strA + ", \"adKey\":\"" + this.ceu + "\", \"adSize\":" + e() + "}";
        } catch (Exception e) {
            l.j(e);
            return "";
        }
    }

    private String e() {
        try {
            Rect rectAjH = super.ajH();
            int iWidth = rectAjH.width();
            int iHeight = rectAjH.height();
            HashMap map = new HashMap();
            map.put("width", Integer.toString(iWidth));
            map.put("height", Integer.toString(iHeight));
            return new JSONObject(map).toString();
        } catch (Exception e) {
            l.j(e);
            return null;
        }
    }

    @Override // com.moat.analytics.mobile.glft.b, com.moat.analytics.mobile.glft.NativeDisplayTracker
    public void startTracking() {
        boolean z = false;
        try {
            super.startTracking();
            z = true;
        } catch (Exception e) {
            l.j(e);
        }
        n.a(3, "MoatNativeDispTracker", this, "Attempt to start tracking ad was " + (z ? "" : "un") + "successful.");
    }

    @Override // com.moat.analytics.mobile.glft.b
    public void stopTracking() {
        try {
            n.a(3, "MoatNativeDispTracker", this, "Called stopTracking.");
            super.stopTracking();
        } catch (Exception e) {
            l.j(e);
        }
    }
}
