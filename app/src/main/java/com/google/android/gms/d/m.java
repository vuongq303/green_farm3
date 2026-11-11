package com.google.android.gms.d;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.internal.stable.zzb;
import com.google.android.gms.internal.stable.zzc;

/* JADX INFO: loaded from: classes.dex */
public abstract class m extends zzb implements l {
    public static l r(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoaderV2");
        return iInterfaceQueryLocalInterface instanceof l ? (l) iInterfaceQueryLocalInterface : new n(iBinder);
    }

    @Override // com.google.android.gms.internal.stable.zzb
    protected boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 1:
                com.google.android.gms.c.a aVarA = a(com.google.android.gms.c.b.p(parcel.readStrongBinder()), parcel.readString(), parcel.createByteArray());
                parcel2.writeNoException();
                zzc.zza(parcel2, aVarA);
                return true;
            case 2:
                com.google.android.gms.c.a aVarA2 = a(com.google.android.gms.c.b.p(parcel.readStrongBinder()), parcel.readString(), parcel.readInt(), com.google.android.gms.c.b.p(parcel.readStrongBinder()));
                parcel2.writeNoException();
                zzc.zza(parcel2, aVarA2);
                return true;
            default:
                return false;
        }
    }
}
