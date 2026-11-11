package com.google.android.gms.common.d;

import android.os.SystemClock;

/* JADX INFO: loaded from: classes.dex */
public class h implements e {
    private static final h bQt = new h();

    private h() {
    }

    public static e abr() {
        return bQt;
    }

    @Override // com.google.android.gms.common.d.e
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    @Override // com.google.android.gms.common.d.e
    public long elapsedRealtime() {
        return SystemClock.elapsedRealtime();
    }

    @Override // com.google.android.gms.common.d.e
    public long nanoTime() {
        return System.nanoTime();
    }
}
