package com.google.android.gms.common;

import android.util.Log;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
@CheckReturnValue
class x {
    private static final x bRf = new x(true, null, null);
    final boolean bRg;
    private final String bRh;
    private final Throwable cause;

    x(boolean z, @Nullable String str, @Nullable Throwable th) {
        this.bRg = z;
        this.bRh = str;
        this.cause = th;
    }

    static x a(String str, i iVar, boolean z, boolean z2) {
        return new z(str, iVar, z, z2);
    }

    static x a(String str, Throwable th) {
        return new x(false, str, th);
    }

    static x abQ() {
        return bRf;
    }

    static x io(String str) {
        return new x(false, str, null);
    }

    @Nullable
    String getErrorMessage() {
        return this.bRh;
    }

    final void zzi() {
        if (this.bRg) {
            return;
        }
        if (this.cause != null) {
            Log.d("GoogleCertificatesRslt", getErrorMessage(), this.cause);
        } else {
            Log.d("GoogleCertificatesRslt", getErrorMessage());
        }
    }
}
