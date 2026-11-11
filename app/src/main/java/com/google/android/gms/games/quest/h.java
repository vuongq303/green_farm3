package com.google.android.gms.games.quest;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.R;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.google.android.gms.games.GameEntity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class h implements Parcelable.Creator<QuestEntity> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ QuestEntity createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        GameEntity gameEntity = null;
        String strN = null;
        long jF = 0;
        Uri uri = null;
        String strN2 = null;
        String strN3 = null;
        long jF2 = 0;
        long jF3 = 0;
        Uri uri2 = null;
        String strN4 = null;
        String strN5 = null;
        long jF4 = 0;
        long jF5 = 0;
        int iD = 0;
        int iD2 = 0;
        ArrayList arrayListC = null;
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
                    jF = com.google.android.gms.common.internal.safeparcel.a.f(parcel, iV);
                    break;
                case 4:
                    uri = (Uri) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, Uri.CREATOR);
                    break;
                case 5:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 6:
                    strN3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 7:
                    jF2 = com.google.android.gms.common.internal.safeparcel.a.f(parcel, iV);
                    break;
                case 8:
                    jF3 = com.google.android.gms.common.internal.safeparcel.a.f(parcel, iV);
                    break;
                case 9:
                    uri2 = (Uri) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, Uri.CREATOR);
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    strN4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingTop /* 11 */:
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingBottom /* 12 */:
                    strN5 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 13:
                    jF4 = com.google.android.gms.common.internal.safeparcel.a.f(parcel, iV);
                    break;
                case 14:
                    jF5 = com.google.android.gms.common.internal.safeparcel.a.f(parcel, iV);
                    break;
                case 15:
                    iD = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 16:
                    iD2 = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
                    arrayListC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV, MilestoneEntity.CREATOR);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new QuestEntity(gameEntity, strN, jF, uri, strN2, strN3, jF2, jF3, uri2, strN4, strN5, jF4, jF5, iD, iD2, arrayListC);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ QuestEntity[] newArray(int i) {
        return new QuestEntity[i];
    }
}
