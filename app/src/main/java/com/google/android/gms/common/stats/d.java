package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.R;
import com.facebook.internal.FacebookRequestErrorClassification;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class d implements Parcelable.Creator<WakeLockEvent> {
    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: G, reason: merged with bridge method [inline-methods] */
    public WakeLockEvent createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        int iD = 0;
        long jF = 0;
        int iD2 = 0;
        String strN = null;
        int iD3 = 0;
        ArrayList<String> arrayListA = null;
        String strN2 = null;
        long jF2 = 0;
        int iD4 = 0;
        String strN3 = null;
        String strN4 = null;
        float fI = 0.0f;
        long jF3 = 0;
        String strN5 = null;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 1:
                    iD = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 2:
                    jF = com.google.android.gms.common.internal.safeparcel.a.f(parcel, iV);
                    break;
                case 3:
                case 7:
                case 9:
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
                case 4:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 5:
                    iD3 = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 6:
                    arrayListA = com.google.android.gms.common.internal.safeparcel.a.A(parcel, iV);
                    break;
                case 8:
                    jF2 = com.google.android.gms.common.internal.safeparcel.a.f(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    strN3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingTop /* 11 */:
                    iD2 = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingBottom /* 12 */:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 13:
                    strN4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 14:
                    iD4 = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 15:
                    fI = com.google.android.gms.common.internal.safeparcel.a.i(parcel, iV);
                    break;
                case 16:
                    jF3 = com.google.android.gms.common.internal.safeparcel.a.f(parcel, iV);
                    break;
                case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
                    strN5 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new WakeLockEvent(iD, jF, iD2, strN, iD3, arrayListA, strN2, jF2, iD4, strN3, strN4, fI, jF3, strN5);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: oc, reason: merged with bridge method [inline-methods] */
    public WakeLockEvent[] newArray(int i) {
        return new WakeLockEvent[i];
    }
}
