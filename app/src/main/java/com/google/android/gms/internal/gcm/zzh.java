package com.google.android.gms.internal.gcm;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.dex */
final class zzh {
    private final ConcurrentHashMap<zzi, List<Throwable>> zzdf = new ConcurrentHashMap<>(16, 0.75f, 10);
    private final ReferenceQueue<Throwable> zzdg = new ReferenceQueue<>();

    zzh() {
    }

    public final List<Throwable> zzd(Throwable th, boolean z) {
        Reference<? extends Throwable> referencePoll = this.zzdg.poll();
        while (referencePoll != null) {
            this.zzdf.remove(referencePoll);
            referencePoll = this.zzdg.poll();
        }
        List<Throwable> list = this.zzdf.get(new zzi(th, null));
        if (list != null) {
            return list;
        }
        Vector vector = new Vector(2);
        List<Throwable> listPutIfAbsent = this.zzdf.putIfAbsent(new zzi(th, this.zzdg), vector);
        return listPutIfAbsent == null ? vector : listPutIfAbsent;
    }
}
