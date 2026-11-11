package com.google.android.gms.common.internal;

import android.os.IBinder;
import com.google.android.gms.internal.stable.zza;

/* JADX INFO: loaded from: classes.dex */
public class am extends zza implements ak {
    am(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.ICancelToken");
    }

    @Override // com.google.android.gms.common.internal.ak
    public void cancel() {
        transactOneway(2, obtainAndWriteInterfaceToken());
    }
}
