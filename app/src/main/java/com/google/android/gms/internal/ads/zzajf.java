package com.google.android.gms.internal.ads;

import android.content.Context;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.k;
import com.google.android.gms.common.l;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
final class zzajf implements Runnable {
    private final /* synthetic */ Context val$context;
    private final /* synthetic */ zzaoj zzcoa;

    zzajf(zzaje zzajeVar, Context context, zzaoj zzaojVar) {
        this.val$context = context;
        this.zzcoa = zzaojVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.zzcoa.set(AdvertisingIdClient.getAdvertisingIdInfo(this.val$context));
        } catch (k | l | IOException | IllegalStateException e) {
            this.zzcoa.setException(e);
            zzane.zzb("Exception while getting advertising Id info", e);
        }
    }
}
