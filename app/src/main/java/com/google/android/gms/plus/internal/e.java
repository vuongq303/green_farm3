package com.google.android.gms.plus.internal;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.common.internal.ak;
import com.google.android.gms.common.internal.al;
import com.google.android.gms.internal.plus.zza;
import com.google.android.gms.internal.plus.zzc;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class e extends zza implements d {
    e(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.plus.internal.IPlusService");
    }

    @Override // com.google.android.gms.plus.internal.d
    public final ak a(b bVar, int i, int i2, int i3, String str) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, bVar);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        parcelObtainAndWriteInterfaceToken.writeInt(i2);
        parcelObtainAndWriteInterfaceToken.writeInt(-1);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        Parcel parcelTransactAndReadException = transactAndReadException(16, parcelObtainAndWriteInterfaceToken);
        ak akVarJ = al.j(parcelTransactAndReadException.readStrongBinder());
        parcelTransactAndReadException.recycle();
        return akVarJ;
    }

    @Override // com.google.android.gms.plus.internal.d
    public final void a(b bVar) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, bVar);
        transactAndReadExceptionReturnVoid(19, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.plus.internal.d
    public final void a(b bVar, List<String> list) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(parcelObtainAndWriteInterfaceToken, bVar);
        parcelObtainAndWriteInterfaceToken.writeStringList(list);
        transactAndReadExceptionReturnVoid(34, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.plus.internal.d
    public final String aad() {
        Parcel parcelTransactAndReadException = transactAndReadException(5, obtainAndWriteInterfaceToken());
        String string = parcelTransactAndReadException.readString();
        parcelTransactAndReadException.recycle();
        return string;
    }

    @Override // com.google.android.gms.plus.internal.d
    public final void zza() {
        transactAndReadExceptionReturnVoid(6, obtainAndWriteInterfaceToken());
    }
}
