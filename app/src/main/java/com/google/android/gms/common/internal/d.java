package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;

/* JADX INFO: loaded from: classes.dex */
public class d implements Parcelable.Creator<AuthAccountRequest> {
    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: nK, reason: merged with bridge method [inline-methods] */
    public AuthAccountRequest[] newArray(int i) {
        return new AuthAccountRequest[i];
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: p, reason: merged with bridge method [inline-methods] */
    public AuthAccountRequest createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        int iD = 0;
        Account account = null;
        Integer numE = null;
        Integer numE2 = null;
        Scope[] scopeArr = null;
        IBinder iBinderO = null;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 1:
                    iD = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 2:
                    iBinderO = com.google.android.gms.common.internal.safeparcel.a.o(parcel, iV);
                    break;
                case 3:
                    scopeArr = (Scope[]) com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV, Scope.CREATOR);
                    break;
                case 4:
                    numE2 = com.google.android.gms.common.internal.safeparcel.a.e(parcel, iV);
                    break;
                case 5:
                    numE = com.google.android.gms.common.internal.safeparcel.a.e(parcel, iV);
                    break;
                case 6:
                    account = (Account) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, Account.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new AuthAccountRequest(iD, iBinderO, scopeArr, numE2, numE, account);
    }
}
