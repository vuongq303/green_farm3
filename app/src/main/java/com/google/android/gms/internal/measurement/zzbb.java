package com.google.android.gms.internal.measurement;

import android.content.ComponentName;

/* JADX INFO: loaded from: classes.dex */
final class zzbb implements Runnable {
    private final /* synthetic */ ComponentName val$name;
    private final /* synthetic */ zzaz zzwo;

    zzbb(zzaz zzazVar, ComponentName componentName) {
        this.zzwo = zzazVar;
        this.val$name = componentName;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzwo.zzwk.onServiceDisconnected(this.val$name);
    }
}
