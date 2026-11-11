package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.appevents.codeless.internal.Constants;

/* JADX INFO: loaded from: classes.dex */
public final class j implements Parcelable.Creator<HintRequest> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ HintRequest createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        String strN = null;
        String strN2 = null;
        boolean zC = false;
        String[] strArrY = null;
        boolean zC2 = false;
        boolean zC3 = false;
        CredentialPickerConfig credentialPickerConfig = null;
        int iD = 0;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 1:
                    credentialPickerConfig = (CredentialPickerConfig) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, CredentialPickerConfig.CREATOR);
                    break;
                case 2:
                    zC3 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV);
                    break;
                case 3:
                    zC2 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV);
                    break;
                case 4:
                    strArrY = com.google.android.gms.common.internal.safeparcel.a.y(parcel, iV);
                    break;
                case 5:
                    zC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV);
                    break;
                case 6:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 7:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS /* 1000 */:
                    iD = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new HintRequest(iD, credentialPickerConfig, zC3, zC2, strArrY, zC, strN2, strN);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ HintRequest[] newArray(int i) {
        return new HintRequest[i];
    }
}
