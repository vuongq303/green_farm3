package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;

/* JADX INFO: loaded from: classes.dex */
public final class zzmv implements Parcelable.Creator<zzmu> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzmu createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        boolean zC = false;
        boolean zC2 = false;
        boolean zC3 = false;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 2:
                    zC3 = a.c(parcel, iV);
                    break;
                case 3:
                    zC2 = a.c(parcel, iV);
                    break;
                case 4:
                    zC = a.c(parcel, iV);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        a.D(parcel, iW);
        return new zzmu(zC3, zC2, zC);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzmu[] newArray(int i) {
        return new zzmu[i];
    }
}
