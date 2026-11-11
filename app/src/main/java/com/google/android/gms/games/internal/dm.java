package com.google.android.gms.games.internal;

import android.os.Parcel;
import com.google.android.gms.internal.games.zzb;
import com.google.android.gms.internal.games.zzc;

/* JADX INFO: loaded from: classes.dex */
public abstract class dm extends zzb implements dl {
    public dm() {
        super("com.google.android.gms.games.internal.IGamesClient");
    }

    @Override // com.google.android.gms.internal.games.zzb
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i != 1001) {
            return false;
        }
        zzaa zzaaVarAdh = adh();
        parcel2.writeNoException();
        zzc.zzb(parcel2, zzaaVarAdh);
        return true;
    }
}
