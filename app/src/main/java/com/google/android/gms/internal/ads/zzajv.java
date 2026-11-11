package com.google.android.gms.internal.ads;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzajv implements zzgj {
    private final Object lock;
    private final zzajr zzcqn;
    private final HashSet<zzajj> zzcqo;
    private final HashSet<zzaju> zzcqp;

    public zzajv() {
        this(zzkb.zzih());
    }

    private zzajv(String str) {
        this.lock = new Object();
        this.zzcqo = new HashSet<>();
        this.zzcqp = new HashSet<>();
        this.zzcqn = new zzajr(str);
    }

    public final Bundle zza(Context context, zzajs zzajsVar, String str) {
        Bundle bundle;
        synchronized (this.lock) {
            bundle = new Bundle();
            bundle.putBundle("app", this.zzcqn.zzk(context, str));
            Bundle bundle2 = new Bundle();
            for (zzaju zzajuVar : this.zzcqp) {
                bundle2.putBundle(zzajuVar.zzqm(), zzajuVar.toBundle());
            }
            bundle.putBundle("slots", bundle2);
            ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
            Iterator<zzajj> it = this.zzcqo.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().toBundle());
            }
            bundle.putParcelableArrayList("ads", arrayList);
            zzajsVar.zza(this.zzcqo);
            this.zzcqo.clear();
        }
        return bundle;
    }

    public final void zza(zzajj zzajjVar) {
        synchronized (this.lock) {
            this.zzcqo.add(zzajjVar);
        }
    }

    public final void zza(zzaju zzajuVar) {
        synchronized (this.lock) {
            this.zzcqp.add(zzajuVar);
        }
    }

    public final void zzb(zzjj zzjjVar, long j) {
        synchronized (this.lock) {
            this.zzcqn.zzb(zzjjVar, j);
        }
    }

    public final void zzb(HashSet<zzajj> hashSet) {
        synchronized (this.lock) {
            this.zzcqo.addAll(hashSet);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzgj
    public final void zzh(boolean z) {
        long jCurrentTimeMillis = com.google.android.gms.ads.internal.zzbv.zzer().currentTimeMillis();
        if (!z) {
            com.google.android.gms.ads.internal.zzbv.zzeo().zzqh().zzj(jCurrentTimeMillis);
            com.google.android.gms.ads.internal.zzbv.zzeo().zzqh().zzaf(this.zzcqn.zzcqg);
            return;
        }
        if (jCurrentTimeMillis - com.google.android.gms.ads.internal.zzbv.zzeo().zzqh().zzrb() > ((Long) zzkb.zzik().zzd(zznk.zzayi)).longValue()) {
            this.zzcqn.zzcqg = -1;
        } else {
            this.zzcqn.zzcqg = com.google.android.gms.ads.internal.zzbv.zzeo().zzqh().zzrc();
        }
    }

    public final void zzpm() {
        synchronized (this.lock) {
            this.zzcqn.zzpm();
        }
    }

    public final void zzpn() {
        synchronized (this.lock) {
            this.zzcqn.zzpn();
        }
    }
}
