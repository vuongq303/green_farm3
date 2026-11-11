package com.moat.analytics.mobile.glft;

import android.app.Application;

/* JADX INFO: loaded from: classes.dex */
public abstract class MoatAnalytics {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static MoatAnalytics f5a = null;

    public static synchronized MoatAnalytics getInstance() {
        if (f5a == null) {
            try {
                f5a = new j();
            } catch (Exception e) {
                l.j(e);
                f5a = new t.a();
            }
        }
        return f5a;
    }

    public abstract void prepareNativeDisplayTracking(String str);

    public abstract void start(Application application);

    public abstract void start(MoatOptions moatOptions, Application application);
}
