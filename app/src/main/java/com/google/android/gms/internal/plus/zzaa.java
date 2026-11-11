package com.google.android.gms.internal.plus;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
public final class zzaa implements Parcelable.Creator<zzr.zzf> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzr.zzf createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        HashSet hashSet = new HashSet();
        String strN = null;
        boolean zC = false;
        int i = 0;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 1:
                    int iD = a.d(parcel, iV);
                    hashSet.add(1);
                    i = iD;
                    break;
                case 2:
                    zC = a.c(parcel, iV);
                    hashSet.add(2);
                    break;
                case 3:
                    strN = a.n(parcel, iV);
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
        return new zzr.zzf(hashSet, i, zC, strN);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzr.zzf[] newArray(int i) {
        return new zzr.zzf[i];
    }
}
