package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class zzair implements Parcelable.Creator<zzaiq> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzaiq createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        ArrayList<String> arrayListA = null;
        boolean zC = false;
        boolean zC2 = false;
        ArrayList<String> arrayListA2 = null;
        boolean zC3 = false;
        boolean zC4 = false;
        String strN = null;
        String strN2 = null;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 2:
                    strN2 = a.n(parcel, iV);
                    break;
                case 3:
                    strN = a.n(parcel, iV);
                    break;
                case 4:
                    zC4 = a.c(parcel, iV);
                    break;
                case 5:
                    zC3 = a.c(parcel, iV);
                    break;
                case 6:
                    arrayListA2 = a.A(parcel, iV);
                    break;
                case 7:
                    zC2 = a.c(parcel, iV);
                    break;
                case 8:
                    zC = a.c(parcel, iV);
                    break;
                case 9:
                    arrayListA = a.A(parcel, iV);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        a.D(parcel, iW);
        return new zzaiq(strN2, strN, zC4, zC3, arrayListA2, zC2, zC, arrayListA);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzaiq[] newArray(int i) {
        return new zzaiq[i];
    }
}
