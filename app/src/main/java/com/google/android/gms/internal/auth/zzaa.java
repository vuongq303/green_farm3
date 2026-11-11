package com.google.android.gms.internal.auth;

import android.os.IInterface;
import com.google.android.gms.auth.api.accounttransfer.DeviceMetaData;
import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
public interface zzaa extends IInterface {
    void onFailure(Status status);

    void zzd(DeviceMetaData deviceMetaData);

    void zzd(Status status, com.google.android.gms.auth.api.accounttransfer.zzo zzoVar);

    void zzd(Status status, com.google.android.gms.auth.api.accounttransfer.zzw zzwVar);

    void zzd(byte[] bArr);

    void zze(Status status);

    void zzi();
}
