package com.google.android.gms.ads.internal;

import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzakk;
import com.google.android.gms.internal.ads.zzjj;
import java.lang.ref.WeakReference;
import javax.annotation.ParametersAreNonnullByDefault;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzbl {
    private final zzbn bBY;
    private boolean bBZ;
    private long bCa;
    private zzjj zzaao;
    private boolean zzaaq;
    private final Runnable zzy;

    public zzbl(zza zzaVar) {
        this(zzaVar, new zzbn(zzakk.zzcrm));
    }

    private zzbl(zza zzaVar, zzbn zzbnVar) {
        this.bBZ = false;
        this.zzaaq = false;
        this.bCa = 0L;
        this.bBY = zzbnVar;
        this.zzy = new z(this, new WeakReference(zzaVar));
    }

    static /* synthetic */ boolean a(zzbl zzblVar, boolean z) {
        zzblVar.bBZ = false;
        return false;
    }

    public final void cancel() {
        this.bBZ = false;
        this.bBY.removeCallbacks(this.zzy);
    }

    public final void pause() {
        this.zzaaq = true;
        if (this.bBZ) {
            this.bBY.removeCallbacks(this.zzy);
        }
    }

    public final void resume() {
        this.zzaaq = false;
        if (this.bBZ) {
            this.bBZ = false;
            zza(this.zzaao, this.bCa);
        }
    }

    public final void zza(zzjj zzjjVar, long j) {
        if (this.bBZ) {
            zzakb.zzdk("An ad refresh is already scheduled.");
            return;
        }
        this.zzaao = zzjjVar;
        this.bBZ = true;
        this.bCa = j;
        if (this.zzaaq) {
            return;
        }
        zzakb.zzdj(new StringBuilder(65).append("Scheduling ad refresh ").append(j).append(" milliseconds from now.").toString());
        this.bBY.postDelayed(this.zzy, j);
    }

    public final void zzdy() {
        this.zzaaq = false;
        this.bBZ = false;
        if (this.zzaao != null && this.zzaao.extras != null) {
            this.zzaao.extras.remove("_ad");
        }
        zza(this.zzaao, 0L);
    }

    public final boolean zzdz() {
        return this.bBZ;
    }

    public final void zzf(zzjj zzjjVar) {
        this.zzaao = zzjjVar;
    }

    public final void zzg(zzjj zzjjVar) {
        zza(zzjjVar, 60000L);
    }
}
