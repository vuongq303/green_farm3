package com.moat.analytics.mobile.glft;

import android.app.Application;
import android.content.Context;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
class j extends MoatAnalytics implements u.b {
    private boolean c = false;
    f ckY;
    WeakReference<Context> clb;
    private String d;

    j() {
    }

    private void a(MoatOptions moatOptions, Application application) {
        u.ajL().f();
        if (application == null) {
            return;
        }
        if (this.c) {
            n.a(3, "MoatAnalytics", this, "Moat SDK has already been started.");
            return;
        }
        this.clb = new WeakReference<>(application.getApplicationContext());
        this.c = true;
        a.b(application);
        u.ajL().a(this);
        if (moatOptions.disableAdIdCollection) {
            return;
        }
        p.a(application);
    }

    private void d() {
        if (this.ckY == null) {
            this.ckY = new f(a.ajG(), f.a.DISPLAY);
            this.ckY.a(this.d);
            n.a(3, "MoatAnalytics", this, "Preparing native display tracking with partner code " + this.d);
        }
    }

    boolean a() {
        return this.c;
    }

    @Override // com.moat.analytics.mobile.glft.u.b
    public void b() {
        if (this.d != null) {
            try {
                d();
            } catch (Exception e) {
                l.j(e);
            }
        }
    }

    @Override // com.moat.analytics.mobile.glft.u.b
    public void c() {
    }

    @Override // com.moat.analytics.mobile.glft.MoatAnalytics
    public void prepareNativeDisplayTracking(String str) {
        this.d = str;
        if (u.ajL().a() == u.d.OFF) {
            return;
        }
        try {
            d();
        } catch (Exception e) {
            l.j(e);
        }
    }

    @Override // com.moat.analytics.mobile.glft.MoatAnalytics
    public void start(Application application) {
        start(new MoatOptions(), application);
    }

    @Override // com.moat.analytics.mobile.glft.MoatAnalytics
    public void start(MoatOptions moatOptions, Application application) {
        try {
            a(moatOptions, application);
        } catch (Exception e) {
            l.j(e);
        }
    }
}
