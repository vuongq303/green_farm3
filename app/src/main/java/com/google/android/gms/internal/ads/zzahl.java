package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;

/* JADX INFO: loaded from: classes.dex */
public final class zzahl implements Parcelable.Creator<zzahk> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzahk createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        String strN = null;
        zzjj zzjjVar = null;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 2:
                    zzjjVar = (zzjj) a.a(parcel, iV, zzjj.CREATOR);
                    break;
                case 3:
                    strN = a.n(parcel, iV);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        a.D(parcel, iW);
        return new zzahk(zzjjVar, strN);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzahk[] newArray(int i) {
        return new zzahk[i];
    }
}
