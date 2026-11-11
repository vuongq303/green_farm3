package com.google.android.gms.d;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.internal.stable.zzb;
import com.google.android.gms.internal.stable.zzc;

/* JADX INFO: loaded from: classes.dex */
public abstract class j extends zzb implements i {
    public static i q(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoader");
        return iInterfaceQueryLocalInterface instanceof i ? (i) iInterfaceQueryLocalInterface : new k(iBinder);
    }

    @Override // com.google.android.gms.internal.stable.zzb
    protected boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 1:
                int iA = a(com.google.android.gms.c.b.p(parcel.readStrongBinder()), parcel.readString());
                parcel2.writeNoException();
                parcel2.writeInt(iA);
                return true;
            case 2:
                com.google.android.gms.c.a aVarA = a(com.google.android.gms.c.b.p(parcel.readStrongBinder()), parcel.readString(), parcel.readInt());
                parcel2.writeNoException();
                zzc.zza(parcel2, aVarA);
                return true;
            case 3:
                int iA2 = a(com.google.android.gms.c.b.p(parcel.readStrongBinder()), parcel.readString(), zzc.zza(parcel));
                parcel2.writeNoException();
                parcel2.writeInt(iA2);
                return true;
            default:
                return false;
        }
    }
}
