package com.google.android.gms.games.stats;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.R;

/* JADX INFO: loaded from: classes.dex */
public final class d implements Parcelable.Creator<zza> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zza createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        Bundle bundleP = null;
        float fI = 0.0f;
        float fI2 = 0.0f;
        float fI3 = 0.0f;
        float fI4 = 0.0f;
        float fI5 = 0.0f;
        int iD = 0;
        int iD2 = 0;
        int iD3 = 0;
        float fI6 = 0.0f;
        float fI7 = 0.0f;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 1:
                    fI7 = com.google.android.gms.common.internal.safeparcel.a.i(parcel, iV);
                    break;
                case 2:
                    fI6 = com.google.android.gms.common.internal.safeparcel.a.i(parcel, iV);
                    break;
                case 3:
                    iD3 = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 4:
                    iD2 = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 5:
                    iD = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 6:
                    fI5 = com.google.android.gms.common.internal.safeparcel.a.i(parcel, iV);
                    break;
                case 7:
                    fI4 = com.google.android.gms.common.internal.safeparcel.a.i(parcel, iV);
                    break;
                case 8:
                    bundleP = com.google.android.gms.common.internal.safeparcel.a.p(parcel, iV);
                    break;
                case 9:
                    fI3 = com.google.android.gms.common.internal.safeparcel.a.i(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    fI2 = com.google.android.gms.common.internal.safeparcel.a.i(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingTop /* 11 */:
                    fI = com.google.android.gms.common.internal.safeparcel.a.i(parcel, iV);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new zza(fI7, fI6, iD3, iD2, iD, fI5, fI4, bundleP, fI3, fI2, fI);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zza[] newArray(int i) {
        return new zza[i];
    }
}
