package com.google.android.gms.c;

import android.os.IBinder;
import android.os.IInterface;
import com.google.android.gms.internal.stable.zzb;

/* JADX INFO: loaded from: classes.dex */
public class b extends zzb implements a {
    public b() {
        super("com.google.android.gms.dynamic.IObjectWrapper");
    }

    public static a p(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamic.IObjectWrapper");
        return iInterfaceQueryLocalInterface instanceof a ? (a) iInterfaceQueryLocalInterface : new c(iBinder);
    }
}
