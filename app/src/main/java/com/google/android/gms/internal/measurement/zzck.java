package com.google.android.gms.internal.measurement;

import com.google.android.gms.common.d.e;

/* JADX INFO: loaded from: classes.dex */
public final class zzck {
    private final long zzaak;
    private final int zzaal;
    private double zzaam;
    private long zzaan;
    private final Object zzaao;
    private final e zzrj;
    private final String zztv;

    private zzck(int i, long j, String str, e eVar) {
        this.zzaao = new Object();
        this.zzaal = 60;
        this.zzaam = this.zzaal;
        this.zzaak = 2000L;
        this.zztv = str;
        this.zzrj = eVar;
    }

    public zzck(String str, e eVar) {
        this(60, 2000L, str, eVar);
    }

    public final boolean zzer() {
        boolean z;
        synchronized (this.zzaao) {
            long jCurrentTimeMillis = this.zzrj.currentTimeMillis();
            if (this.zzaam < this.zzaal) {
                double d = (jCurrentTimeMillis - this.zzaan) / this.zzaak;
                if (d > 0.0d) {
                    this.zzaam = Math.min(this.zzaal, d + this.zzaam);
                }
            }
            this.zzaan = jCurrentTimeMillis;
            if (this.zzaam >= 1.0d) {
                this.zzaam -= 1.0d;
                z = true;
            } else {
                String str = this.zztv;
                zzcl.zzab(new StringBuilder(String.valueOf(str).length() + 34).append("Excessive ").append(str).append(" detected; call ignored.").toString());
                z = false;
            }
        }
        return z;
    }
}
