package com.google.android.gms.games.snapshot;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.R;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.PlayerEntity;

/* JADX INFO: loaded from: classes.dex */
public final class l implements Parcelable.Creator<SnapshotMetadataEntity> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ SnapshotMetadataEntity createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        GameEntity gameEntity = null;
        PlayerEntity playerEntity = null;
        String strN = null;
        Uri uri = null;
        String strN2 = null;
        String strN3 = null;
        String strN4 = null;
        long jF = 0;
        long jF2 = 0;
        float fI = 0.0f;
        String strN5 = null;
        boolean zC = false;
        long jF3 = 0;
        String strN6 = null;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 1:
                    gameEntity = (GameEntity) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, GameEntity.CREATOR);
                    break;
                case 2:
                    playerEntity = (PlayerEntity) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, PlayerEntity.CREATOR);
                    break;
                case 3:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 4:
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
                case 5:
                    uri = (Uri) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, Uri.CREATOR);
                    break;
                case 6:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 7:
                    strN3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 8:
                    strN4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 9:
                    jF = com.google.android.gms.common.internal.safeparcel.a.f(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    jF2 = com.google.android.gms.common.internal.safeparcel.a.f(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingTop /* 11 */:
                    fI = com.google.android.gms.common.internal.safeparcel.a.i(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingBottom /* 12 */:
                    strN5 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 13:
                    zC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV);
                    break;
                case 14:
                    jF3 = com.google.android.gms.common.internal.safeparcel.a.f(parcel, iV);
                    break;
                case 15:
                    strN6 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new SnapshotMetadataEntity(gameEntity, playerEntity, strN, uri, strN2, strN3, strN4, jF, jF2, fI, strN5, zC, jF3, strN6);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ SnapshotMetadataEntity[] newArray(int i) {
        return new SnapshotMetadataEntity[i];
    }
}
