package com.google.android.gms.common.internal;

import android.content.Context;
import android.util.SparseIntArray;

/* JADX INFO: loaded from: classes.dex */
public class ag {
    private final SparseIntArray bOq;
    private com.google.android.gms.common.g bOr;

    public ag() {
        this(com.google.android.gms.common.e.YB());
    }

    public ag(com.google.android.gms.common.g gVar) {
        this.bOq = new SparseIntArray();
        bf.checkNotNull(gVar);
        this.bOr = gVar;
    }

    public int a(Context context, com.google.android.gms.common.api.l lVar) {
        int iIsGooglePlayServicesAvailable;
        bf.checkNotNull(context);
        bf.checkNotNull(lVar);
        if (!lVar.requiresGooglePlayServices()) {
            return 0;
        }
        int minApkVersion = lVar.getMinApkVersion();
        int i = this.bOq.get(minApkVersion, -1);
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        while (true) {
            if (i2 >= this.bOq.size()) {
                iIsGooglePlayServicesAvailable = i;
                break;
            }
            int iKeyAt = this.bOq.keyAt(i2);
            if (iKeyAt > minApkVersion && this.bOq.get(iKeyAt) == 0) {
                iIsGooglePlayServicesAvailable = 0;
                break;
            }
            i2++;
        }
        if (iIsGooglePlayServicesAvailable == -1) {
            iIsGooglePlayServicesAvailable = this.bOr.isGooglePlayServicesAvailable(context, minApkVersion);
        }
        this.bOq.put(minApkVersion, iIsGooglePlayServicesAvailable);
        return iIsGooglePlayServicesAvailable;
    }

    public void flush() {
        this.bOq.clear();
    }
}
