package com.google.android.gms.common.api.a;

import android.os.IBinder;
import java.lang.ref.WeakReference;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes.dex */
final class cp implements IBinder.DeathRecipient, cq {
    private final WeakReference<g<?>> bLQ;
    private final WeakReference<com.google.android.gms.common.api.al> bLR;
    private final WeakReference<IBinder> bLS;

    private cp(g<?> gVar, com.google.android.gms.common.api.al alVar, IBinder iBinder) {
        this.bLR = new WeakReference<>(alVar);
        this.bLQ = new WeakReference<>(gVar);
        this.bLS = new WeakReference<>(iBinder);
    }

    /* synthetic */ cp(g gVar, com.google.android.gms.common.api.al alVar, IBinder iBinder, co coVar) {
        this(gVar, null, iBinder);
    }

    private final void zzcf() {
        g<?> gVar = this.bLQ.get();
        com.google.android.gms.common.api.al alVar = this.bLR.get();
        if (alVar != null && gVar != null) {
            alVar.remove(gVar.zzo().intValue());
        }
        IBinder iBinder = this.bLS.get();
        if (iBinder != null) {
            try {
                iBinder.unlinkToDeath(this, 0);
            } catch (NoSuchElementException e) {
            }
        }
    }

    @Override // com.google.android.gms.common.api.a.cq
    public final void b(g<?> gVar) {
        zzcf();
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        zzcf();
    }
}
