package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.internal.stable.zzb;
import com.google.android.gms.internal.stable.zzc;

/* JADX INFO: loaded from: classes.dex */
public abstract class ba extends zzb implements az {
    public static az o(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IResolveAccountCallbacks");
        return iInterfaceQueryLocalInterface instanceof az ? (az) iInterfaceQueryLocalInterface : new bb(iBinder);
    }

    @Override // com.google.android.gms.internal.stable.zzb
    protected boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i != 2) {
            return false;
        }
        a((ResolveAccountResponse) zzc.zza(parcel, ResolveAccountResponse.CREATOR));
        parcel2.writeNoException();
        return true;
    }
}
