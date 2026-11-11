package com.google.android.gms.internal.plus;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.R;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
public final class zzz implements Parcelable.Creator<zzr.zze> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzr.zze createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        HashSet hashSet = new HashSet();
        int iD = 0;
        String strN = null;
        String strN2 = null;
        boolean zC = false;
        String strN3 = null;
        String strN4 = null;
        String strN5 = null;
        String strN6 = null;
        String strN7 = null;
        int iD2 = 0;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 1:
                    iD2 = a.d(parcel, iV);
                    hashSet.add(1);
                    break;
                case 2:
                    strN7 = a.n(parcel, iV);
                    hashSet.add(2);
                    break;
                case 3:
                    strN6 = a.n(parcel, iV);
                    hashSet.add(3);
                    break;
                case 4:
                    strN5 = a.n(parcel, iV);
                    hashSet.add(4);
                    break;
                case 5:
                    strN4 = a.n(parcel, iV);
                    hashSet.add(5);
                    break;
                case 6:
                    strN3 = a.n(parcel, iV);
                    hashSet.add(6);
                    break;
                case 7:
                    zC = a.c(parcel, iV);
                    hashSet.add(7);
                    break;
                case 8:
                    strN2 = a.n(parcel, iV);
                    hashSet.add(8);
                    break;
                case 9:
                    strN = a.n(parcel, iV);
                    hashSet.add(9);
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    iD = a.d(parcel, iV);
                    hashSet.add(10);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        if (parcel.dataPosition() != iW) {
            throw new b(new StringBuilder(37).append("Overread allowed size end=").append(iW).toString(), parcel);
        }
        return new zzr.zze(hashSet, iD2, strN7, strN6, strN5, strN4, strN3, zC, strN2, strN, iD);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzr.zze[] newArray(int i) {
        return new zzr.zze[i];
    }
}
