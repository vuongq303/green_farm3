package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ResolveAccountRequest;

/* JADX INFO: loaded from: classes.dex */
public class l implements Parcelable.Creator<SignInRequest> {
    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: P, reason: merged with bridge method [inline-methods] */
    public SignInRequest createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        int iD = 0;
        ResolveAccountRequest resolveAccountRequest = null;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 1:
                    iD = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 2:
                    resolveAccountRequest = (ResolveAccountRequest) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, ResolveAccountRequest.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new SignInRequest(iD, resolveAccountRequest);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: ov, reason: merged with bridge method [inline-methods] */
    public SignInRequest[] newArray(int i) {
        return new SignInRequest[i];
    }
}
