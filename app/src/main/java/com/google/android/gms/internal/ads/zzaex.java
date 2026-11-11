package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;

/* JADX INFO: loaded from: classes.dex */
public final class zzaex implements Parcelable.Creator<zzaev> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzaev createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        ParcelFileDescriptor parcelFileDescriptor = null;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 2:
                    parcelFileDescriptor = (ParcelFileDescriptor) a.a(parcel, iV, ParcelFileDescriptor.CREATOR);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        a.D(parcel, iW);
        return new zzaev(parcelFileDescriptor);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzaev[] newArray(int i) {
        return new zzaev[i];
    }
}
