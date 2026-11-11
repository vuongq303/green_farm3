package com.moat.analytics.mobile.glft;

import android.content.Context;
import android.content.Intent;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
class h {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static final h f9a = new h();
    private ScheduledFuture<?> e;
    private ScheduledFuture<?> f;
    private final ScheduledExecutorService d = Executors.newScheduledThreadPool(1);
    private final Map<i, String> b = new WeakHashMap();
    private final Map<b, String> c = new WeakHashMap();

    private h() {
    }

    private void a(final Context context) {
        if (this.f == null || this.f.isDone()) {
            n.a(3, "JSUpdateLooper", this, "Starting metadata reporting loop");
            this.f = this.d.scheduleWithFixedDelay(new Runnable() { // from class: com.moat.analytics.mobile.glft.h.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        android.support.v4.content.i.h(context.getApplicationContext()).b(new Intent("UPDATE_METADATA"));
                        if (h.this.b.isEmpty()) {
                            h.this.f.cancel(true);
                        }
                    } catch (Exception e) {
                        l.j(e);
                    }
                }
            }, 0L, 50L, TimeUnit.MILLISECONDS);
        }
    }

    static h ajJ() {
        return f9a;
    }

    private void b(final Context context) {
        if (this.e == null || this.e.isDone()) {
            n.a(3, "JSUpdateLooper", this, "Starting view update loop");
            this.e = this.d.scheduleWithFixedDelay(new Runnable() { // from class: com.moat.analytics.mobile.glft.h.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        android.support.v4.content.i.h(context.getApplicationContext()).b(new Intent("UPDATE_VIEW_INFO"));
                        if (h.this.c.isEmpty()) {
                            n.a(3, "JSUpdateLooper", h.this, "No more active trackers");
                            h.this.e.cancel(true);
                        }
                    } catch (Exception e) {
                        l.j(e);
                    }
                }
            }, 0L, u.ajL().c(), TimeUnit.MILLISECONDS);
        }
    }

    void a(Context context, b bVar) {
        if (bVar != null) {
            n.a(3, "JSUpdateLooper", this, "addActiveTracker" + bVar.hashCode());
            if (this.c == null || this.c.containsKey(bVar)) {
                return;
            }
            this.c.put(bVar, "");
            b(context);
        }
    }

    void a(Context context, i iVar) {
        if (this.b == null || iVar == null) {
            return;
        }
        this.b.put(iVar, "");
        a(context);
    }

    void a(b bVar) {
        if (bVar != null) {
            n.a(3, "JSUpdateLooper", this, "removeActiveTracker" + bVar.hashCode());
            if (this.c != null) {
                this.c.remove(bVar);
            }
        }
    }

    void a(i iVar) {
        if (iVar != null) {
            n.a(3, "JSUpdateLooper", this, "removeSetupNeededBridge" + iVar.hashCode());
            if (this.b != null) {
                this.b.remove(iVar);
            }
        }
    }
}
