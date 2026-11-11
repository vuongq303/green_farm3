package com.google.android.gms.internal.ads;

import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzxx extends zzek implements zzxw {
    public zzxx() {
        super("com.google.android.gms.ads.internal.mediation.client.IMediationResponseMetadata");
    }

    @Override // com.google.android.gms.internal.ads.zzek
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i != 1) {
            return false;
        }
        int iZzmm = zzmm();
        parcel2.writeNoException();
        parcel2.writeInt(iZzmm);
        return true;
    }
}
