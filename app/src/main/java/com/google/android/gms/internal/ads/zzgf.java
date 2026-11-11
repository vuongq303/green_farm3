package com.google.android.gms.internal.ads;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzgf {
    private int zzahm;
    private final Object mLock = new Object();
    private List<zzge> zzahn = new LinkedList();

    public final boolean zza(zzge zzgeVar) {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzahn.contains(zzgeVar);
        }
        return z;
    }

    public final boolean zzb(zzge zzgeVar) {
        synchronized (this.mLock) {
            Iterator<zzge> it = this.zzahn.iterator();
            while (it.hasNext()) {
                zzge next = it.next();
                if (!((Boolean) zzkb.zzik().zzd(zznk.zzawq)).booleanValue() || com.google.android.gms.ads.internal.zzbv.zzeo().zzqh().zzqu()) {
                    if (((Boolean) zzkb.zzik().zzd(zznk.zzaws)).booleanValue() && !com.google.android.gms.ads.internal.zzbv.zzeo().zzqh().zzqw() && zzgeVar != next && next.zzgp().equals(zzgeVar.zzgp())) {
                        it.remove();
                        return true;
                    }
                } else if (zzgeVar != next && next.getSignature().equals(zzgeVar.getSignature())) {
                    it.remove();
                    return true;
                }
            }
            return false;
        }
    }

    public final void zzc(zzge zzgeVar) {
        synchronized (this.mLock) {
            if (this.zzahn.size() >= 10) {
                zzakb.zzck(new StringBuilder(41).append("Queue is full, current size = ").append(this.zzahn.size()).toString());
                this.zzahn.remove(0);
            }
            int i = this.zzahm;
            this.zzahm = i + 1;
            zzgeVar.zzo(i);
            this.zzahn.add(zzgeVar);
        }
    }

    public final zzge zzgv() {
        zzge zzgeVar = null;
        synchronized (this.mLock) {
            if (this.zzahn.size() == 0) {
                zzakb.zzck("Queue empty");
                return null;
            }
            if (this.zzahn.size() < 2) {
                zzge zzgeVar2 = this.zzahn.get(0);
                zzgeVar2.zzgq();
                return zzgeVar2;
            }
            int i = Integer.MIN_VALUE;
            int i2 = 0;
            int i3 = 0;
            for (zzge zzgeVar3 : this.zzahn) {
                int score = zzgeVar3.getScore();
                if (score > i) {
                    i2 = i3;
                } else {
                    score = i;
                    zzgeVar3 = zzgeVar;
                }
                i3++;
                i = score;
                zzgeVar = zzgeVar3;
            }
            this.zzahn.remove(i2);
            return zzgeVar;
        }
    }
}
