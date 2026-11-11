package com.google.android.gms.internal.measurement;

import android.content.ComponentName;

/* JADX INFO: loaded from: classes.dex */
final class zzjb implements Runnable {
    private final /* synthetic */ ComponentName val$name;
    private final /* synthetic */ zziz zzaqi;

    zzjb(zziz zzizVar, ComponentName componentName) {
        this.zzaqi = zzizVar;
        this.val$name = componentName;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzaqi.zzapy.onServiceDisconnected(this.val$name);
    }
}
