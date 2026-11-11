package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.common.GoogleCertificatesQuery;
import com.google.android.gms.internal.stable.zzb;
import com.google.android.gms.internal.stable.zzc;

/* JADX INFO: loaded from: classes.dex */
public abstract class ax extends zzb implements aw {
    public static aw n(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IGoogleCertificatesApi");
        return iInterfaceQueryLocalInterface instanceof aw ? (aw) iInterfaceQueryLocalInterface : new ay(iBinder);
    }

    @Override // com.google.android.gms.internal.stable.zzb
    protected boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 1:
                com.google.android.gms.c.a aVarAay = aay();
                parcel2.writeNoException();
                zzc.zza(parcel2, aVarAay);
                return true;
            case 2:
                com.google.android.gms.c.a aVarAaz = aaz();
                parcel2.writeNoException();
                zzc.zza(parcel2, aVarAaz);
                return true;
            case 3:
                boolean zA = a(parcel.readString(), com.google.android.gms.c.b.p(parcel.readStrongBinder()));
                parcel2.writeNoException();
                zzc.zza(parcel2, zA);
                return true;
            case 4:
                boolean zB = b(parcel.readString(), com.google.android.gms.c.b.p(parcel.readStrongBinder()));
                parcel2.writeNoException();
                zzc.zza(parcel2, zB);
                return true;
            case 5:
                boolean zA2 = a((GoogleCertificatesQuery) zzc.zza(parcel, GoogleCertificatesQuery.CREATOR), com.google.android.gms.c.b.p(parcel.readStrongBinder()));
                parcel2.writeNoException();
                zzc.zza(parcel2, zA2);
                return true;
            default:
                return false;
        }
    }
}
