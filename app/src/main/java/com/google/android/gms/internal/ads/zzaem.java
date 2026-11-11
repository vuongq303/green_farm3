package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class zzaem implements Parcelable.Creator<zzael> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzael createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        boolean zC = false;
        ArrayList<String> arrayListA = null;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 2:
                    zC = a.c(parcel, iV);
                    break;
                case 3:
                    arrayListA = a.A(parcel, iV);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        a.D(parcel, iW);
        return new zzael(zC, arrayListA);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzael[] newArray(int i) {
        return new zzael[i];
    }
}
