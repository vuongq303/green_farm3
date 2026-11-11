package com.google.android.gms.common.api.a;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.e;

/* JADX INFO: loaded from: classes.dex */
public final class bt<O extends com.google.android.gms.common.api.e> extends am {
    private final com.google.android.gms.common.api.t<O> bLm;

    public bt(com.google.android.gms.common.api.t<O> tVar) {
        super("Method is not supported by connectionless client. APIs supporting connectionless client must not call this method.");
        this.bLm = tVar;
    }

    @Override // com.google.android.gms.common.api.w
    public final <A extends com.google.android.gms.common.api.c, R extends com.google.android.gms.common.api.ae, T extends e<R, A>> T a(T t) {
        return (T) this.bLm.doRead(t);
    }

    @Override // com.google.android.gms.common.api.w
    public final void a(ck ckVar) {
    }

    @Override // com.google.android.gms.common.api.w
    public final <A extends com.google.android.gms.common.api.c, T extends e<? extends com.google.android.gms.common.api.ae, A>> T b(T t) {
        return (T) this.bLm.doWrite(t);
    }

    @Override // com.google.android.gms.common.api.w
    public final void b(ck ckVar) {
    }

    @Override // com.google.android.gms.common.api.w
    public final Context getContext() {
        return this.bLm.getApplicationContext();
    }

    @Override // com.google.android.gms.common.api.w
    public final Looper getLooper() {
        return this.bLm.getLooper();
    }
}
