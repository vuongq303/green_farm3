package com.google.android.gms.internal.measurement;

import android.content.Intent;
import android.os.Parcel;
import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzdr extends zzo implements zzdq {
    public zzdr() {
        super("com.google.android.gms.appinvite.internal.IAppInviteCallbacks");
    }

    @Override // com.google.android.gms.internal.measurement.zzo
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 1:
                zza((Status) zzp.zza(parcel, Status.CREATOR));
                break;
            case 2:
                zza((Status) zzp.zza(parcel, Status.CREATOR), (Intent) zzp.zza(parcel, Intent.CREATOR));
                break;
            default:
                return false;
        }
        parcel2.writeNoException();
        return true;
    }
}
