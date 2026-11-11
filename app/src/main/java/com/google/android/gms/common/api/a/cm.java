package com.google.android.gms.common.api.a;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
final class cm extends Handler {
    private final /* synthetic */ ck bLK;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public cm(ck ckVar, Looper looper) {
        super(looper);
        this.bLK = ckVar;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        switch (message.what) {
            case 0:
                com.google.android.gms.common.api.aa<?> aaVar = (com.google.android.gms.common.api.aa) message.obj;
                synchronized (this.bLK.zzfa) {
                    try {
                        if (aaVar == null) {
                            this.bLK.bLD.d(new Status(13, "Transform returned null"));
                        } else if (aaVar instanceof cb) {
                            this.bLK.bLD.d(((cb) aaVar).getStatus());
                        } else {
                            this.bLK.bLD.a(aaVar);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    break;
                }
                return;
            case 1:
                RuntimeException runtimeException = (RuntimeException) message.obj;
                String strValueOf = String.valueOf(runtimeException.getMessage());
                Log.e("TransformedResultImpl", strValueOf.length() != 0 ? "Runtime exception on the transformation worker thread: ".concat(strValueOf) : new String("Runtime exception on the transformation worker thread: "));
                throw runtimeException;
            default:
                Log.e("TransformedResultImpl", new StringBuilder(70).append("TransformationResultHandler received unknown message type: ").append(message.what).toString());
                return;
        }
    }
}
