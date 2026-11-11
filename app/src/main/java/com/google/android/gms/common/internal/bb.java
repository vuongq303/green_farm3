package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.internal.stable.zza;
import com.google.android.gms.internal.stable.zzc;

/* JADX INFO: loaded from: classes.dex */
public class bb extends zza implements az {
    bb(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.IResolveAccountCallbacks");
    }

    @Override // com.google.android.gms.common.internal.az
    public void a(ResolveAccountResponse resolveAccountResponse) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, resolveAccountResponse);
        transactAndReadExceptionReturnVoid(2, parcelObtainAndWriteInterfaceToken);
    }
}
