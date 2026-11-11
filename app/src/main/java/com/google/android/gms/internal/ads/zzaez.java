package com.google.android.gms.internal.ads;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class zzaez implements Parcelable.Creator<zzaey> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzaey createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        boolean zC = false;
        String strN = null;
        String strN2 = null;
        PackageInfo packageInfo = null;
        ArrayList<String> arrayListA = null;
        String strN3 = null;
        ApplicationInfo applicationInfo = null;
        zzang zzangVar = null;
        Bundle bundleP = null;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 1:
                    bundleP = a.p(parcel, iV);
                    break;
                case 2:
                    zzangVar = (zzang) a.a(parcel, iV, zzang.CREATOR);
                    break;
                case 3:
                    applicationInfo = (ApplicationInfo) a.a(parcel, iV, ApplicationInfo.CREATOR);
                    break;
                case 4:
                    strN3 = a.n(parcel, iV);
                    break;
                case 5:
                    arrayListA = a.A(parcel, iV);
                    break;
                case 6:
                    packageInfo = (PackageInfo) a.a(parcel, iV, PackageInfo.CREATOR);
                    break;
                case 7:
                    strN2 = a.n(parcel, iV);
                    break;
                case 8:
                    zC = a.c(parcel, iV);
                    break;
                case 9:
                    strN = a.n(parcel, iV);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        a.D(parcel, iW);
        return new zzaey(bundleP, zzangVar, applicationInfo, strN3, arrayListA, packageInfo, strN2, zC, strN);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzaey[] newArray(int i) {
        return new zzaey[i];
    }
}
