package com.google.android.gms.games;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public final class v implements Parcelable.Creator<PlayerLevelInfo> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ PlayerLevelInfo createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        PlayerLevel playerLevel = null;
        PlayerLevel playerLevel2 = null;
        long jF = 0;
        long jF2 = 0;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 1:
                    jF2 = com.google.android.gms.common.internal.safeparcel.a.f(parcel, iV);
                    break;
                case 2:
                    jF = com.google.android.gms.common.internal.safeparcel.a.f(parcel, iV);
                    break;
                case 3:
                    playerLevel2 = (PlayerLevel) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, PlayerLevel.CREATOR);
                    break;
                case 4:
                    playerLevel = (PlayerLevel) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, PlayerLevel.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new PlayerLevelInfo(jF2, jF, playerLevel2, playerLevel);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ PlayerLevelInfo[] newArray(int i) {
        return new PlayerLevelInfo[i];
    }
}
