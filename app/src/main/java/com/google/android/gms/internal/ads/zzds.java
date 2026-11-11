package com.google.android.gms.internal.ads;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class zzds extends zzei {
    public zzds(zzcz zzczVar, String str, String str2, zzba zzbaVar, int i, int i2) {
        super(zzczVar, str, str2, zzbaVar, i, 24);
    }

    private final void zzau() {
        AdvertisingIdClient advertisingIdClientZzan = this.zzps.zzan();
        if (advertisingIdClientZzan == null) {
            return;
        }
        try {
            AdvertisingIdClient.Info info = advertisingIdClientZzan.getInfo();
            String strZzn = zzdg.zzn(info.getId());
            if (strZzn != null) {
                synchronized (this.zztq) {
                    this.zztq.zzfi = strZzn;
                    this.zztq.zzfk = Boolean.valueOf(info.isLimitAdTrackingEnabled());
                    this.zztq.zzfj = 5;
                }
            }
        } catch (IOException e) {
        }
    }

    @Override // com.google.android.gms.internal.ads.zzei, java.util.concurrent.Callable
    public final /* synthetic */ Object call() {
        return call();
    }

    @Override // com.google.android.gms.internal.ads.zzei
    protected final void zzar() {
        if (this.zzps.zzaf()) {
            zzau();
            return;
        }
        synchronized (this.zztq) {
            this.zztq.zzfi = (String) this.zztz.invoke(null, this.zzps.getContext());
        }
    }

    @Override // com.google.android.gms.internal.ads.zzei
    /* JADX INFO: renamed from: zzat */
    public final Void call() {
        if (this.zzps.isInitialized()) {
            return super.call();
        }
        if (this.zzps.zzaf()) {
            zzau();
        }
        return null;
    }
}
