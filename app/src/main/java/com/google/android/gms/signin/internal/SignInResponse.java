package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class SignInResponse extends AbstractSafeParcelable {
    public static final Parcelable.Creator<SignInResponse> CREATOR = new m();
    private final ConnectionResult bMn;
    private final ResolveAccountResponse bYC;
    private final int zzal;

    public SignInResponse(int i) {
        this(new ConnectionResult(i, null), null);
    }

    SignInResponse(int i, ConnectionResult connectionResult, ResolveAccountResponse resolveAccountResponse) {
        this.zzal = i;
        this.bMn = connectionResult;
        this.bYC = resolveAccountResponse;
    }

    public SignInResponse(ConnectionResult connectionResult, ResolveAccountResponse resolveAccountResponse) {
        this(1, connectionResult, resolveAccountResponse);
    }

    public ConnectionResult ZM() {
        return this.bMn;
    }

    public ResolveAccountResponse agm() {
        return this.bYC;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.zzal);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, (Parcelable) ZM(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, (Parcelable) agm(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
