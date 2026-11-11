package com.google.android.gms.auth.a;

import android.os.IBinder;
import android.os.IInterface;
import com.google.android.gms.internal.auth.zze;

/* JADX INFO: loaded from: classes.dex */
public abstract class g extends zze implements f {
    public static f e(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.auth.account.IWorkAccountService");
        return iInterfaceQueryLocalInterface instanceof f ? (f) iInterfaceQueryLocalInterface : new h(iBinder);
    }
}
