package com.google.android.gms.ads.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.R;

/* JADX INFO: loaded from: classes.dex */
public final class zzar implements Parcelable.Creator<zzaq> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzaq createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        String strN = null;
        float fI = 0.0f;
        boolean zC = false;
        boolean zC2 = false;
        boolean zC3 = false;
        int iD = 0;
        boolean zC4 = false;
        boolean zC5 = false;
        boolean zC6 = false;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 2:
                    zC6 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV);
                    break;
                case 3:
                    zC5 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV);
                    break;
                case 4:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 5:
                    zC4 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV);
                    break;
                case 6:
                    fI = com.google.android.gms.common.internal.safeparcel.a.i(parcel, iV);
                    break;
                case 7:
                    iD = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 8:
                    zC3 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV);
                    break;
                case 9:
                    zC2 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    zC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new zzaq(zC6, zC5, strN, zC4, fI, iD, zC3, zC2, zC);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzaq[] newArray(int i) {
        return new zzaq[i];
    }
}
