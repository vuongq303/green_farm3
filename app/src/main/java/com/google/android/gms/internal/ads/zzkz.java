package com.google.android.gms.internal.ads;

import android.os.IBinder;

/* JADX INFO: loaded from: classes.dex */
public final class zzkz extends zzej implements zzkx {
    zzkz(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.ads.internal.client.IAdMetadataListener");
    }

    @Override // com.google.android.gms.internal.ads.zzkx
    public final void zzt() {
        transactAndReadExceptionReturnVoid(1, obtainAndWriteInterfaceToken());
    }
}
