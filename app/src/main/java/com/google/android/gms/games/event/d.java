package com.google.android.gms.games.event;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.games.PlayerEntity;

/* JADX INFO: loaded from: classes.dex */
public final class d implements Parcelable.Creator<EventEntity> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ EventEntity createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        long jF = 0;
        boolean zC = false;
        String strN = null;
        PlayerEntity playerEntity = null;
        String strN2 = null;
        Uri uri = null;
        String strN3 = null;
        String strN4 = null;
        String strN5 = null;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 1:
                    strN5 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 2:
                    strN4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 3:
                    strN3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 4:
                    uri = (Uri) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, Uri.CREATOR);
                    break;
                case 5:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 6:
                    playerEntity = (PlayerEntity) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, PlayerEntity.CREATOR);
                    break;
                case 7:
                    jF = com.google.android.gms.common.internal.safeparcel.a.f(parcel, iV);
                    break;
                case 8:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 9:
                    zC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new EventEntity(strN5, strN4, strN3, uri, strN2, playerEntity, jF, strN, zC);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ EventEntity[] newArray(int i) {
        return new EventEntity[i];
    }
}
