package com.google.android.gms.internal.measurement;

import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public final class zzdw extends zzn implements zzdu {
    zzdw(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.iid.IMessengerCompat");
    }

    @Override // com.google.android.gms.internal.measurement.zzdu
    public final void send(Message message) {
        Parcel parcelObtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzp.zza(parcelObtainAndWriteInterfaceToken, message);
        transactOneway(1, parcelObtainAndWriteInterfaceToken);
    }
}
