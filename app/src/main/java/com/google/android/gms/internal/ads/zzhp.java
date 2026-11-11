package com.google.android.gms.internal.ads;

import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public final class zzhp extends zzej implements zzho {
    zzhp(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.ads.internal.cache.ICacheService");
    }

    @Override // com.google.android.gms.internal.ads.zzho
    public final zzhi zza(zzhl zzhlVar) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(parcelObtainAndWriteInterfaceToken, zzhlVar);
        Parcel parcelTransactAndReadException = transactAndReadException(1, parcelObtainAndWriteInterfaceToken);
        zzhi zzhiVar = (zzhi) zzel.zza(parcelTransactAndReadException, zzhi.CREATOR);
        parcelTransactAndReadException.recycle();
        return zzhiVar;
    }
}
