package com.google.android.gms.internal.ads;

import android.content.Context;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzwg {
    private final Object mLock = new Object();

    @GuardedBy("mLock")
    private zzwn zzbrb;

    public final zzwn zzb(Context context, zzang zzangVar) {
        zzwn zzwnVar;
        synchronized (this.mLock) {
            if (this.zzbrb == null) {
                Context applicationContext = context.getApplicationContext();
                if (applicationContext != null) {
                    context = applicationContext;
                }
                this.zzbrb = new zzwn(context, zzangVar, (String) zzkb.zzik().zzd(zznk.zzaub));
            }
            zzwnVar = this.zzbrb;
        }
        return zzwnVar;
    }
}
