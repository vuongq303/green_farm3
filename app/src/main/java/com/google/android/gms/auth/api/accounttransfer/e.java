package com.google.android.gms.auth.api.accounttransfer;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
public final class e implements Parcelable.Creator<zzw> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzw createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        HashSet hashSet = new HashSet();
        DeviceMetaData deviceMetaData = null;
        PendingIntent pendingIntent = null;
        byte[] bArrQ = null;
        int iD = 0;
        String strN = null;
        int iD2 = 0;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 1:
                    iD2 = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    hashSet.add(1);
                    break;
                case 2:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    hashSet.add(2);
                    break;
                case 3:
                    iD = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    hashSet.add(3);
                    break;
                case 4:
                    bArrQ = com.google.android.gms.common.internal.safeparcel.a.q(parcel, iV);
                    hashSet.add(4);
                    break;
                case 5:
                    PendingIntent pendingIntent2 = (PendingIntent) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, PendingIntent.CREATOR);
                    hashSet.add(5);
                    pendingIntent = pendingIntent2;
                    break;
                case 6:
                    DeviceMetaData deviceMetaData2 = (DeviceMetaData) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, DeviceMetaData.CREATOR);
                    hashSet.add(6);
                    deviceMetaData = deviceMetaData2;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
            }
        }
        if (parcel.dataPosition() != iW) {
            throw new com.google.android.gms.common.internal.safeparcel.b(new StringBuilder(37).append("Overread allowed size end=").append(iW).toString(), parcel);
        }
        return new zzw(hashSet, iD2, strN, iD, bArrQ, pendingIntent, deviceMetaData);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzw[] newArray(int i) {
        return new zzw[i];
    }
}
