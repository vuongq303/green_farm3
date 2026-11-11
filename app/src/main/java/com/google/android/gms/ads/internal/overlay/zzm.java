package com.google.android.gms.ads.internal.overlay;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.R;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.google.android.gms.ads.internal.zzaq;
import com.google.android.gms.internal.ads.zzang;

/* JADX INFO: loaded from: classes.dex */
public final class zzm implements Parcelable.Creator<AdOverlayInfoParcel> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ AdOverlayInfoParcel createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        zzc zzcVar = null;
        IBinder iBinderO = null;
        IBinder iBinderO2 = null;
        IBinder iBinderO3 = null;
        IBinder iBinderO4 = null;
        String strN = null;
        boolean zC = false;
        String strN2 = null;
        IBinder iBinderO5 = null;
        int iD = 0;
        int iD2 = 0;
        String strN3 = null;
        zzang zzangVar = null;
        String strN4 = null;
        zzaq zzaqVar = null;
        IBinder iBinderO6 = null;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 2:
                    zzcVar = (zzc) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, zzc.CREATOR);
                    break;
                case 3:
                    iBinderO = com.google.android.gms.common.internal.safeparcel.a.o(parcel, iV);
                    break;
                case 4:
                    iBinderO2 = com.google.android.gms.common.internal.safeparcel.a.o(parcel, iV);
                    break;
                case 5:
                    iBinderO3 = com.google.android.gms.common.internal.safeparcel.a.o(parcel, iV);
                    break;
                case 6:
                    iBinderO4 = com.google.android.gms.common.internal.safeparcel.a.o(parcel, iV);
                    break;
                case 7:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 8:
                    zC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV);
                    break;
                case 9:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    iBinderO5 = com.google.android.gms.common.internal.safeparcel.a.o(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingTop /* 11 */:
                    iD = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingBottom /* 12 */:
                    iD2 = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 13:
                    strN3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 14:
                    zzangVar = (zzang) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, zzang.CREATOR);
                    break;
                case 15:
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
                case 16:
                    strN4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
                    zzaqVar = (zzaq) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, zzaq.CREATOR);
                    break;
                case 18:
                    iBinderO6 = com.google.android.gms.common.internal.safeparcel.a.o(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new AdOverlayInfoParcel(zzcVar, iBinderO, iBinderO2, iBinderO3, iBinderO4, strN, zC, strN2, iBinderO5, iD, iD2, strN3, zzangVar, strN4, zzaqVar, iBinderO6);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ AdOverlayInfoParcel[] newArray(int i) {
        return new AdOverlayInfoParcel[i];
    }
}
