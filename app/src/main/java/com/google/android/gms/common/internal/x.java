package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.R;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Scope;

/* JADX INFO: loaded from: classes.dex */
public class x implements Parcelable.Creator<GetServiceRequest> {
    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: nM, reason: merged with bridge method [inline-methods] */
    public GetServiceRequest[] newArray(int i) {
        return new GetServiceRequest[i];
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: r, reason: merged with bridge method [inline-methods] */
    public GetServiceRequest createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        boolean zC = false;
        Feature[] featureArr = null;
        Feature[] featureArr2 = null;
        Account account = null;
        Bundle bundleP = null;
        Scope[] scopeArr = null;
        IBinder iBinderO = null;
        String strN = null;
        int iD = 0;
        int iD2 = 0;
        int iD3 = 0;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 1:
                    iD3 = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 2:
                    iD2 = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 3:
                    iD = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 4:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 5:
                    iBinderO = com.google.android.gms.common.internal.safeparcel.a.o(parcel, iV);
                    break;
                case 6:
                    scopeArr = (Scope[]) com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV, Scope.CREATOR);
                    break;
                case 7:
                    bundleP = com.google.android.gms.common.internal.safeparcel.a.p(parcel, iV);
                    break;
                case 8:
                    account = (Account) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, Account.CREATOR);
                    break;
                case 9:
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    featureArr2 = (Feature[]) com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV, Feature.CREATOR);
                    break;
                case R.styleable.CardView_contentPaddingTop /* 11 */:
                    featureArr = (Feature[]) com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV, Feature.CREATOR);
                    break;
                case R.styleable.CardView_contentPaddingBottom /* 12 */:
                    zC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new GetServiceRequest(iD3, iD2, iD, strN, iBinderO, scopeArr, bundleP, account, featureArr2, featureArr, zC);
    }
}
