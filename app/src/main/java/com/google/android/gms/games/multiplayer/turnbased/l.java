package com.google.android.gms.games.multiplayer.turnbased;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.R;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class l implements Parcelable.Creator<TurnBasedMatchEntity> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ TurnBasedMatchEntity createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        GameEntity gameEntity = null;
        String strN = null;
        String strN2 = null;
        long jF = 0;
        String strN3 = null;
        long jF2 = 0;
        String strN4 = null;
        int iD = 0;
        int iD2 = 0;
        int iD3 = 0;
        byte[] bArrQ = null;
        ArrayList arrayListC = null;
        String strN5 = null;
        byte[] bArrQ2 = null;
        int iD4 = 0;
        Bundle bundleP = null;
        int iD5 = 0;
        boolean zC = false;
        String strN6 = null;
        String strN7 = null;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 1:
                    gameEntity = (GameEntity) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, GameEntity.CREATOR);
                    break;
                case 2:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 3:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 4:
                    jF = com.google.android.gms.common.internal.safeparcel.a.f(parcel, iV);
                    break;
                case 5:
                    strN3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 6:
                    jF2 = com.google.android.gms.common.internal.safeparcel.a.f(parcel, iV);
                    break;
                case 7:
                    strN4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 8:
                    iD = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 9:
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    iD2 = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingTop /* 11 */:
                    iD3 = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingBottom /* 12 */:
                    bArrQ = com.google.android.gms.common.internal.safeparcel.a.q(parcel, iV);
                    break;
                case 13:
                    arrayListC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV, ParticipantEntity.CREATOR);
                    break;
                case 14:
                    strN5 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 15:
                    bArrQ2 = com.google.android.gms.common.internal.safeparcel.a.q(parcel, iV);
                    break;
                case 16:
                    iD4 = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
                    bundleP = com.google.android.gms.common.internal.safeparcel.a.p(parcel, iV);
                    break;
                case 18:
                    iD5 = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 19:
                    zC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV);
                    break;
                case 20:
                    strN6 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 21:
                    strN7 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new TurnBasedMatchEntity(gameEntity, strN, strN2, jF, strN3, jF2, strN4, iD, iD2, iD3, bArrQ, arrayListC, strN5, bArrQ2, iD4, bundleP, iD5, zC, strN6, strN7);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ TurnBasedMatchEntity[] newArray(int i) {
        return new TurnBasedMatchEntity[i];
    }
}
