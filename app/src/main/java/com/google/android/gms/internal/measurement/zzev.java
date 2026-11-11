package com.google.android.gms.internal.measurement;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;

/* JADX INFO: loaded from: classes.dex */
public final class zzev implements Parcelable.Creator<zzeu> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzeu createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        long jF = 0;
        String strN = null;
        zzer zzerVar = null;
        String strN2 = null;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 2:
                    strN2 = a.n(parcel, iV);
                    break;
                case 3:
                    zzerVar = (zzer) a.a(parcel, iV, zzer.CREATOR);
                    break;
                case 4:
                    strN = a.n(parcel, iV);
                    break;
                case 5:
                    jF = a.f(parcel, iV);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        a.D(parcel, iW);
        return new zzeu(strN2, zzerVar, strN, jF);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzeu[] newArray(int i) {
        return new zzeu[i];
    }
}
