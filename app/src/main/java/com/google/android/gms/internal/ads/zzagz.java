package com.google.android.gms.internal.ads;

import android.os.Bundle;
import android.os.IInterface;
import com.google.android.gms.c.a;

/* JADX INFO: loaded from: classes.dex */
public interface zzagz extends IInterface {
    void destroy();

    String getMediationAdapterClassName();

    boolean isLoaded();

    void pause();

    void resume();

    void setImmersiveMode(boolean z);

    void setUserId(String str);

    void show();

    void zza(zzagx zzagxVar);

    void zza(zzahe zzaheVar);

    void zza(zzahk zzahkVar);

    void zza(zzkx zzkxVar);

    Bundle zzba();

    void zzd(a aVar);

    void zze(a aVar);

    void zzf(a aVar);
}
