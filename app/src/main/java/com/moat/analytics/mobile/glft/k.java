package com.moat.analytics.mobile.glft;

import android.os.Build;
import com.gameloft.glads.GLURLConnection;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class k {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private boolean f12a = false;
    private boolean b = false;
    private boolean c = false;
    private int d = GLURLConnection.HTTP_OK;

    public k(String str) {
        a(str);
    }

    private void a(String str) {
        int i;
        if (str == null) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString("sa");
            boolean zEquals = string.equals("0bbafd1ca55aa33048dfa8f9ed6e5644a7625d79");
            boolean zEquals2 = string.equals("8f1d08a2d6496191a5ebae8f0590f513e2619489");
            if ((string.equals("on") || zEquals || zEquals2) && !a(jSONObject)) {
                this.f12a = true;
                this.b = zEquals;
                this.c = zEquals2;
                if (this.c) {
                    this.b = true;
                }
            }
            if (!jSONObject.has("in") || (i = jSONObject.getInt("in")) < 100 || i > 1000) {
                return;
            }
            this.d = i;
        } catch (Exception e) {
            this.f12a = false;
            this.b = false;
            this.d = GLURLConnection.HTTP_OK;
        }
    }

    private boolean a(JSONObject jSONObject) {
        try {
            if (16 > Build.VERSION.SDK_INT) {
                return true;
            }
            if (jSONObject.has("ob")) {
                JSONArray jSONArray = jSONObject.getJSONArray("ob");
                int length = jSONArray.length();
                for (int i = 0; i < length; i++) {
                    if (jSONArray.getInt(i) == Build.VERSION.SDK_INT) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    private boolean e() {
        return this.f12a;
    }

    boolean a() {
        return this.b;
    }

    u.d ajK() {
        return e() ? u.d.ON : u.d.OFF;
    }

    boolean b() {
        return this.c;
    }

    int c() {
        return this.d;
    }
}
