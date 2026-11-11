package com.google.android.gms.internal.ads;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.c.a;

/* JADX INFO: loaded from: classes.dex */
public final class zzlf extends zzej implements zzld {
    zzlf(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.ads.internal.client.IClientApi");
    }

    @Override // com.google.android.gms.internal.ads.zzld
    public final zzkn createAdLoaderBuilder(a aVar, String str, zzxn zzxnVar, int i) {
        zzkn zzkpVar;
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(parcelObtainAndWriteInterfaceToken, aVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        zzel.zza(parcelObtainAndWriteInterfaceToken, zzxnVar);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        Parcel parcelTransactAndReadException = transactAndReadException(3, parcelObtainAndWriteInterfaceToken);
        IBinder strongBinder = parcelTransactAndReadException.readStrongBinder();
        if (strongBinder == null) {
            zzkpVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.IAdLoaderBuilder");
            zzkpVar = iInterfaceQueryLocalInterface instanceof zzkn ? (zzkn) iInterfaceQueryLocalInterface : new zzkp(strongBinder);
        }
        parcelTransactAndReadException.recycle();
        return zzkpVar;
    }

    @Override // com.google.android.gms.internal.ads.zzld
    public final zzaap createAdOverlay(a aVar) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(parcelObtainAndWriteInterfaceToken, aVar);
        Parcel parcelTransactAndReadException = transactAndReadException(8, parcelObtainAndWriteInterfaceToken);
        zzaap zzaapVarZzu = zzaaq.zzu(parcelTransactAndReadException.readStrongBinder());
        parcelTransactAndReadException.recycle();
        return zzaapVarZzu;
    }

    @Override // com.google.android.gms.internal.ads.zzld
    public final zzks createBannerAdManager(a aVar, zzjn zzjnVar, String str, zzxn zzxnVar, int i) {
        zzks zzkuVar;
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(parcelObtainAndWriteInterfaceToken, aVar);
        zzel.zza(parcelObtainAndWriteInterfaceToken, zzjnVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        zzel.zza(parcelObtainAndWriteInterfaceToken, zzxnVar);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        Parcel parcelTransactAndReadException = transactAndReadException(1, parcelObtainAndWriteInterfaceToken);
        IBinder strongBinder = parcelTransactAndReadException.readStrongBinder();
        if (strongBinder == null) {
            zzkuVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.IAdManager");
            zzkuVar = iInterfaceQueryLocalInterface instanceof zzks ? (zzks) iInterfaceQueryLocalInterface : new zzku(strongBinder);
        }
        parcelTransactAndReadException.recycle();
        return zzkuVar;
    }

    @Override // com.google.android.gms.internal.ads.zzld
    public final zzaaz createInAppPurchaseManager(a aVar) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(parcelObtainAndWriteInterfaceToken, aVar);
        Parcel parcelTransactAndReadException = transactAndReadException(7, parcelObtainAndWriteInterfaceToken);
        zzaaz zzaazVarZzw = zzaba.zzw(parcelTransactAndReadException.readStrongBinder());
        parcelTransactAndReadException.recycle();
        return zzaazVarZzw;
    }

    @Override // com.google.android.gms.internal.ads.zzld
    public final zzks createInterstitialAdManager(a aVar, zzjn zzjnVar, String str, zzxn zzxnVar, int i) {
        zzks zzkuVar;
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(parcelObtainAndWriteInterfaceToken, aVar);
        zzel.zza(parcelObtainAndWriteInterfaceToken, zzjnVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        zzel.zza(parcelObtainAndWriteInterfaceToken, zzxnVar);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        Parcel parcelTransactAndReadException = transactAndReadException(2, parcelObtainAndWriteInterfaceToken);
        IBinder strongBinder = parcelTransactAndReadException.readStrongBinder();
        if (strongBinder == null) {
            zzkuVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.IAdManager");
            zzkuVar = iInterfaceQueryLocalInterface instanceof zzks ? (zzks) iInterfaceQueryLocalInterface : new zzku(strongBinder);
        }
        parcelTransactAndReadException.recycle();
        return zzkuVar;
    }

    @Override // com.google.android.gms.internal.ads.zzld
    public final zzqa createNativeAdViewDelegate(a aVar, a aVar2) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(parcelObtainAndWriteInterfaceToken, aVar);
        zzel.zza(parcelObtainAndWriteInterfaceToken, aVar2);
        Parcel parcelTransactAndReadException = transactAndReadException(5, parcelObtainAndWriteInterfaceToken);
        zzqa zzqaVarZzi = zzqb.zzi(parcelTransactAndReadException.readStrongBinder());
        parcelTransactAndReadException.recycle();
        return zzqaVarZzi;
    }

