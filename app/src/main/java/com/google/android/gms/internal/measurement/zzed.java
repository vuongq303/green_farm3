package com.google.android.gms.internal.measurement;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.R;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.google.android.gms.common.internal.safeparcel.a;

/* JADX INFO: loaded from: classes.dex */
public final class zzed implements Parcelable.Creator<zzec> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzec createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        String strN = null;
        String strN2 = null;
        String strN3 = null;
        String strN4 = null;
        long jF = 0;
        long jF2 = 0;
        String strN5 = null;
        boolean zC = true;
        boolean zC2 = false;
        long jF3 = -2147483648L;
        String strN6 = null;
        long jF4 = 0;
        long jF5 = 0;
        int iD = 0;
        boolean zC3 = true;
        boolean zC4 = true;
        boolean zC5 = false;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 2:
                    strN = a.n(parcel, iV);
                    break;
                case 3:
                    strN2 = a.n(parcel, iV);
                    break;
                case 4:
                    strN3 = a.n(parcel, iV);
                    break;
                case 5:
                    strN4 = a.n(parcel, iV);
                    break;
                case 6:
                    jF = a.f(parcel, iV);
                    break;
                case 7:
                    jF2 = a.f(parcel, iV);
                    break;
                case 8:
                    strN5 = a.n(parcel, iV);
                    break;
                case 9:
                    zC = a.c(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    zC2 = a.c(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingTop /* 11 */:
                    jF3 = a.f(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingBottom /* 12 */:
                    strN6 = a.n(parcel, iV);
                    break;
                case 13:
                    jF4 = a.f(parcel, iV);
                    break;
                case 14:
                    jF5 = a.f(parcel, iV);
                    break;
                case 15:
                    iD = a.d(parcel, iV);
                    break;
                case 16:
                    zC3 = a.c(parcel, iV);
                    break;
                case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
                    zC4 = a.c(parcel, iV);
                    break;
                case 18:
                    zC5 = a.c(parcel, iV);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        a.D(parcel, iW);
        return new zzec(strN, strN2, strN3, strN4, jF, jF2, strN5, zC, zC2, jF3, strN6, jF4, jF5, iD, zC3, zC4, zC5);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzec[] newArray(int i) {
        return new zzec[i];
    }
}
