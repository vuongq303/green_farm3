package com.google.android.gms.common;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.aw;
import com.google.android.gms.common.internal.ax;
import com.google.android.gms.common.internal.bf;
import javax.annotation.CheckReturnValue;

/* JADX INFO: loaded from: classes.dex */
@CheckReturnValue
final class h {
    private static volatile aw bIh;
    private static final Object bIi = new Object();
    private static Context bIj;

    static x a(String str, i iVar, boolean z) {
        try {
            zzc();
            bf.checkNotNull(bIj);
            try {
                if (bIh.a(new GoogleCertificatesQuery(str, iVar, z), com.google.android.gms.c.d.F(bIj.getPackageManager()))) {
                    return x.abQ();
                }
                return x.a(str, iVar, z, !z && a(str, iVar, true).bRg);
            } catch (RemoteException e) {
                Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
                return x.a("module call", e);
            }
        } catch (com.google.android.gms.d.c e2) {
            return x.a("module init", e2);
        }
    }

    static synchronized void init(Context context) {
        if (bIj != null) {
            Log.w("GoogleCertificates", "GoogleCertificates has been initialized already");
        } else if (context != null) {
            bIj = context.getApplicationContext();
        }
    }

    private static void zzc() {
        if (bIh != null) {
            return;
        }
        bf.checkNotNull(bIj);
        synchronized (bIi) {
            if (bIh == null) {
                bIh = ax.n(com.google.android.gms.d.a.a(bIj, com.google.android.gms.d.a.bRv, "com.google.android.gms.googlecertificates").ip("com.google.android.gms.common.GoogleCertificatesImpl"));
            }
        }
    }
}