    @Override // com.google.android.gms.internal.ads.zzld
    public final zzqf createNativeAdViewHolderDelegate(a aVar, a aVar2, a aVar3) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(parcelObtainAndWriteInterfaceToken, aVar);
        zzel.zza(parcelObtainAndWriteInterfaceToken, aVar2);
        zzel.zza(parcelObtainAndWriteInterfaceToken, aVar3);
        Parcel parcelTransactAndReadException = transactAndReadException(11, parcelObtainAndWriteInterfaceToken);
        zzqf zzqfVarZzj = zzqg.zzj(parcelTransactAndReadException.readStrongBinder());
        parcelTransactAndReadException.recycle();
        return zzqfVarZzj;
    }

    @Override // com.google.android.gms.internal.ads.zzld
    public final zzagz createRewardedVideoAd(a aVar, zzxn zzxnVar, int i) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(parcelObtainAndWriteInterfaceToken, aVar);
        zzel.zza(parcelObtainAndWriteInterfaceToken, zzxnVar);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        Parcel parcelTransactAndReadException = transactAndReadException(6, parcelObtainAndWriteInterfaceToken);
        zzagz zzagzVarZzy = zzaha.zzy(parcelTransactAndReadException.readStrongBinder());
        parcelTransactAndReadException.recycle();
        return zzagzVarZzy;
    }

    @Override // com.google.android.gms.internal.ads.zzld
    public final zzks createSearchAdManager(a aVar, zzjn zzjnVar, String str, int i) {
        zzks zzkuVar;
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(parcelObtainAndWriteInterfaceToken, aVar);
        zzel.zza(parcelObtainAndWriteInterfaceToken, zzjnVar);
        parcelObtainAndWriteInterfaceToken.writeString(str);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        Parcel parcelTransactAndReadException = transactAndReadException(10, parcelObtainAndWriteInterfaceToken);
        IBinder strongBinder = parcelTransactAndReadException.readStrongBinder();
        if (strongBinder == null) {
            zzkuVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.IAdManager");
            zzkuVar = iInterfaceQueryLocalInterface instanceof zzks ? (zzks) iInterfaceQueryLocalInterface : new zzku(strongBinder);
        }
        parcelTransactAndReadException.recycle();
        return zzkuVar;
    }

    @Override // com.google.android.gms.internal.ads.zzld
    public final zzlj getMobileAdsSettingsManager(a aVar) {
        zzlj zzllVar;
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(parcelObtainAndWriteInterfaceToken, aVar);
        Parcel parcelTransactAndReadException = transactAndReadException(4, parcelObtainAndWriteInterfaceToken);
        IBinder strongBinder = parcelTransactAndReadException.readStrongBinder();
        if (strongBinder == null) {
            zzllVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.IMobileAdsSettingManager");
            zzllVar = iInterfaceQueryLocalInterface instanceof zzlj ? (zzlj) iInterfaceQueryLocalInterface : new zzll(strongBinder);
        }
        parcelTransactAndReadException.recycle();
        return zzllVar;
    }

    @Override // com.google.android.gms.internal.ads.zzld
    public final zzlj getMobileAdsSettingsManagerWithClientJarVersion(a aVar, int i) {
        zzlj zzllVar;
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzel.zza(parcelObtainAndWriteInterfaceToken, aVar);
        parcelObtainAndWriteInterfaceToken.writeInt(i);
        Parcel parcelTransactAndReadException = transactAndReadException(9, parcelObtainAndWriteInterfaceToken);
        IBinder strongBinder = parcelTransactAndReadException.readStrongBinder();
        if (strongBinder == null) {
            zzllVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.IMobileAdsSettingManager");
            zzllVar = iInterfaceQueryLocalInterface instanceof zzlj ? (zzlj) iInterfaceQueryLocalInterface : new zzll(strongBinder);
        }
        parcelTransactAndReadException.recycle();
        return zzllVar;
    }
}
