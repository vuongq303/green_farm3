package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.R;
import com.google.android.gms.common.internal.safeparcel.a;

/* JADX INFO: loaded from: classes.dex */
public final class zzjo implements Parcelable.Creator<zzjn> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzjn createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        boolean zC = false;
        boolean zC2 = false;
        boolean zC3 = false;
        zzjn[] zzjnVarArr = null;
        int iD = 0;
        int iD2 = 0;
        boolean zC4 = false;
        int iD3 = 0;
        int iD4 = 0;
        String strN = null;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 2:
                    strN = a.n(parcel, iV);
                    break;
                case 3:
                    iD4 = a.d(parcel, iV);
                    break;
                case 4:
                    iD3 = a.d(parcel, iV);
                    break;
                case 5:
                    zC4 = a.c(parcel, iV);
                    break;
                case 6:
                    iD2 = a.d(parcel, iV);
                    break;
                case 7:
                    iD = a.d(parcel, iV);
                    break;
                case 8:
                    zzjnVarArr = (zzjn[]) a.b(parcel, iV, zzjn.CREATOR);
                    break;
                case 9:
                    zC3 = a.c(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    zC2 = a.c(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingTop /* 11 */:
                    zC = a.c(parcel, iV);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        a.D(parcel, iW);
        return new zzjn(strN, iD4, iD3, zC4, iD2, iD, zzjnVarArr, zC3, zC2, zC);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzjn[] newArray(int i) {
        return new zzjn[i];
    }
}
