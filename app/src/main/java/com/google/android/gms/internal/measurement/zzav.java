package com.google.android.gms.internal.measurement;

import android.content.Context;
import com.google.android.gms.common.internal.bf;

/* JADX INFO: loaded from: classes.dex */
public final class zzav {
    private final Context zzwa;
    private final Context zzwb;

    public zzav(Context context) {
        bf.checkNotNull(context);
        Context applicationContext = context.getApplicationContext();
        bf.d(applicationContext, "Application context can't be null");
        this.zzwa = applicationContext;
        this.zzwb = applicationContext;
    }

    public final Context getApplicationContext() {
        return this.zzwa;
    }

    public final Context zzci() {
        return this.zzwb;
    }
}
