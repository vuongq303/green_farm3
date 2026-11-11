package com.google.android.gms.games.achievement;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.R;
import com.google.android.gms.games.PlayerEntity;

/* JADX INFO: loaded from: classes.dex */
public final class e implements Parcelable.Creator<AchievementEntity> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ AchievementEntity createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        String strN = null;
        int iD = 0;
        String strN2 = null;
        String strN3 = null;
        Uri uri = null;
        String strN4 = null;
        Uri uri2 = null;
        String strN5 = null;
        int iD2 = 0;
        String strN6 = null;
        PlayerEntity playerEntity = null;
        int iD3 = 0;
        int iD4 = 0;
        String strN7 = null;
        long jF = 0;
        long jF2 = 0;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 1:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 2:
                    iD = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 3:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 4:
                    strN3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 5:
                    uri = (Uri) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, Uri.CREATOR);
                    break;
                case 6:
                    strN4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 7:
                    uri2 = (Uri) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, Uri.CREATOR);
                    break;
                case 8:
                    strN5 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 9:
                    iD2 = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    strN6 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingTop /* 11 */:
                    playerEntity = (PlayerEntity) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, PlayerEntity.CREATOR);
                    break;
                case R.styleable.CardView_contentPaddingBottom /* 12 */:
                    iD3 = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 13:
                    iD4 = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 14:
                    strN7 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 15:
                    jF = com.google.android.gms.common.internal.safeparcel.a.f(parcel, iV);
                    break;
                case 16:
                    jF2 = com.google.android.gms.common.internal.safeparcel.a.f(parcel, iV);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new AchievementEntity(strN, iD, strN2, strN3, uri, strN4, uri2, strN5, iD2, strN6, playerEntity, iD3, iD4, strN7, jF, jF2);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ AchievementEntity[] newArray(int i) {
        return new AchievementEntity[i];
    }
}
