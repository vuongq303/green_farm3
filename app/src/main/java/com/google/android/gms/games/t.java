package com.google.android.gms.games;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.R;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.google.android.gms.games.internal.player.zzb;

/* JADX INFO: loaded from: classes.dex */
public class t implements Parcelable.Creator<PlayerEntity> {
    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: I, reason: merged with bridge method [inline-methods] */
    public PlayerEntity createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        String strN = null;
        String strN2 = null;
        Uri uri = null;
        Uri uri2 = null;
        long jF = 0;
        int iD = 0;
        long jF2 = 0;
        String strN3 = null;
        String strN4 = null;
        String strN5 = null;
        zzb zzbVar = null;
        PlayerLevelInfo playerLevelInfo = null;
        boolean zC = false;
        boolean zC2 = false;
        String strN6 = null;
        String strN7 = null;
        Uri uri3 = null;
        String strN8 = null;
        Uri uri4 = null;
        String strN9 = null;
        int iD2 = 0;
        long jF3 = 0;
        boolean zC3 = false;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 1:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 2:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 3:
                    uri = (Uri) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, Uri.CREATOR);
                    break;
                case 4:
                    uri2 = (Uri) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, Uri.CREATOR);
                    break;
                case 5:
                    jF = com.google.android.gms.common.internal.safeparcel.a.f(parcel, iV);
                    break;
                case 6:
                    iD = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 7:
                    jF2 = com.google.android.gms.common.internal.safeparcel.a.f(parcel, iV);
                    break;
                case 8:
                    strN3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 9:
                    strN4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                case R.styleable.CardView_contentPaddingTop /* 11 */:
                case R.styleable.CardView_contentPaddingBottom /* 12 */:
                case 13:
                case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
                case 14:
                    strN5 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 15:
                    zzbVar = (zzb) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, zzb.CREATOR);
                    break;
                case 16:
                    playerLevelInfo = (PlayerLevelInfo) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, PlayerLevelInfo.CREATOR);
                    break;
                case 18:
                    zC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV);
                    break;
                case 19:
                    zC2 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV);
                    break;
                case 20:
                    strN6 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 21:
                    strN7 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 22:
                    uri3 = (Uri) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, Uri.CREATOR);
                    break;
                case 23:
                    strN8 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 24:
                    uri4 = (Uri) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, Uri.CREATOR);
                    break;
                case Constants.MAX_TREE_DEPTH /* 25 */:
                    strN9 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 26:
                    iD2 = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 27:
                    jF3 = com.google.android.gms.common.internal.safeparcel.a.f(parcel, iV);
                    break;
                case 28:
                    zC3 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new PlayerEntity(strN, strN2, uri, uri2, jF, iD, jF2, strN3, strN4, strN5, zzbVar, playerLevelInfo, zC, zC2, strN6, strN7, uri3, strN8, uri4, strN9, iD2, jF3, zC3);
    }

    @Override // android.os.Parcelable.Creator
    public /* synthetic */ PlayerEntity[] newArray(int i) {
        return new PlayerEntity[i];
    }
}
