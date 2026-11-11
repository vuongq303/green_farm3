package com.google.android.gms.internal.ads;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.facebook.R;
import com.google.android.gms.c.b;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzle extends zzek implements zzld {
    public zzle() {
        super("com.google.android.gms.ads.internal.client.IClientApi");
    }

    public static zzld asInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.IClientApi");
        return iInterfaceQueryLocalInterface instanceof zzld ? (zzld) iInterfaceQueryLocalInterface : new zzlf(iBinder);
    }

    @Override // com.google.android.gms.internal.ads.zzek
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 1:
                zzks zzksVarCreateBannerAdManager = createBannerAdManager(b.p(parcel.readStrongBinder()), (zzjn) zzel.zza(parcel, zzjn.CREATOR), parcel.readString(), zzxo.zzr(parcel.readStrongBinder()), parcel.readInt());
                parcel2.writeNoException();
                zzel.zza(parcel2, zzksVarCreateBannerAdManager);
                return true;
            case 2:
                zzks zzksVarCreateInterstitialAdManager = createInterstitialAdManager(b.p(parcel.readStrongBinder()), (zzjn) zzel.zza(parcel, zzjn.CREATOR), parcel.readString(), zzxo.zzr(parcel.readStrongBinder()), parcel.readInt());
                parcel2.writeNoException();
                zzel.zza(parcel2, zzksVarCreateInterstitialAdManager);
                return true;
            case 3:
                zzkn zzknVarCreateAdLoaderBuilder = createAdLoaderBuilder(b.p(parcel.readStrongBinder()), parcel.readString(), zzxo.zzr(parcel.readStrongBinder()), parcel.readInt());
                parcel2.writeNoException();
                zzel.zza(parcel2, zzknVarCreateAdLoaderBuilder);
                return true;
            case 4:
                zzlj mobileAdsSettingsManager = getMobileAdsSettingsManager(b.p(parcel.readStrongBinder()));
                parcel2.writeNoException();
                zzel.zza(parcel2, mobileAdsSettingsManager);
                return true;
            case 5:
                zzqa zzqaVarCreateNativeAdViewDelegate = createNativeAdViewDelegate(b.p(parcel.readStrongBinder()), b.p(parcel.readStrongBinder()));
                parcel2.writeNoException();
                zzel.zza(parcel2, zzqaVarCreateNativeAdViewDelegate);
                return true;
            case 6:
                zzagz zzagzVarCreateRewardedVideoAd = createRewardedVideoAd(b.p(parcel.readStrongBinder()), zzxo.zzr(parcel.readStrongBinder()), parcel.readInt());
                parcel2.writeNoException();
                zzel.zza(parcel2, zzagzVarCreateRewardedVideoAd);
                return true;
            case 7:
                zzaaz zzaazVarCreateInAppPurchaseManager = createInAppPurchaseManager(b.p(parcel.readStrongBinder()));
                parcel2.writeNoException();
                zzel.zza(parcel2, zzaazVarCreateInAppPurchaseManager);
                return true;
            case 8:
                zzaap zzaapVarCreateAdOverlay = createAdOverlay(b.p(parcel.readStrongBinder()));
                parcel2.writeNoException();
                zzel.zza(parcel2, zzaapVarCreateAdOverlay);
                return true;
            case 9:
                zzlj mobileAdsSettingsManagerWithClientJarVersion = getMobileAdsSettingsManagerWithClientJarVersion(b.p(parcel.readStrongBinder()), parcel.readInt());
                parcel2.writeNoException();
                zzel.zza(parcel2, mobileAdsSettingsManagerWithClientJarVersion);
                return true;
            case R.styleable.CardView_contentPaddingRight /* 10 */:
                zzks zzksVarCreateSearchAdManager = createSearchAdManager(b.p(parcel.readStrongBinder()), (zzjn) zzel.zza(parcel, zzjn.CREATOR), parcel.readString(), parcel.readInt());
                parcel2.writeNoException();
                zzel.zza(parcel2, zzksVarCreateSearchAdManager);
                return true;
            case R.styleable.CardView_contentPaddingTop /* 11 */:
                zzqf zzqfVarCreateNativeAdViewHolderDelegate = createNativeAdViewHolderDelegate(b.p(parcel.readStrongBinder()), b.p(parcel.readStrongBinder()), b.p(parcel.readStrongBinder()));
                parcel2.writeNoException();
                zzel.zza(parcel2, zzqfVarCreateNativeAdViewHolderDelegate);
                return true;
            default:
                return false;
        }
    }
}
