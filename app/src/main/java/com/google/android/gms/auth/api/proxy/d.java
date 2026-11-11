package com.google.android.gms.auth.api.proxy;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.appevents.codeless.internal.Constants;

/* JADX INFO: loaded from: classes.dex */
public final class d implements Parcelable.Creator<ProxyResponse> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ ProxyResponse createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        byte[] bArrQ = null;
        Bundle bundleP = null;
        int iD = 0;
        PendingIntent pendingIntent = null;
        int iD2 = 0;
        int iD3 = 0;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 1:
                    iD2 = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 2:
                    pendingIntent = (PendingIntent) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, PendingIntent.CREATOR);
                    break;
                case 3:
                    iD = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 4:
                    bundleP = com.google.android.gms.common.internal.safeparcel.a.p(parcel, iV);
                    break;
                case 5:
                    bArrQ = com.google.android.gms.common.internal.safeparcel.a.q(parcel, iV);
                    break;
                case Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS /* 1000 */:
                    iD3 = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new ProxyResponse(iD3, iD2, pendingIntent, iD, bundleP, bArrQ);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ ProxyResponse[] newArray(int i) {
        return new ProxyResponse[i];
    }
}
