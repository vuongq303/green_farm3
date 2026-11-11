package com.google.android.gms.internal.ads;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzhc {
    private final Object zzajo = new Object();

    @GuardedBy("mPoolLock")
    private boolean zzajp = false;
}
