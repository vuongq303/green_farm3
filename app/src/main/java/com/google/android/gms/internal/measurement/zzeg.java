package com.google.android.gms.internal.measurement;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.R;
import com.google.android.gms.common.internal.safeparcel.a;

/* JADX INFO: loaded from: classes.dex */
public final class zzeg implements Parcelable.Creator<zzef> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzef createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        String strN = null;
        String strN2 = null;
        zzjs zzjsVar = null;
        long jF = 0;
        boolean zC = false;
        String strN3 = null;
        zzeu zzeuVar = null;
        long jF2 = 0;
        zzeu zzeuVar2 = null;
        long jF3 = 0;
        zzeu zzeuVar3 = null;
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
                    zzjsVar = (zzjs) a.a(parcel, iV, zzjs.CREATOR);
                    break;
                case 5:
                    jF = a.f(parcel, iV);
                    break;
                case 6:
                    zC = a.c(parcel, iV);
                    break;
                case 7:
                    strN3 = a.n(parcel, iV);
                    break;
                case 8:
                    zzeuVar = (zzeu) a.a(parcel, iV, zzeu.CREATOR);
                    break;
                case 9:
                    jF2 = a.f(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    zzeuVar2 = (zzeu) a.a(parcel, iV, zzeu.CREATOR);
                    break;
                case R.styleable.CardView_contentPaddingTop /* 11 */:
                    jF3 = a.f(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingBottom /* 12 */:
                    zzeuVar3 = (zzeu) a.a(parcel, iV, zzeu.CREATOR);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        a.D(parcel, iW);
        return new zzef(strN, strN2, zzjsVar, jF, zC, strN3, zzeuVar, jF2, zzeuVar2, jF3, zzeuVar3);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzef[] newArray(int i) {
        return new zzef[i];
    }
}
