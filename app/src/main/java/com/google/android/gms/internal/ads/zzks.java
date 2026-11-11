package com.google.android.gms.internal.ads;

import android.os.Bundle;
import android.os.IInterface;
import com.google.android.gms.c.a;

/* JADX INFO: loaded from: classes.dex */
public interface zzks extends IInterface {
    void destroy();

    String getAdUnitId();

    String getMediationAdapterClassName();

    zzlo getVideoController();

    boolean isLoading();

    boolean isReady();

    void pause();

    void resume();

    void setImmersiveMode(boolean z);

    void setManualImpressionsEnabled(boolean z);

    void setUserId(String str);

    void showInterstitial();

    void stopLoading();

    void zza(zzaaw zzaawVar);

    void zza(zzabc zzabcVar, String str);

    void zza(zzahe zzaheVar);

    void zza(zzjn zzjnVar);

    void zza(zzke zzkeVar);

    void zza(zzkh zzkhVar);

    void zza(zzkx zzkxVar);

    void zza(zzla zzlaVar);

    void zza(zzlg zzlgVar);

    void zza(zzlu zzluVar);

    void zza(zzmu zzmuVar);

    void zza(zzod zzodVar);

    boolean zzb(zzjj zzjjVar);

    Bundle zzba();

    a zzbj();

    zzjn zzbk();

    void zzbm();

    zzla zzbw();

    zzkh zzbx();

    String zzck();
}
