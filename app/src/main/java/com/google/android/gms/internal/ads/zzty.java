package com.google.android.gms.internal.ads;

import com.google.android.gms.common.internal.bf;
import java.util.Iterator;
import java.util.LinkedList;
import javax.annotation.ParametersAreNonnullByDefault;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
final class zzty {
    private final LinkedList<zztz> zzbon;
    private zzjj zzboo;
    private final int zzbop;
    private boolean zzboq;
    private final String zzye;

    zzty(zzjj zzjjVar, String str, int i) {
        bf.checkNotNull(zzjjVar);
        bf.checkNotNull(str);
        this.zzbon = new LinkedList<>();
        this.zzboo = zzjjVar;
        this.zzye = str;
        this.zzbop = i;
    }

    final String getAdUnitId() {
        return this.zzye;
    }

    final int getNetworkType() {
        return this.zzbop;
    }

    final int size() {
        return this.zzbon.size();
    }

    final void zza(zzss zzssVar, zzjj zzjjVar) {
        this.zzbon.add(new zztz(this, zzssVar, zzjjVar));
    }

    final boolean zzb(zzss zzssVar) {
        zztz zztzVar = new zztz(this, zzssVar);
        this.zzbon.add(zztzVar);
        return zztzVar.load();
    }

    final zztz zzl(zzjj zzjjVar) {
        if (zzjjVar != null) {
            this.zzboo = zzjjVar;
        }
        return this.zzbon.remove();
    }

    final zzjj zzlf() {
        return this.zzboo;
    }

    final int zzlg() {
        int i = 0;
        Iterator<zztz> it = this.zzbon.iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return i2;
            }
            i = it.next().zzwa ? i2 + 1 : i2;
        }
    }

    final int zzlh() {
        int i = 0;
        Iterator<zztz> it = this.zzbon.iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return i2;
            }
            i = it.next().load() ? i2 + 1 : i2;
        }
    }

    final void zzli() {
        this.zzboq = true;
    }

    final boolean zzlj() {
        return this.zzboq;
    }
}
