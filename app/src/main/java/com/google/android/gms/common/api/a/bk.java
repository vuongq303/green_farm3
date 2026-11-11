package com.google.android.gms.common.api.a;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
final class bk extends Handler {
    private final /* synthetic */ bi bLg;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    bk(bi biVar, Looper looper) {
        super(looper);
        this.bLg = biVar;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        switch (message.what) {
            case 1:
                ((bj) message.obj).c(this.bLg);
                return;
            case 2:
                throw ((RuntimeException) message.obj);
            default:
                Log.w("GACStateManager", new StringBuilder(31).append("Unknown message id: ").append(message.what).toString());
                return;
        }
    }
}
