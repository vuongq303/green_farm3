package com.google.android.gms.common.api.a;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* JADX INFO: loaded from: classes.dex */
final class y extends Handler {
    private final /* synthetic */ v bJT;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public y(v vVar, Looper looper) {
        super(looper);
        this.bJT = vVar;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        com.google.android.gms.common.internal.bf.ca(message.what == 1);
        this.bJT.b((x) message.obj);
    }
}
