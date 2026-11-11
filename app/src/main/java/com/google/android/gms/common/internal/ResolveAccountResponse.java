package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class ResolveAccountResponse extends AbstractSafeParcelable {
    public static final Parcelable.Creator<ResolveAccountResponse> CREATOR = new bh();
    private boolean bKs;
    private ConnectionResult bMn;
    private IBinder bNt;
    private boolean bOy;
    private final int zzal;

    ResolveAccountResponse(int i, IBinder iBinder, ConnectionResult connectionResult, boolean z, boolean z2) {
        this.zzal = i;
        this.bNt = iBinder;
        this.bMn = connectionResult;
        this.bKs = z;
        this.bOy = z2;
    }

    public ConnectionResult ZM() {
        return this.bMn;
    }

    public ah aaC() {
        return ai.i(this.bNt);
    }

    public boolean aaD() {
        return this.bKs;
    }

    public boolean aaE() {
        return this.bOy;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ResolveAccountResponse)) {
            return false;
        }
        ResolveAccountResponse resolveAccountResponse = (ResolveAccountResponse) obj;
        return this.bMn.equals(resolveAccountResponse.bMn) && aaC().equals(resolveAccountResponse.aaC());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.zzal);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, this.bNt, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, (Parcelable) ZM(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, aaD());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 5, aaE());
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
