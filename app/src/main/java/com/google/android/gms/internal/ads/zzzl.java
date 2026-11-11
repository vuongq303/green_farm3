package com.google.android.gms.internal.ads;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.c.a;

/* JADX INFO: loaded from: classes.dex */
public final class zzzl extends zzej implements zzzj {
    zzzl(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.ads.internal.mediation.client.rtb.IRtbAdapter");
    }

    @Override // com.google.android.gms.internal.ads.zzzj
    public final zzlo getVideoController() {
        Parcel parcelTransactAndReadException = transactAndReadException(5, obtainAndWriteInterfaceToken());
        zzlo zzloVarZze = zzlp.zze(parcelTransactAndReadException.readStrongBinder());
        parcelTransactAndReadException.recycle();
        return zzloVarZze;
    }

    @Override // com.google.android.gms.internal.ads.zzzj
    public final void showInterstitial() {
        transactAndReadExceptionReturnVoid(7, obtainAndWriteInterfaceToken());
    }

    @Override // com.google.android.gms.internal.ads.zzzj
    public final void zza(a aVar, String str, Bundle bundle, zzzm zzzmVar) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(parcelObtainAndWriteInterfaceToken, aVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        zzel.zza(parcelObtainAndWriteInterfaceToken, bundle);
        zzel.zza(parcelObtainAndWriteInterfaceToken, zzzmVar);
        transactAndReadExceptionReturnVoid(1, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.ads.zzzj
    public final void zza(byte[] bArr, String str, Bundle bundle, a aVar, zzzf zzzfVar, zzxt zzxtVar, zzjn zzjnVar) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeByteArray(bArr);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        zzel.zza(parcelObtainAndWriteInterfaceToken, bundle);
        zzel.zza(parcelObtainAndWriteInterfaceToken, aVar);
        zzel.zza(parcelObtainAndWriteInterfaceToken, zzzfVar);
        zzel.zza(parcelObtainAndWriteInterfaceToken, zzxtVar);
        zzel.zza(parcelObtainAndWriteInterfaceToken, zzjnVar);
        transactAndReadExceptionReturnVoid(4, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.ads.zzzj
    public final void zza(byte[] bArr, String str, Bundle bundle, a aVar, zzzh zzzhVar, zzxt zzxtVar) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        parcelObtainAndWriteInterfaceToken.writeByteArray(bArr);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        zzel.zza(parcelObtainAndWriteInterfaceToken, bundle);
        zzel.zza(parcelObtainAndWriteInterfaceToken, aVar);
        zzel.zza(parcelObtainAndWriteInterfaceToken, zzzhVar);
        zzel.zza(parcelObtainAndWriteInterfaceToken, zzxtVar);
        transactAndReadExceptionReturnVoid(6, parcelObtainAndWriteInterfaceToken);
    }

    @Override // com.google.android.gms.internal.ads.zzzj
    public final zzzt zznc() {
        Parcel parcelTransactAndReadException = transactAndReadException(2, obtainAndWriteInterfaceToken());
        zzzt zzztVar = (zzzt) zzel.zza(parcelTransactAndReadException, zzzt.CREATOR);
        parcelTransactAndReadException.recycle();
        return zzztVar;
    }

    @Override // com.google.android.gms.internal.ads.zzzj
    public final zzzt zznd() {
        Parcel parcelTransactAndReadException = transactAndReadException(3, obtainAndWriteInterfaceToken());
        zzzt zzztVar = (zzzt) zzel.zza(parcelTransactAndReadException, zzzt.CREATOR);
        parcelTransactAndReadException.recycle();
        return zzztVar;
    }
}
