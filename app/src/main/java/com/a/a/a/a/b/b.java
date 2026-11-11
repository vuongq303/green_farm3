package com.a.a.a.a.b;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public abstract class b {
    public static b a(c cVar, d dVar) {
        com.a.a.a.a.e.f.a();
        com.a.a.a.a.e.f.c(cVar, "AdSessionConfiguration is null");
        com.a.a.a.a.e.f.c(dVar, "AdSessionContext is null");
        return new i(cVar, dVar);
    }

    public abstract void R(View view);

    public abstract void finish();

    public abstract void start();
}
