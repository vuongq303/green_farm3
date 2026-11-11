package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;

/* JADX INFO: loaded from: classes.dex */
public final class zzlv implements Parcelable.Creator<zzlu> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzlu createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        int iD = 0;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 2:
                    iD = a.d(parcel, iV);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        a.D(parcel, iW);
        return new zzlu(iD);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzlu[] newArray(int i) {
        return new zzlu[i];
    }
}
