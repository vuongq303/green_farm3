package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;

/* JADX INFO: loaded from: classes.dex */
public final class zzatw implements Parcelable.Creator<zzatv> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzatv createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        int iD = 0;
        byte[] bArrQ = null;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 1:
                    iD = a.d(parcel, iV);
                    break;
                case 2:
                    bArrQ = a.q(parcel, iV);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        a.D(parcel, iW);
        return new zzatv(iD, bArrQ);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzatv[] newArray(int i) {
        return new zzatv[i];
    }
}
