package com.google.firebase.iid;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* JADX INFO: loaded from: classes.dex */
final class zzy extends Handler {
    private final /* synthetic */ zzx zzbrt;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzy(zzx zzxVar, Looper looper) {
        super(looper);
        this.zzbrt = zzxVar;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        this.zzbrt.zzb(message);
    }
}
