package com.google.android.gms.internal.ads;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.c.a;
import com.google.android.gms.c.b;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzzk extends zzek implements zzzj {
    public zzzk() {
        super("com.google.android.gms.ads.internal.mediation.client.rtb.IRtbAdapter");
    }

    public static zzzj zzt(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.mediation.client.rtb.IRtbAdapter");
        return iInterfaceQueryLocalInterface instanceof zzzj ? (zzzj) iInterfaceQueryLocalInterface : new zzzl(iBinder);
    }

    @Override // com.google.android.gms.internal.ads.zzek
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        zzzm zzznVar;
        zzzh zzziVar = null;
        zzzf zzzgVar = null;
        switch (i) {
            case 1:
                a aVarP = b.p(parcel.readStrongBinder());
                String string = parcel.readString();
                Bundle bundle = (Bundle) zzel.zza(parcel, Bundle.CREATOR);
                IBinder strongBinder = parcel.readStrongBinder();
                if (strongBinder == null) {
                    zzznVar = null;
                } else {
                    IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.google.android.gms.ads.internal.mediation.client.rtb.ISignalsCallback");
                    zzznVar = iInterfaceQueryLocalInterface instanceof zzzm ? (zzzm) iInterfaceQueryLocalInterface : new zzzn(strongBinder);
                }
                zza(aVarP, string, bundle, zzznVar);
                parcel2.writeNoException();
                return true;
            case 2:
                zzzt zzztVarZznc = zznc();
                parcel2.writeNoException();
                zzel.zzb(parcel2, zzztVarZznc);
                return true;
            case 3:
                zzzt zzztVarZznd = zznd();
                parcel2.writeNoException();
                zzel.zzb(parcel2, zzztVarZznd);
                return true;
            case 4:
                byte[] bArrCreateByteArray = parcel.createByteArray();
                String string2 = parcel.readString();
                Bundle bundle2 = (Bundle) zzel.zza(parcel, Bundle.CREATOR);
                a aVarP2 = b.p(parcel.readStrongBinder());
                IBinder strongBinder2 = parcel.readStrongBinder();
                if (strongBinder2 != null) {
                    IInterface iInterfaceQueryLocalInterface2 = strongBinder2.queryLocalInterface("com.google.android.gms.ads.internal.mediation.client.rtb.IBannerCallback");
                    zzzgVar = iInterfaceQueryLocalInterface2 instanceof zzzf ? (zzzf) iInterfaceQueryLocalInterface2 : new zzzg(strongBinder2);
                }
                zza(bArrCreateByteArray, string2, bundle2, aVarP2, zzzgVar, zzxu.zzs(parcel.readStrongBinder()), (zzjn) zzel.zza(parcel, zzjn.CREATOR));
                parcel2.writeNoException();
                return true;
            case 5:
                zzlo videoController = getVideoController();
                parcel2.writeNoException();
                zzel.zza(parcel2, videoController);
                return true;
            case 6:
                byte[] bArrCreateByteArray2 = parcel.createByteArray();
                String string3 = parcel.readString();
                Bundle bundle3 = (Bundle) zzel.zza(parcel, Bundle.CREATOR);
                a aVarP3 = b.p(parcel.readStrongBinder());
                IBinder strongBinder3 = parcel.readStrongBinder();
                if (strongBinder3 != null) {
                    IInterface iInterfaceQueryLocalInterface3 = strongBinder3.queryLocalInterface("com.google.android.gms.ads.internal.mediation.client.rtb.IInterstitialCallback");
                    zzziVar = iInterfaceQueryLocalInterface3 instanceof zzzh ? (zzzh) iInterfaceQueryLocalInterface3 : new zzzi(strongBinder3);
                }
                zza(bArrCreateByteArray2, string3, bundle3, aVarP3, zzziVar, zzxu.zzs(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case 7:
                showInterstitial();
                parcel2.writeNoException();
                return true;
            default:
                return false;
        }
    }
}
