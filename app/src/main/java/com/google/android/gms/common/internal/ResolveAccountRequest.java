package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class ResolveAccountRequest extends AbstractSafeParcelable {
    public static final Parcelable.Creator<ResolveAccountRequest> CREATOR = new bg();
    private final int bOw;
    private final GoogleSignInAccount bOx;
    private final int zzal;
    private final Account zzs;

    ResolveAccountRequest(int i, Account account, int i2, GoogleSignInAccount googleSignInAccount) {
        this.zzal = i;
        this.zzs = account;
        this.bOw = i2;
        this.bOx = googleSignInAccount;
    }

    public ResolveAccountRequest(Account account, int i, GoogleSignInAccount googleSignInAccount) {
        this(2, account, i, googleSignInAccount);
    }

    public GoogleSignInAccount aaB() {
        return this.bOx;
    }

    public Account getAccount() {
        return this.zzs;
    }

    public int getSessionId() {
        return this.bOw;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.zzal);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, (Parcelable) getAccount(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 3, getSessionId());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, (Parcelable) aaB(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
