package com.google.android.gms.internal.ads;

import android.os.IBinder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;

/* JADX INFO: loaded from: classes.dex */
public final class zzsl extends zzej implements zzsk {
    zzsl(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.ads.internal.httpcache.IHttpAssetsCacheService");
    }

    @Override // com.google.android.gms.internal.ads.zzsk
    public final ParcelFileDescriptor zza(zzsg zzsgVar) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(parcelObtainAndWriteInterfaceToken, zzsgVar);
        Parcel parcelTransactAndReadException = transactAndReadException(1, parcelObtainAndWriteInterfaceToken);
        ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) zzel.zza(parcelTransactAndReadException, ParcelFileDescriptor.CREATOR);
        parcelTransactAndReadException.recycle();
        return parcelFileDescriptor;
    }
}
