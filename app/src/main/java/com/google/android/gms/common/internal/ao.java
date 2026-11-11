package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.internal.stable.zzb;
import com.google.android.gms.internal.stable.zzc;

/* JADX INFO: loaded from: classes.dex */
public abstract class ao extends zzb implements an {
    public ao() {
        super("com.google.android.gms.common.internal.ICertData");
    }

    public static an k(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.ICertData");
        return iInterfaceQueryLocalInterface instanceof an ? (an) iInterfaceQueryLocalInterface : new ap(iBinder);
    }

    @Override // com.google.android.gms.internal.stable.zzb
    protected boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 1:
                com.google.android.gms.c.a aVarYE = YE();
                parcel2.writeNoException();
                zzc.zza(parcel2, aVarYE);
                return true;
            case 2:
                int iYF = YF();
                parcel2.writeNoException();
                parcel2.writeInt(iYF);
                return true;
            default:
                return false;
        }
    }
}
