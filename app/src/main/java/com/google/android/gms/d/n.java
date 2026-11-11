package com.google.android.gms.d;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.internal.stable.zza;
import com.google.android.gms.internal.stable.zzc;

/* JADX INFO: loaded from: classes.dex */
public class n extends zza implements l {
    n(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.dynamite.IDynamiteLoaderV2");
    }

    @Override // com.google.android.gms.d.l
    public com.google.android.gms.c.a a(com.google.android.gms.c.a aVar, String str, int i, com.google.android.gms.c.a aVar2) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, aVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        zzc.zza(parcelObtainAndWriteInterfaceToken, aVar2);
        Parcel parcelTransactAndReadException = transactAndReadException(2, parcelObtainAndWriteInterfaceToken);
        com.google.android.gms.c.a aVarP = com.google.android.gms.c.b.p(parcelTransactAndReadException.readStrongBinder());
        parcelTransactAndReadException.recycle();
        return aVarP;
    }

    @Override // com.google.android.gms.d.l
    public com.google.android.gms.c.a a(com.google.android.gms.c.a aVar, String str, byte[] bArr) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, aVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeByteArray(bArr);
        Parcel parcelTransactAndReadException = transactAndReadException(1, parcelObtainAndWriteInterfaceToken);
        com.google.android.gms.c.a aVarP = com.google.android.gms.c.b.p(parcelTransactAndReadException.readStrongBinder());
        parcelTransactAndReadException.recycle();
        return aVarP;
    }
}
