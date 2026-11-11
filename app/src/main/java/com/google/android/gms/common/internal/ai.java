package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.internal.stable.zzb;
import com.google.android.gms.internal.stable.zzc;

/* JADX INFO: loaded from: classes.dex */
public abstract class ai extends zzb implements ah {
    public static ah i(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IAccountAccessor");
        return iInterfaceQueryLocalInterface instanceof ah ? (ah) iInterfaceQueryLocalInterface : new aj(iBinder);
    }

    @Override // com.google.android.gms.internal.stable.zzb
    protected boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i != 2) {
            return false;
        }
        Account account = getAccount();
        parcel2.writeNoException();
        zzc.zzb(parcel2, account);
        return true;
    }
}
