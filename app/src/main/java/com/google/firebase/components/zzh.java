package com.google.firebase.components;

import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
final class zzh {
    private final Component<?> zzaj;
    private final Set<zzh> zzak = new HashSet();
    private final Set<zzh> zzal = new HashSet();

    zzh(Component<?> component) {
        this.zzaj = component;
    }

    final void zza(zzh zzhVar) {
        this.zzak.add(zzhVar);
    }

    final void zzb(zzh zzhVar) {
        this.zzal.add(zzhVar);
    }

    final void zzc(zzh zzhVar) {
        this.zzal.remove(zzhVar);
    }

    final Set<zzh> zzf() {
        return this.zzak;
    }

    final Component<?> zzk() {
        return this.zzaj;
    }

    final boolean zzl() {
        return this.zzal.isEmpty();
    }

    final boolean zzm() {
        return this.zzak.isEmpty();
    }
}
