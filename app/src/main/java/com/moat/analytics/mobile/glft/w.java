package com.moat.analytics.mobile.glft;

import android.app.Activity;
import android.webkit.WebView;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
class w extends b implements WebAdTracker {

    class a implements v.c<WebAdTracker> {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private static final com.moat.analytics.mobile.glft.a.b.a<Method> f22a;
        private static final com.moat.analytics.mobile.glft.a.b.a<Method> b;
        private static final com.moat.analytics.mobile.glft.a.b.a<Method> c;

        static {
            com.moat.analytics.mobile.glft.a.b.a<Method> aVarA = com.moat.analytics.mobile.glft.a.b.a.a();
            com.moat.analytics.mobile.glft.a.b.a<Method> aVarA2 = com.moat.analytics.mobile.glft.a.b.a.a();
            com.moat.analytics.mobile.glft.a.b.a<Method> aVarA3 = com.moat.analytics.mobile.glft.a.b.a.a();
            try {
                Method method = WebAdTracker.class.getMethod("startTracking", new Class[0]);
                Method method2 = WebAdTracker.class.getMethod("stopTracking", new Class[0]);
                Method method3 = WebAdTracker.class.getMethod("setActivity", Activity.class);
                aVarA = com.moat.analytics.mobile.glft.a.b.a.a(method);
                aVarA2 = com.moat.analytics.mobile.glft.a.b.a.a(method2);
                aVarA3 = com.moat.analytics.mobile.glft.a.b.a.a(method3);
            } catch (NoSuchMethodException e) {
                l.j(e);
            }
            f22a = aVarA;
            b = aVarA2;
            c = aVarA3;
        }

        a() {
        }

        @Override // com.moat.analytics.mobile.glft.v.c
        public Class<WebAdTracker> a() {
            return WebAdTracker.class;
        }
    }

    w(WebView webView) {
        super(webView, false, false);
        if (u.ajL().b()) {
            n.a(3, "MoatWebAdTracker", this, "In initialization method.");
        }
        super.b(webView);
    }

    @Override // com.moat.analytics.mobile.glft.b, com.moat.analytics.mobile.glft.NativeDisplayTracker
    public void startTracking() {
        boolean z = false;
        try {
            n.a(3, "MoatWebAdTracker", this, "In startTracking method.");
            super.startTracking();
            z = true;
        } catch (Exception e) {
            l.j(e);
        }
        n.a(3, "MoatWebAdTracker", this, "Attempt to start tracking ad impression was " + (z ? "" : "un") + "successful.");
    }

    @Override // com.moat.analytics.mobile.glft.b
    public void stopTracking() {
        boolean z = false;
        try {
            n.a(3, "MoatWebAdTracker", this, "In stopTracking method.");
            super.stopTracking();
            z = true;
        } catch (Exception e) {
            l.j(e);
        }
        n.a(3, "MoatWebAdTracker", this, "Attempt to stop tracking ad impression was " + (z ? "" : "un") + "successful.");
    }
}
