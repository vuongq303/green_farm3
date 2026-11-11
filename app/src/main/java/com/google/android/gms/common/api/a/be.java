package com.google.android.gms.common.api.a;

import android.os.RemoteException;
import android.os.TransactionTooLargeException;
import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
public abstract class be {
    private final int type;

    public be(int i) {
        this.type = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Status a(RemoteException remoteException) {
        StringBuilder sb = new StringBuilder();
        if (com.google.android.gms.common.d.s.abw() && (remoteException instanceof TransactionTooLargeException)) {
            sb.append("TransactionTooLargeException: ");
        }
        sb.append(remoteException.getLocalizedMessage());
        return new Status(8, sb.toString());
    }

    public abstract void a(ah ahVar, boolean z);

    public abstract void b(RuntimeException runtimeException);

    public abstract void d(m<?> mVar);

    public abstract void zza(Status status);
}
