package com.google.android.gms.internal.measurement;

import android.content.Context;
import com.google.android.gms.common.internal.bf;

/* JADX INFO: loaded from: classes.dex */
public final class zzhl {
    final Context zzqs;

    public zzhl(Context context) {
        bf.checkNotNull(context);
        Context applicationContext = context.getApplicationContext();
        bf.checkNotNull(applicationContext);
        this.zzqs = applicationContext;
    }
}
