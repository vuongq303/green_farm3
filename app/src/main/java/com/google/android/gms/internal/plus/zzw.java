package com.google.android.gms.internal.plus;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
public final class zzw implements Parcelable.Creator<zzr.zzb.C0004zzb> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzr.zzb.C0004zzb createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        HashSet hashSet = new HashSet();
        String strN = null;
        int iD = 0;
        int iD2 = 0;
        int iD3 = 0;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 1:
                    iD3 = a.d(parcel, iV);
                    hashSet.add(1);
                    break;
                case 2:
                    iD2 = a.d(parcel, iV);
                    hashSet.add(2);
                    break;
                case 3:
                    strN = a.n(parcel, iV);
                    hashSet.add(3);
                    break;
                case 4:
                    iD = a.d(parcel, iV);
                    hashSet.add(4);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        if (parcel.dataPosition() != iW) {
            throw new b(new StringBuilder(37).append("Overread allowed size end=").append(iW).toString(), parcel);
        }
        return new zzr.zzb.C0004zzb(hashSet, iD3, iD2, strN, iD);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzr.zzb.C0004zzb[] newArray(int i) {
        return new zzr.zzb.C0004zzb[i];
    }
}
