package com.google.android.gms.internal.ads;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzaeo extends zzek implements zzaen {
    public zzaeo() {
        super("com.google.android.gms.ads.internal.request.IAdRequestService");
    }

    @Override // com.google.android.gms.internal.ads.zzek
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        zzaet zzaeuVar = null;
        zzaeq zzaesVar = null;
        zzaet zzaeuVar2 = null;
        switch (i) {
            case 1:
                zzaej zzaejVarZzb = zzb((zzaef) zzel.zza(parcel, zzaef.CREATOR));
                parcel2.writeNoException();
                zzel.zzb(parcel2, zzaejVarZzb);
                return true;
            case 2:
                zzaef zzaefVar = (zzaef) zzel.zza(parcel, zzaef.CREATOR);
                IBinder strongBinder = parcel.readStrongBinder();
                if (strongBinder != null) {
                    IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.google.android.gms.ads.internal.request.IAdResponseListener");
                    zzaesVar = iInterfaceQueryLocalInterface instanceof zzaeq ? (zzaeq) iInterfaceQueryLocalInterface : new zzaes(strongBinder);
                }
                zza(zzaefVar, zzaesVar);
                parcel2.writeNoException();
                return true;
            case 3:
            default:
                return false;
            case 4:
                zzaey zzaeyVar = (zzaey) zzel.zza(parcel, zzaey.CREATOR);
                IBinder strongBinder2 = parcel.readStrongBinder();
                if (strongBinder2 != null) {
                    IInterface iInterfaceQueryLocalInterface2 = strongBinder2.queryLocalInterface("com.google.android.gms.ads.internal.request.INonagonStreamingResponseListener");
                    zzaeuVar2 = iInterfaceQueryLocalInterface2 instanceof zzaet ? (zzaet) iInterfaceQueryLocalInterface2 : new zzaeu(strongBinder2);
                }
                zza(zzaeyVar, zzaeuVar2);
                parcel2.writeNoException();
                return true;
            case 5:
                zzaey zzaeyVar2 = (zzaey) zzel.zza(parcel, zzaey.CREATOR);
                IBinder strongBinder3 = parcel.readStrongBinder();
                if (strongBinder3 != null) {
                    IInterface iInterfaceQueryLocalInterface3 = strongBinder3.queryLocalInterface("com.google.android.gms.ads.internal.request.INonagonStreamingResponseListener");
                    zzaeuVar = iInterfaceQueryLocalInterface3 instanceof zzaet ? (zzaet) iInterfaceQueryLocalInterface3 : new zzaeu(strongBinder3);
                }
                zzb(zzaeyVar2, zzaeuVar);
                parcel2.writeNoException();
                return true;
        }
    }
}
