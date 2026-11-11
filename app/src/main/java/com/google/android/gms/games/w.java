package com.google.android.gms.games;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.R;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.internal.FacebookRequestErrorClassification;

/* JADX INFO: loaded from: classes.dex */
public class w implements Parcelable.Creator<GameEntity> {
    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: H, reason: merged with bridge method [inline-methods] */
    public GameEntity createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        String strN = null;
        String strN2 = null;
        String strN3 = null;
        String strN4 = null;
        String strN5 = null;
        String strN6 = null;
        Uri uri = null;
        Uri uri2 = null;
        Uri uri3 = null;
        boolean zC = false;
        boolean zC2 = false;
        String strN7 = null;
        int iD = 0;
        int iD2 = 0;
        int iD3 = 0;
        boolean zC3 = false;
        boolean zC4 = false;
        String strN8 = null;
        String strN9 = null;
        String strN10 = null;
        boolean zC5 = false;
        boolean zC6 = false;
        boolean zC7 = false;
        String strN11 = null;
        boolean zC8 = false;
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
                    strN3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 4:
                    strN4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 5:
                    strN5 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 6:
                    strN6 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 7:
                    uri = (Uri) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, Uri.CREATOR);
                    break;
                case 8:
                    uri2 = (Uri) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, Uri.CREATOR);
                    break;
                case 9:
                    uri3 = (Uri) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, Uri.CREATOR);
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    zC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingTop /* 11 */:
                    zC2 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingBottom /* 12 */:
                    strN7 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 13:
                    iD = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 14:
                    iD2 = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 15:
                    iD3 = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 16:
                    zC3 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV);
                    break;
                case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
                    zC4 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV);
                    break;
                case 18:
                    strN8 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 19:
                    strN9 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 20:
                    strN10 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 21:
                    zC5 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV);
                    break;
                case 22:
                    zC6 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV);
                    break;
                case 23:
                    zC7 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV);
                    break;
                case 24:
                    strN11 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case Constants.MAX_TREE_DEPTH /* 25 */:
                    zC8 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new GameEntity(strN, strN2, strN3, strN4, strN5, strN6, uri, uri2, uri3, zC, zC2, strN7, iD, iD2, iD3, zC3, zC4, strN8, strN9, strN10, zC5, zC6, zC7, strN11, zC8);
    }

    @Override // android.os.Parcelable.Creator
    public /* synthetic */ GameEntity[] newArray(int i) {
        return new GameEntity[i];
    }
}
