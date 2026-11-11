package com.google.android.gms.internal.ads;

import android.os.Parcel;
import com.google.android.gms.c.b;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzlk extends zzek implements zzlj {
    public zzlk() {
        super("com.google.android.gms.ads.internal.client.IMobileAdsSettingManager");
    }

    @Override // com.google.android.gms.internal.ads.zzek
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 1:
                zza();
                parcel2.writeNoException();
                return true;
            case 2:
                setAppVolume(parcel.readFloat());
                parcel2.writeNoException();
                return true;
            case 3:
                zzt(parcel.readString());
                parcel2.writeNoException();
                return true;
            case 4:
                setAppMuted(zzel.zza(parcel));
                parcel2.writeNoException();
                return true;
            case 5:
                zzb(b.p(parcel.readStrongBinder()), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 6:
                zza(parcel.readString(), b.p(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case 7:
                float fZzdo = zzdo();
                parcel2.writeNoException();
                parcel2.writeFloat(fZzdo);
                return true;
            case 8:
                boolean zZzdp = zzdp();
                parcel2.writeNoException();
                zzel.zza(parcel2, zZzdp);
                return true;
            default:
                return false;
        }
    }
}
