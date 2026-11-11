package com.google.android.gms.internal.measurement;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.dex */
final class zzwk {
    private final ConcurrentHashMap<zzwl, List<Throwable>> zzbnc = new ConcurrentHashMap<>(16, 0.75f, 10);
    private final ReferenceQueue<Throwable> zzbnd = new ReferenceQueue<>();

    zzwk() {
    }

    public final List<Throwable> zza(Throwable th, boolean z) {
        Reference<? extends Throwable> referencePoll = this.zzbnd.poll();
        while (referencePoll != null) {
            this.zzbnc.remove(referencePoll);
            referencePoll = this.zzbnd.poll();
        }
        return this.zzbnc.get(new zzwl(th, null));
    }
}
