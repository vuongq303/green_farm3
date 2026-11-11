package com.google.android.gms.internal.measurement;

import com.google.android.gms.common.internal.bf;

/* JADX INFO: loaded from: classes.dex */
final class zzeq {
    final String name;
    final long zzafp;
    final long zzafq;
    final long zzafr;
    final long zzafs;
    final Long zzaft;
    final Long zzafu;
    final Boolean zzafv;
    final String zztd;

    zzeq(String str, String str2, long j, long j2, long j3, long j4, Long l, Long l2, Boolean bool) {
        bf.id(str);
        bf.id(str2);
        bf.ca(j >= 0);
        bf.ca(j2 >= 0);
        bf.ca(j4 >= 0);
        this.zztd = str;
        this.name = str2;
        this.zzafp = j;
        this.zzafq = j2;
        this.zzafr = j3;
        this.zzafs = j4;
        this.zzaft = l;
        this.zzafu = l2;
        this.zzafv = bool;
    }

    final zzeq zza(Long l, Long l2, Boolean bool) {
        return new zzeq(this.zztd, this.name, this.zzafp, this.zzafq, this.zzafr, this.zzafs, l, l2, (bool == null || bool.booleanValue()) ? bool : null);
    }

    final zzeq zzac(long j) {
        return new zzeq(this.zztd, this.name, this.zzafp, this.zzafq, j, this.zzafs, this.zzaft, this.zzafu, this.zzafv);
    }

    final zzeq zzad(long j) {
        return new zzeq(this.zztd, this.name, this.zzafp, this.zzafq, this.zzafr, j, this.zzaft, this.zzafu, this.zzafv);
    }

    final zzeq zzie() {
        return new zzeq(this.zztd, this.name, this.zzafp + 1, this.zzafq + 1, this.zzafr, this.zzafs, this.zzaft, this.zzafu, this.zzafv);
    }
}
