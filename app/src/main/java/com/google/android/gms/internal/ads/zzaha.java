package com.google.android.gms.internal.ads;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.facebook.R;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.google.android.gms.c.b;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzaha extends zzek implements zzagz {
    public zzaha() {
        super("com.google.android.gms.ads.internal.reward.client.IRewardedVideoAd");
    }

    public static zzagz zzy(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.reward.client.IRewardedVideoAd");
        return iInterfaceQueryLocalInterface instanceof zzagz ? (zzagz) iInterfaceQueryLocalInterface : new zzahb(iBinder);
    }

    @Override // com.google.android.gms.internal.ads.zzek
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        zzagx zzagyVar = null;
        zzahe zzahgVar = null;
        switch (i) {
            case 1:
                zza((zzahk) zzel.zza(parcel, zzahk.CREATOR));
                parcel2.writeNoException();
                return true;
            case 2:
                show();
                parcel2.writeNoException();
                return true;
            case 3:
                IBinder strongBinder = parcel.readStrongBinder();
                if (strongBinder != null) {
                    IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.google.android.gms.ads.internal.reward.client.IRewardedVideoAdListener");
                    zzahgVar = iInterfaceQueryLocalInterface instanceof zzahe ? (zzahe) iInterfaceQueryLocalInterface : new zzahg(strongBinder);
                }
                zza(zzahgVar);
                parcel2.writeNoException();
                return true;
            case 4:
            case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case Constants.MAX_TREE_DEPTH /* 25 */:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            default:
                return false;
            case 5:
                boolean zIsLoaded = isLoaded();
                parcel2.writeNoException();
                zzel.zza(parcel2, zIsLoaded);
                return true;
            case 6:
                pause();
                parcel2.writeNoException();
                return true;
            case 7:
                resume();
                parcel2.writeNoException();
                return true;
            case 8:
                destroy();
                parcel2.writeNoException();
                return true;
            case 9:
                zzd(b.p(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case R.styleable.CardView_contentPaddingRight /* 10 */:
                zze(b.p(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case R.styleable.CardView_contentPaddingTop /* 11 */:
                zzf(b.p(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case R.styleable.CardView_contentPaddingBottom /* 12 */:
                String mediationAdapterClassName = getMediationAdapterClassName();
                parcel2.writeNoException();
                parcel2.writeString(mediationAdapterClassName);
                return true;
            case 13:
                setUserId(parcel.readString());
                parcel2.writeNoException();
                return true;
            case 14:
                zza(zzky.zzc(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case 15:
                Bundle bundleZzba = zzba();
                parcel2.writeNoException();
                zzel.zzb(parcel2, bundleZzba);
                return true;
            case 16:
                IBinder strongBinder2 = parcel.readStrongBinder();
                if (strongBinder2 != null) {
                    IInterface iInterfaceQueryLocalInterface2 = strongBinder2.queryLocalInterface("com.google.android.gms.ads.internal.reward.client.IRewardedAdSkuListener");
                    zzagyVar = iInterfaceQueryLocalInterface2 instanceof zzagx ? (zzagx) iInterfaceQueryLocalInterface2 : new zzagy(strongBinder2);
                }
                zza(zzagyVar);
                parcel2.writeNoException();
                return true;
            case 34:
                setImmersiveMode(zzel.zza(parcel));
                parcel2.writeNoException();
                return true;
        }
    }
}
