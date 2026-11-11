package com.google.android.gms.common.api.a;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
final class bf extends Handler {
    private final /* synthetic */ bc bKW;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    bf(bc bcVar, Looper looper) {
        super(looper);
        this.bKW = bcVar;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        switch (message.what) {
            case 1:
                this.bKW.Ze();
                break;
            case 2:
                this.bKW.resume();
                break;
            default:
                Log.w("GoogleApiClientImpl", new StringBuilder(31).append("Unknown message id: ").append(message.what).toString());
                break;
        }
    }
}
