package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
final class ag extends dc implements com.google.android.gms.games.a.m {
    private final com.google.android.gms.games.a.c bUs;
    private final com.google.android.gms.games.a.f bUt;

    ag(DataHolder dataHolder, DataHolder dataHolder2) {
        super(dataHolder2);
        com.google.android.gms.games.a.b bVar = new com.google.android.gms.games.a.b(dataHolder);
        try {
            if (bVar.getCount() > 0) {
                this.bUs = (com.google.android.gms.games.a.c) bVar.get(0).freeze();
            } else {
                this.bUs = null;
            }
            bVar.release();
            this.bUt = new com.google.android.gms.games.a.f(dataHolder2);
        } catch (Throwable th) {
            bVar.release();
            throw th;
        }
    }
}
