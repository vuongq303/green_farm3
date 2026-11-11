package com.google.android.gms.internal.ads;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.R;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.google.android.gms.common.internal.safeparcel.a;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class zzaeh implements Parcelable.Creator<zzaef> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzaef createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        int iD = 0;
        Bundle bundleP = null;
        zzjj zzjjVar = null;
        zzjn zzjnVar = null;
        String strN = null;
        ApplicationInfo applicationInfo = null;
        PackageInfo packageInfo = null;
        String strN2 = null;
        String strN3 = null;
        String strN4 = null;
        zzang zzangVar = null;
        Bundle bundleP2 = null;
        int iD2 = 0;
        ArrayList<String> arrayListA = null;
        Bundle bundleP3 = null;
        boolean zC = false;
        int iD3 = 0;
        int iD4 = 0;
        float fI = 0.0f;
        String strN5 = null;
        long jF = 0;
        String strN6 = null;
        ArrayList<String> arrayListA2 = null;
        String strN7 = null;
        zzpl zzplVar = null;
        ArrayList<String> arrayListA3 = null;
        long jF2 = 0;
        String strN8 = null;
        float fI2 = 0.0f;
        boolean zC2 = false;
        int iD5 = 0;
        int iD6 = 0;
        boolean zC3 = false;
        boolean zC4 = false;
        String strN9 = null;
        String strN10 = null;
        boolean zC5 = false;
        int iD7 = 0;
        Bundle bundleP4 = null;
        String strN11 = null;
        zzlu zzluVar = null;
        boolean zC6 = false;
        Bundle bundleP5 = null;
        String strN12 = null;
        String strN13 = null;
        String strN14 = null;
        boolean zC7 = false;
        ArrayList<Integer> arrayListZ = null;
        String strN15 = null;
        ArrayList<String> arrayListA4 = null;
        int iD8 = 0;
        boolean zC8 = false;
        boolean zC9 = false;
        boolean zC10 = false;
        ArrayList<String> arrayListA5 = null;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 1:
                    iD = a.d(parcel, iV);
                    break;
                case 2:
                    bundleP = a.p(parcel, iV);
                    break;
                case 3:
                    zzjjVar = (zzjj) a.a(parcel, iV, zzjj.CREATOR);
                    break;
                case 4:
                    zzjnVar = (zzjn) a.a(parcel, iV, zzjn.CREATOR);
                    break;
                case 5:
                    strN = a.n(parcel, iV);
                    break;
                case 6:
                    applicationInfo = (ApplicationInfo) a.a(parcel, iV, ApplicationInfo.CREATOR);
                    break;
                case 7:
                    packageInfo = (PackageInfo) a.a(parcel, iV, PackageInfo.CREATOR);
                    break;
                case 8:
                    strN2 = a.n(parcel, iV);
                    break;
                case 9:
                    strN3 = a.n(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    strN4 = a.n(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingTop /* 11 */:
                    zzangVar = (zzang) a.a(parcel, iV, zzang.CREATOR);
                    break;
                case R.styleable.CardView_contentPaddingBottom /* 12 */:
                    bundleP2 = a.p(parcel, iV);
                    break;
                case 13:
                    iD2 = a.d(parcel, iV);
                    break;
                case 14:
                    arrayListA = a.A(parcel, iV);
                    break;
                case 15:
                    bundleP3 = a.p(parcel, iV);
                    break;
                case 16:
                    zC = a.c(parcel, iV);
                    break;
                case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
                case 22:
                case 23:
                case 24:
                case 32:
                default:
                    a.b(parcel, iV);
                    break;
                case 18:
                    iD3 = a.d(parcel, iV);
                    break;
                case 19:
                    iD4 = a.d(parcel, iV);
                    break;
                case 20:
                    fI = a.i(parcel, iV);
                    break;
                case 21:
                    strN5 = a.n(parcel, iV);
                    break;
                case Constants.MAX_TREE_DEPTH /* 25 */:
                    jF = a.f(parcel, iV);
                    break;
                case 26:
                    strN6 = a.n(parcel, iV);
                    break;
                case 27:
                    arrayListA2 = a.A(parcel, iV);
                    break;
                case 28:
                    strN7 = a.n(parcel, iV);
                    break;
                case 29:
                    zzplVar = (zzpl) a.a(parcel, iV, zzpl.CREATOR);
                    break;
                case 30:
                    arrayListA3 = a.A(parcel, iV);
                    break;
                case 31:
                    jF2 = a.f(parcel, iV);
                    break;
                case 33:
                    strN8 = a.n(parcel, iV);
                    break;
                case 34:
                    fI2 = a.i(parcel, iV);
                    break;
                case 35:
                    iD5 = a.d(parcel, iV);
                    break;
                case 36:
                    iD6 = a.d(parcel, iV);
                    break;
                case 37:
                    zC3 = a.c(parcel, iV);
                    break;
                case 38:
                    zC4 = a.c(parcel, iV);
                    break;
                case 39:
                    strN9 = a.n(parcel, iV);
                    break;
                case 40:
                    zC2 = a.c(parcel, iV);
                    break;
                case 41:
                    strN10 = a.n(parcel, iV);
                    break;
                case 42:
                    zC5 = a.c(parcel, iV);
                    break;
                case 43:
                    iD7 = a.d(parcel, iV);
                    break;
                case 44:
                    bundleP4 = a.p(parcel, iV);
                    break;
                case 45:
                    strN11 = a.n(parcel, iV);
                    break;
                case 46:
                    zzluVar = (zzlu) a.a(parcel, iV, zzlu.CREATOR);
                    break;
                case 47:
                    zC6 = a.c(parcel, iV);
                    break;
                case 48:
                    bundleP5 = a.p(parcel, iV);
                    break;
                case 49:
                    strN12 = a.n(parcel, iV);
                    break;
                case 50:
                    strN13 = a.n(parcel, iV);
                    break;
                case 51:
                    strN14 = a.n(parcel, iV);
                    break;
                case 52:
                    zC7 = a.c(parcel, iV);
                    break;
                case 53:
                    arrayListZ = a.z(parcel, iV);
                    break;
                case 54:
                    strN15 = a.n(parcel, iV);
                    break;
                case 55:
                    arrayListA4 = a.A(parcel, iV);
                    break;
                case 56:
                    iD8 = a.d(parcel, iV);
                    break;
                case 57:
                    zC8 = a.c(parcel, iV);
                    break;
                case 58:
                    zC9 = a.c(parcel, iV);
                    break;
                case 59:
                    zC10 = a.c(parcel, iV);
                    break;
                case 60:
                    arrayListA5 = a.A(parcel, iV);
                    break;
            }
        }
        a.D(parcel, iW);
        return new zzaef(iD, bundleP, zzjjVar, zzjnVar, strN, applicationInfo, packageInfo, strN2, strN3, strN4, zzangVar, bundleP2, iD2, arrayListA, bundleP3, zC, iD3, iD4, fI, strN5, jF, strN6, arrayListA2, strN7, zzplVar, arrayListA3, jF2, strN8, fI2, zC2, iD5, iD6, zC3, zC4, strN9, strN10, zC5, iD7, bundleP4, strN11, zzluVar, zC6, bundleP5, strN12, strN13, strN14, zC7, arrayListZ, strN15, arrayListA4, iD8, zC8, zC9, zC10, arrayListA5);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzaef[] newArray(int i) {
        return new zzaef[i];
    }
}
