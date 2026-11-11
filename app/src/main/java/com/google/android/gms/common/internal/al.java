package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.internal.stable.zzb;

/* JADX INFO: loaded from: classes.dex */
public abstract class al extends zzb implements ak {
    public static ak j(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.ICancelToken");
        return iInterfaceQueryLocalInterface instanceof ak ? (ak) iInterfaceQueryLocalInterface : new am(iBinder);
    }

    @Override // com.google.android.gms.internal.stable.zzb
    protected boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i != 2) {
            return false;
        }
        cancel();
        return true;
    }
}
