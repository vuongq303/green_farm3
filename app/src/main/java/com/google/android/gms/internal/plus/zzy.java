package com.google.android.gms.internal.plus;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
public final class zzy implements Parcelable.Creator<zzr.zzd> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzr.zzd createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        HashSet hashSet = new HashSet();
        int iD = 0;
        String strN = null;
        String strN2 = null;
        String strN3 = null;
        String strN4 = null;
        String strN5 = null;
        String strN6 = null;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 1:
                    iD = a.d(parcel, iV);
                    hashSet.add(1);
                    break;
                case 2:
                    strN6 = a.n(parcel, iV);
                    hashSet.add(2);
                    break;
                case 3:
                    strN5 = a.n(parcel, iV);
                    hashSet.add(3);
                    break;
                case 4:
                    strN4 = a.n(parcel, iV);
                    hashSet.add(4);
                    break;
                case 5:
                    strN3 = a.n(parcel, iV);
                    hashSet.add(5);
                    break;
                case 6:
                    strN2 = a.n(parcel, iV);
                    hashSet.add(6);
                    break;
                case 7:
                    strN = a.n(parcel, iV);
                    hashSet.add(7);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        if (parcel.dataPosition() != iW) {
            throw new b(new StringBuilder(37).append("Overread allowed size end=").append(iW).toString(), parcel);
        }
        return new zzr.zzd(hashSet, iD, strN6, strN5, strN4, strN3, strN2, strN);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzr.zzd[] newArray(int i) {
        return new zzr.zzd[i];
    }
}
