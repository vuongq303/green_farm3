package com.google.android.gms.internal.ads;

import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.R;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.google.android.gms.common.internal.safeparcel.a;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class zzjl implements Parcelable.Creator<zzjj> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzjj createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        int iD = 0;
        long jF = 0;
        Bundle bundleP = null;
        int iD2 = 0;
        ArrayList<String> arrayListA = null;
        boolean zC = false;
        int iD3 = 0;
        boolean zC2 = false;
        String strN = null;
        zzmq zzmqVar = null;
        Location location = null;
        String strN2 = null;
        Bundle bundleP2 = null;
        Bundle bundleP3 = null;
        ArrayList<String> arrayListA2 = null;
        String strN3 = null;
        String strN4 = null;
        boolean zC3 = false;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 1:
                    iD = a.d(parcel, iV);
                    break;
                case 2:
                    jF = a.f(parcel, iV);
                    break;
                case 3:
                    bundleP = a.p(parcel, iV);
                    break;
                case 4:
                    iD2 = a.d(parcel, iV);
                    break;
                case 5:
                    arrayListA = a.A(parcel, iV);
                    break;
                case 6:
                    zC = a.c(parcel, iV);
                    break;
                case 7:
                    iD3 = a.d(parcel, iV);
                    break;
                case 8:
                    zC2 = a.c(parcel, iV);
                    break;
                case 9:
                    strN = a.n(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    zzmqVar = (zzmq) a.a(parcel, iV, zzmq.CREATOR);
                    break;
                case R.styleable.CardView_contentPaddingTop /* 11 */:
                    location = (Location) a.a(parcel, iV, Location.CREATOR);
                    break;
                case R.styleable.CardView_contentPaddingBottom /* 12 */:
                    strN2 = a.n(parcel, iV);
                    break;
                case 13:
                    bundleP2 = a.p(parcel, iV);
                    break;
                case 14:
                    bundleP3 = a.p(parcel, iV);
                    break;
                case 15:
                    arrayListA2 = a.A(parcel, iV);
                    break;
                case 16:
                    strN3 = a.n(parcel, iV);
                    break;
                case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
                    strN4 = a.n(parcel, iV);
                    break;
                case 18:
                    zC3 = a.c(parcel, iV);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        a.D(parcel, iW);
        return new zzjj(iD, jF, bundleP, iD2, arrayListA, zC, iD3, zC2, strN, zzmqVar, location, strN2, bundleP2, bundleP3, arrayListA2, strN3, strN4, zC3);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzjj[] newArray(int i) {
        return new zzjj[i];
    }
}
