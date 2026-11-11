package com.google.android.gms.games.request;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.R;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.PlayerEntity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class f implements Parcelable.Creator<GameRequestEntity> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ GameRequestEntity createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        GameEntity gameEntity = null;
        PlayerEntity playerEntity = null;
        byte[] bArrQ = null;
        String strN = null;
        ArrayList arrayListC = null;
        int iD = 0;
        long jF = 0;
        long jF2 = 0;
        Bundle bundleP = null;
        int iD2 = 0;
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
                    bArrQ = com.google.android.gms.common.internal.safeparcel.a.q(parcel, iV);
                    break;
                case 4:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 5:
                    arrayListC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV, PlayerEntity.CREATOR);
                    break;
                case 6:
                case 8:
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
                case 7:
                    iD = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 9:
                    jF = com.google.android.gms.common.internal.safeparcel.a.f(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    jF2 = com.google.android.gms.common.internal.safeparcel.a.f(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingTop /* 11 */:
                    bundleP = com.google.android.gms.common.internal.safeparcel.a.p(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingBottom /* 12 */:
                    iD2 = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new GameRequestEntity(gameEntity, playerEntity, bArrQ, strN, arrayListC, iD, jF, jF2, bundleP, iD2);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ GameRequestEntity[] newArray(int i) {
        return new GameRequestEntity[i];
    }
}
