package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;

/* JADX INFO: loaded from: classes.dex */
public final class zzaih implements Parcelable.Creator<zzaig> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzaig createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        String strN = null;
        int iD = 0;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 2:
                    strN = a.n(parcel, iV);
                    break;
                case 3:
                    iD = a.d(parcel, iV);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        a.D(parcel, iW);
        return new zzaig(strN, iD);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzaig[] newArray(int i) {
        return new zzaig[i];
    }
}
