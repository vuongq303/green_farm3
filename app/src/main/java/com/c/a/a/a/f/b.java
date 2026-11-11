package com.c.a.a.a.f;

import android.content.Context;
import com.c.a.a.a.f.a.g;
import com.c.a.a.a.h;

/* JADX INFO: compiled from: AvidAdSessionManager.java */
/* JADX INFO: loaded from: classes.dex */
public class b {
    public static c a(Context context, e eVar) {
        h.aiF().init(context);
        c cVar = new c();
        com.c.a.a.a.f.a.e eVar2 = new com.c.a.a.a.f.a.e(context, cVar.aiZ(), eVar);
        eVar2.onStart();
        h.aiF().a(cVar, eVar2);
        return cVar;
    }

    public static d b(Context context, e eVar) {
        h.aiF().init(context);
        d dVar = new d();
        g gVar = new g(context, dVar.aiZ(), eVar);
        gVar.onStart();
        h.aiF().a(dVar, gVar);
        return dVar;
    }
}
