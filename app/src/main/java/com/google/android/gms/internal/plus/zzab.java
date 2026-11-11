package com.google.android.gms.internal.plus;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
public final class zzab implements Parcelable.Creator<zzr.zzg> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzr.zzg createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        HashSet hashSet = new HashSet();
        int iD = 0;
        String strN = null;
        int iD2 = 0;
        String strN2 = null;
        int iD3 = 0;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 1:
                    iD3 = a.d(parcel, iV);
                    hashSet.add(1);
                    break;
                case 2:
                default:
                    a.b(parcel, iV);
                    break;
                case 3:
                    iD = a.d(parcel, iV);
                    hashSet.add(3);
                    break;
                case 4:
                    strN = a.n(parcel, iV);
                    hashSet.add(4);
                    break;
                case 5:
                    strN2 = a.n(parcel, iV);
                    hashSet.add(5);
                    break;
                case 6:
                    iD2 = a.d(parcel, iV);
                    hashSet.add(6);
                    break;
            }
        }
        if (parcel.dataPosition() != iW) {
            throw new b(new StringBuilder(37).append("Overread allowed size end=").append(iW).toString(), parcel);
        }
        return new zzr.zzg(hashSet, iD3, strN2, iD2, strN, iD);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzr.zzg[] newArray(int i) {
        return new zzr.zzg[i];
    }
}
