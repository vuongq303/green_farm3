package com.google.android.gms.games.quest;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public final class g implements Parcelable.Creator<MilestoneEntity> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ MilestoneEntity createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        int iD = 0;
        String strN = null;
        byte[] bArrQ = null;
        long jF = 0;
        long jF2 = 0;
        String strN2 = null;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 1:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 2:
                    jF2 = com.google.android.gms.common.internal.safeparcel.a.f(parcel, iV);
                    break;
                case 3:
                    jF = com.google.android.gms.common.internal.safeparcel.a.f(parcel, iV);
                    break;
                case 4:
                    bArrQ = com.google.android.gms.common.internal.safeparcel.a.q(parcel, iV);
                    break;
                case 5:
                    iD = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 6:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new MilestoneEntity(strN2, jF2, jF, bArrQ, iD, strN);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ MilestoneEntity[] newArray(int i) {
        return new MilestoneEntity[i];
    }
}
