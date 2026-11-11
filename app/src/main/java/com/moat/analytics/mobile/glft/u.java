package com.moat.analytics.mobile.glft;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.gameloft.glads.GLURLConnection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public class u {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static u f17a;
    private static final Queue<c> b = new ConcurrentLinkedQueue();
    private Handler c;
    private volatile d d = d.OFF;
    private volatile boolean e = false;
    private volatile boolean f = false;
    private volatile int g = GLURLConnection.HTTP_OK;
    private final AtomicBoolean h = new AtomicBoolean(false);
    private volatile long i = 0;
    private final AtomicInteger j = new AtomicInteger(0);
    private final AtomicBoolean k = new AtomicBoolean(false);
    private long l = 1800000;
    private long m = 60000;

    class a implements Runnable {
        private final Handler b;
        private final String c;
        private final e d;

        private a(String str, Handler handler, e eVar) {
            this.d = eVar;
            this.b = handler;
            this.c = "https://z.moatads.com/" + str + "/android/" + "0bbafd1ca55aa33048dfa8f9ed6e5644a7625d79".substring(0, 7) + "/status.json";
        }

        private void a() {
            String strB = b();
            final k kVar = new k(strB);
            u.this.e = kVar.a();
            u.this.f = kVar.b();
            u.this.g = kVar.c();
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.moat.analytics.mobile.glft.u.a.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        a.this.d.a(kVar);
                    } catch (Exception e) {
                        l.j(e);
                    }
                }
            });
            u.this.i = System.currentTimeMillis();
            u.this.k.compareAndSet(true, false);
            if (strB != null) {
                u.this.j.set(0);
            } else if (u.this.j.incrementAndGet() < 10) {
                u.this.a(u.this.m);
            }
        }

        private String b() {
            try {
                return o.jb(this.c + "?ts=" + System.currentTimeMillis() + "&v=2.1.0").b();
            } catch (Exception e) {
                return null;
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                a();
            } catch (Exception e) {
                l.j(e);
            }
            this.b.removeCallbacks(this);
            Looper looperMyLooper = Looper.myLooper();
            if (looperMyLooper != null) {
                looperMyLooper.quit();
            }
        }
    }

    interface b {
        void b();

        void c();
    }

    class c {
        final Long clj;
        final b clk;

        c(Long l, b bVar) {
            this.clj = l;
            this.clk = bVar;
        }
    }

    enum d {
        OFF,
        ON
    }

    interface e {
        void a(k kVar);
    }

    u() {
        try {
            this.c = new Handler(Looper.getMainLooper());
        } catch (Exception e2) {
            l.j(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final long j) {
        if (this.k.compareAndSet(false, true)) {
            if (this.e) {
                Log.d("MoatOnOff", "Performing status check.");
            }
            new Thread() { // from class: com.moat.analytics.mobile.glft.u.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    Looper.prepare();
                    Handler handler = new Handler();
                    handler.postDelayed(new a("GLFT", handler, new e() { // from class: com.moat.analytics.mobile.glft.u.1.1
                        @Override // com.moat.analytics.mobile.glft.u.e
                        public void a(k kVar) {
                            synchronized (u.b) {
                                if (u.this.d != kVar.ajK()) {
                                    u.this.d = kVar.ajK();
                                    if (u.this.d == d.ON && u.this.e) {
                                        Log.d("MoatOnOff", "Moat enabled - Version 2.1.0");
                                    }
                                    for (c cVar : u.b) {
                                        if (u.this.d == d.ON) {
                                            cVar.clk.b();
                                        } else {
                                            cVar.clk.c();
                                        }
                                    }
                                }
                                while (!u.b.isEmpty()) {
                                    u.b.remove();
                                }
                            }
                        }
                    }), j);
                    Looper.loop();
                }
            }.start();
        }
    }

    static synchronized u ajL() {
        if (f17a == null) {
            try {
                f17a = new u();
            } catch (Exception e2) {
                l.j(e2);
                f17a = new t.e();
            }
        }
        return f17a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h() {
        synchronized (b) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            Iterator<c> it = b.iterator();
            while (it.hasNext()) {
                if (jCurrentTimeMillis - it.next().clj.longValue() >= 60000) {
                    it.remove();
                }
            }
            if (b.size() >= 15) {
                for (int i = 0; i < 5; i++) {
                    b.remove();
                }
            }
        }
    }

    private void i() {
        if (this.h.compareAndSet(false, true)) {
            this.c.postDelayed(new Runnable() { // from class: com.moat.analytics.mobile.glft.u.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (u.b.size() > 0) {
                            u.this.h();
                            u.this.c.postDelayed(this, 60000L);
                        } else {
                            u.this.h.compareAndSet(true, false);
                            u.this.c.removeCallbacks(this);
                        }
                    } catch (Exception e2) {
                        l.j(e2);
                    }
                }
            }, 60000L);
        }
    }

    public d a() {
        return this.d;
    }

    public void a(b bVar) {
        if (this.d == d.ON) {
            bVar.b();
            return;
        }
        h();
        b.add(new c(Long.valueOf(System.currentTimeMillis()), bVar));
        i();
    }

    public boolean b() {
        return this.e;
    }

    public int c() {
        return this.g;
    }

    boolean e() {
        return this.f;
    }

    public void f() {
        if (System.currentTimeMillis() - this.i > this.l) {
            a(0L);
        }
    }
}
