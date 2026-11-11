package com.c.a.a.a;

import android.app.Activity;
import android.content.Context;
import java.util.Iterator;

/* JADX INFO: compiled from: AvidManager.java */
/* JADX INFO: loaded from: classes.dex */
public class h implements e, com.c.a.a.a.e.b, k {
    private static h ciU = new h();
    private static Context context;

    public static h aiF() {
        return ciU;
    }

    public void init(Context context2) {
        if (context == null) {
            context = context2.getApplicationContext();
            i.aiH().init(context);
            com.c.a.a.a.e.a.aiW().a(this);
            com.c.a.a.a.g.b.init(context);
        }
    }

    public void a(com.c.a.a.a.f.a aVar, com.c.a.a.a.f.a.a aVar2) {
        com.c.a.a.a.e.a.aiW().a(aVar, aVar2);
    }

    public com.c.a.a.a.f.a.a iN(String str) {
        return com.c.a.a.a.e.a.aiW().iN(str);
    }

    public void r(Activity activity) {
        com.c.a.a.a.a.a.aiT().s(activity);
    }

    private void start() {
        i.aiH().a(this);
        i.aiH().start();
        if (i.aiH().isActive()) {
            l.aiL().start();
        }
    }

    private void stop() {
        com.c.a.a.a.a.a.aiT().bM();
        l.aiL().stop();
        i.aiH().stop();
        c.aiz().aiA();
        context = null;
    }

    private boolean isActive() {
        return !com.c.a.a.a.e.a.aiW().isEmpty();
    }

    private void aiG() {
        com.c.a.a.a.e.a.aiW().a((com.c.a.a.a.e.b) null);
        Iterator<com.c.a.a.a.f.a.a> it = com.c.a.a.a.e.a.aiW().aiX().iterator();
        while (it.hasNext()) {
            it.next().ajf().ajp();
        }
        com.c.a.a.a.e.a.aiW().a(this);
    }

    @Override // com.c.a.a.a.e
    public void aiE() {
        if (isActive()) {
            aiG();
            if (com.c.a.a.a.e.a.aiW().aiY()) {
                start();
            }
        }
    }

    @Override // com.c.a.a.a.k
    public void ci(boolean z) {
        if (z) {
            l.aiL().start();
        } else {
            l.aiL().pause();
        }
    }

    @Override // com.c.a.a.a.e.b
    public void a(com.c.a.a.a.e.a aVar) {
        if (!aVar.isEmpty() && !a.ait()) {
            c.aiz().a(this);
            c.aiz().aY(context);
        }
    }

    @Override // com.c.a.a.a.e.b
    public void b(com.c.a.a.a.e.a aVar) {
        if (aVar.aiY() && a.ait()) {
            start();
        } else {
            stop();
        }
    }
}
