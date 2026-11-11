package com.google.android.gms.common.internal;

import android.app.PendingIntent;
import android.os.Handler;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;

/* JADX INFO: loaded from: classes.dex */
final class q extends Handler {
    private final /* synthetic */ e bNy;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public q(e eVar, Looper looper) {
        super(looper);
        this.bNy = eVar;
    }

    private static void a(Message message) {
        e.h hVar = (e.h) message.obj;
        hVar.aaa();
        hVar.unregister();
    }

    private static boolean b(Message message) {
        return message.what == 2 || message.what == 1 || message.what == 7;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        if (this.bNy.mDisconnectCount.get() != message.arg1) {
            if (b(message)) {
                a(message);
                return;
            }
            return;
        }
        if ((message.what == 1 || message.what == 7 || message.what == 4 || message.what == 5) && !this.bNy.isConnecting()) {
            a(message);
            return;
        }
        if (message.what == 4) {
            this.bNy.zzrr = new ConnectionResult(message.arg2);
            if (this.bNy.zzcr() && !this.bNy.zzrs) {
                this.bNy.zza(3, (IInterface) null);
                return;
            }
            ConnectionResult connectionResult = this.bNy.zzrr != null ? this.bNy.zzrr : new ConnectionResult(8);
            this.bNy.mConnectionProgressReportCallbacks.d(connectionResult);
            this.bNy.onConnectionFailed(connectionResult);
            return;
        }
        if (message.what == 5) {
            ConnectionResult connectionResult2 = this.bNy.zzrr != null ? this.bNy.zzrr : new ConnectionResult(8);
            this.bNy.mConnectionProgressReportCallbacks.d(connectionResult2);
            this.bNy.onConnectionFailed(connectionResult2);
            return;
        }
        if (message.what == 3) {
            ConnectionResult connectionResult3 = new ConnectionResult(message.arg2, message.obj instanceof PendingIntent ? (PendingIntent) message.obj : null);
            this.bNy.mConnectionProgressReportCallbacks.d(connectionResult3);
            this.bNy.onConnectionFailed(connectionResult3);
            return;
        }
        if (message.what == 6) {
            this.bNy.zza(5, (IInterface) null);
            if (this.bNy.zzrn != null) {
                this.bNy.zzrn.onConnectionSuspended(message.arg2);
            }
            this.bNy.onConnectionSuspended(message.arg2);
            this.bNy.zza(5, 1, (IInterface) null);
            return;
        }
        if (message.what == 2 && !this.bNy.isConnected()) {
            a(message);
        } else if (b(message)) {
            ((e.h) message.obj).aab();
        } else {
            Log.wtf("GmsClient", new StringBuilder(45).append("Don't know how to handle message: ").append(message.what).toString(), new Exception());
        }
    }
}
