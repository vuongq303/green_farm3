package com.google.android.gms.common.api;

import android.os.Looper;
import com.google.android.gms.common.internal.bf;

/* JADX INFO: loaded from: classes.dex */
public class v {
    private Looper zzcn;
    private com.google.android.gms.common.api.a.ac zzcp;

    public u YL() {
        if (this.zzcp == null) {
            this.zzcp = new com.google.android.gms.common.api.a.a();
        }
        if (this.zzcn == null) {
            this.zzcn = Looper.getMainLooper();
        }
        return new u(this.zzcp, this.zzcn);
    }

    public v a(Looper looper) {
        bf.d(looper, "Looper must not be null.");
        this.zzcn = looper;
        return this;
    }

    public v a(com.google.android.gms.common.api.a.ac acVar) {
        bf.d(acVar, "StatusExceptionMapper must not be null.");
        this.zzcp = acVar;
        return this;
    }
}
