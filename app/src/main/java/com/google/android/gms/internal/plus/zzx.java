package com.google.android.gms.internal.plus;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
public final class zzx implements Parcelable.Creator<zzr.zzc> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzr.zzc createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        HashSet hashSet = new HashSet();
        int iD = 0;
        String strN = null;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 1:
                    iD = a.d(parcel, iV);
                    hashSet.add(1);
                    break;
                case 2:
                    strN = a.n(parcel, iV);
                    hashSet.add(2);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        if (parcel.dataPosition() != iW) {
            throw new b(new StringBuilder(37).append("Overread allowed size end=").append(iW).toString(), parcel);
        }
        return new zzr.zzc(hashSet, iD, strN);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzr.zzc[] newArray(int i) {
        return new zzr.zzc[i];
    }
}
