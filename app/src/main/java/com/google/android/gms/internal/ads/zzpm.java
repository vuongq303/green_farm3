package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;

/* JADX INFO: loaded from: classes.dex */
public final class zzpm implements Parcelable.Creator<zzpl> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzpl createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        zzmu zzmuVar = null;
        int iD = 0;
        boolean zC = false;
        int iD2 = 0;
        boolean zC2 = false;
        int iD3 = 0;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 1:
                    iD3 = a.d(parcel, iV);
                    break;
                case 2:
                    zC2 = a.c(parcel, iV);
                    break;
                case 3:
                    iD2 = a.d(parcel, iV);
                    break;
                case 4:
                    zC = a.c(parcel, iV);
                    break;
                case 5:
                    iD = a.d(parcel, iV);
                    break;
                case 6:
                    zzmuVar = (zzmu) a.a(parcel, iV, zzmu.CREATOR);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        a.D(parcel, iW);
        return new zzpl(iD3, zC2, iD2, zC, iD, zzmuVar);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzpl[] newArray(int i) {
        return new zzpl[i];
    }
}
