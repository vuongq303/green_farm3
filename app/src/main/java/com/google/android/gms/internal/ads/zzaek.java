package com.google.android.gms.internal.ads;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.R;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.google.android.gms.common.internal.safeparcel.a;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class zzaek implements Parcelable.Creator<zzaej> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzaej createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        int iD = 0;
        String strN = null;
        String strN2 = null;
        ArrayList<String> arrayListA = null;
        int iD2 = 0;
        ArrayList<String> arrayListA2 = null;
        long jF = 0;
        boolean zC = false;
        long jF2 = 0;
        ArrayList<String> arrayListA3 = null;
        long jF3 = 0;
        int iD3 = 0;
        String strN3 = null;
        long jF4 = 0;
        String strN4 = null;
        boolean zC2 = false;
        String strN5 = null;
        String strN6 = null;
        boolean zC3 = false;
        boolean zC4 = false;
        boolean zC5 = false;
        boolean zC6 = false;
        boolean zC7 = false;
        zzaev zzaevVar = null;
        String strN7 = null;
        String strN8 = null;
        boolean zC8 = false;
        boolean zC9 = false;
        zzaig zzaigVar = null;
        ArrayList<String> arrayListA4 = null;
        ArrayList<String> arrayListA5 = null;
        boolean zC10 = false;
        zzael zzaelVar = null;
        boolean zC11 = false;
        String strN9 = null;
        ArrayList<String> arrayListA6 = null;
        boolean zC12 = false;
        String strN10 = null;
        zzaiq zzaiqVar = null;
        String strN11 = null;
        boolean zC13 = false;
        boolean zC14 = false;
        Bundle bundleP = null;
        boolean zC15 = false;
        int iD4 = 0;
        boolean zC16 = false;
        ArrayList<String> arrayListA7 = null;
        boolean zC17 = false;
        String strN12 = null;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 1:
                    iD = a.d(parcel, iV);
                    break;
                case 2:
                    strN = a.n(parcel, iV);
                    break;
                case 3:
                    strN2 = a.n(parcel, iV);
                    break;
                case 4:
                    arrayListA = a.A(parcel, iV);
                    break;
                case 5:
                    iD2 = a.d(parcel, iV);
                    break;
                case 6:
                    arrayListA2 = a.A(parcel, iV);
                    break;
                case 7:
                    jF = a.f(parcel, iV);
                    break;
                case 8:
                    zC = a.c(parcel, iV);
                    break;
                case 9:
                    jF2 = a.f(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    arrayListA3 = a.A(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingTop /* 11 */:
                    jF3 = a.f(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingBottom /* 12 */:
                    iD3 = a.d(parcel, iV);
                    break;
                case 13:
                    strN3 = a.n(parcel, iV);
                    break;
                case 14:
                    jF4 = a.f(parcel, iV);
                    break;
                case 15:
                    strN4 = a.n(parcel, iV);
                    break;
                case 16:
                case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
                case 20:
                case 27:
                case 41:
                default:
                    a.b(parcel, iV);
                    break;
                case 18:
                    zC2 = a.c(parcel, iV);
                    break;
                case 19:
                    strN5 = a.n(parcel, iV);
                    break;
                case 21:
                    strN6 = a.n(parcel, iV);
                    break;
                case 22:
                    zC3 = a.c(parcel, iV);
                    break;
                case 23:
                    zC4 = a.c(parcel, iV);
                    break;
                case 24:
                    zC5 = a.c(parcel, iV);
                    break;
                case Constants.MAX_TREE_DEPTH /* 25 */:
                    zC6 = a.c(parcel, iV);
                    break;
                case 26:
                    zC7 = a.c(parcel, iV);
                    break;
                case 28:
                    zzaevVar = (zzaev) a.a(parcel, iV, zzaev.CREATOR);
                    break;
                case 29:
                    strN7 = a.n(parcel, iV);
                    break;
                case 30:
                    strN8 = a.n(parcel, iV);
                    break;
                case 31:
                    zC8 = a.c(parcel, iV);
                    break;
                case 32:
                    zC9 = a.c(parcel, iV);
                    break;
                case 33:
                    zzaigVar = (zzaig) a.a(parcel, iV, zzaig.CREATOR);
                    break;
                case 34:
                    arrayListA4 = a.A(parcel, iV);
                    break;
                case 35:
                    arrayListA5 = a.A(parcel, iV);
                    break;
                case 36:
                    zC10 = a.c(parcel, iV);
                    break;
                case 37:
                    zzaelVar = (zzael) a.a(parcel, iV, zzael.CREATOR);
                    break;
                case 38:
                    zC11 = a.c(parcel, iV);
                    break;
                case 39:
                    strN9 = a.n(parcel, iV);
                    break;
                case 40:
                    arrayListA6 = a.A(parcel, iV);
                    break;
                case 42:
                    zC12 = a.c(parcel, iV);
                    break;
                case 43:
                    strN10 = a.n(parcel, iV);
                    break;
                case 44:
                    zzaiqVar = (zzaiq) a.a(parcel, iV, zzaiq.CREATOR);
                    break;
                case 45:
                    strN11 = a.n(parcel, iV);
                    break;
                case 46:
                    zC13 = a.c(parcel, iV);
                    break;
                case 47:
                    zC14 = a.c(parcel, iV);
                    break;
                case 48:
                    bundleP = a.p(parcel, iV);
                    break;
                case 49:
                    zC15 = a.c(parcel, iV);
                    break;
                case 50:
                    iD4 = a.d(parcel, iV);
                    break;
                case 51:
                    zC16 = a.c(parcel, iV);
                    break;
                case 52:
                    arrayListA7 = a.A(parcel, iV);
                    break;
                case 53:
                    zC17 = a.c(parcel, iV);
                    break;
                case 54:
                    strN12 = a.n(parcel, iV);
                    break;
            }
        }
        a.D(parcel, iW);
        return new zzaej(iD, strN, strN2, arrayListA, iD2, arrayListA2, jF, zC, jF2, arrayListA3, jF3, iD3, strN3, jF4, strN4, zC2, strN5, strN6, zC3, zC4, zC5, zC6, zC7, zzaevVar, strN7, strN8, zC8, zC9, zzaigVar, arrayListA4, arrayListA5, zC10, zzaelVar, zC11, strN9, arrayListA6, zC12, strN10, zzaiqVar, strN11, zC13, zC14, bundleP, zC15, iD4, zC16, arrayListA7, zC17, strN12);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzaej[] newArray(int i) {
        return new zzaej[i];
    }
}
