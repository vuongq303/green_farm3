package com.google.android.gms.common.api.a;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;

/* JADX INFO: loaded from: classes.dex */
public class h<R extends com.google.android.gms.common.api.ae> extends Handler {
    public h() {
        this(Looper.getMainLooper());
    }

    public h(Looper looper) {
        super(looper);
    }

    public final void a(com.google.android.gms.common.api.af<? super R> afVar, R r) {
        sendMessage(obtainMessage(1, new Pair(afVar, r)));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.os.Handler
    public void handleMessage(Message message) {
        switch (message.what) {
            case 1:
                Pair pair = (Pair) message.obj;
                com.google.android.gms.common.api.af afVar = (com.google.android.gms.common.api.af) pair.first;
                com.google.android.gms.common.api.ae aeVar = (com.google.android.gms.common.api.ae) pair.second;
                try {
                    afVar.a(aeVar);
                    return;
                } catch (RuntimeException e) {
                    g.zzb(aeVar);
                    throw e;
                }
            case 2:
                ((g) message.obj).zzb(Status.bIR);
                return;
            default:
                Log.wtf("BasePendingResult", new StringBuilder(45).append("Don't know how to handle message: ").append(message.what).toString(), new Exception());
                return;
        }
    }
}
