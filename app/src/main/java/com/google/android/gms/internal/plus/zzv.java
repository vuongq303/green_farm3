package com.google.android.gms.internal.plus;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
public final class zzv implements Parcelable.Creator<zzr.zzb.zza> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzr.zzb.zza createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        HashSet hashSet = new HashSet();
        int iD = 0;
        int i = 0;
        int i2 = 0;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 1:
                    int iD2 = a.d(parcel, iV);
                    hashSet.add(1);
                    i2 = iD2;
                    break;
                case 2:
                    int iD3 = a.d(parcel, iV);
                    hashSet.add(2);
                    i = iD3;
                    break;
                case 3:
                    iD = a.d(parcel, iV);
                    hashSet.add(3);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        if (parcel.dataPosition() != iW) {
            throw new b(new StringBuilder(37).append("Overread allowed size end=").append(iW).toString(), parcel);
        }
        return new zzr.zzb.zza(hashSet, i2, i, iD);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzr.zzb.zza[] newArray(int i) {
        return new zzr.zzb.zza[i];
    }
}
