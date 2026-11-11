package com.google.android.gms.internal.ads;

import android.os.Bundle;
import android.os.IInterface;
import com.google.android.gms.c.a;

/* JADX INFO: loaded from: classes.dex */
public interface zzzj extends IInterface {
    zzlo getVideoController();

    void showInterstitial();

    void zza(a aVar, String str, Bundle bundle, zzzm zzzmVar);

    void zza(byte[] bArr, String str, Bundle bundle, a aVar, zzzf zzzfVar, zzxt zzxtVar, zzjn zzjnVar);

    void zza(byte[] bArr, String str, Bundle bundle, a aVar, zzzh zzzhVar, zzxt zzxtVar);

    zzzt zznc();

    zzzt zznd();
}
