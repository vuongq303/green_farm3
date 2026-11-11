package com.google.android.gms.internal.ads;

import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public final class zzaty extends zzej implements zzatx {
    zzaty(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.gass.internal.IGassService");
    }

    @Override // com.google.android.gms.internal.ads.zzatx
    public final zzatv zza(zzatt zzattVar) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(parcelObtainAndWriteInterfaceToken, zzattVar);
        Parcel parcelTransactAndReadException = transactAndReadException(1, parcelObtainAndWriteInterfaceToken);
        zzatv zzatvVar = (zzatv) zzel.zza(parcelTransactAndReadException, zzatv.CREATOR);
        parcelTransactAndReadException.recycle();
        return zzatvVar;
    }
}
