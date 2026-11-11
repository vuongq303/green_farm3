package com.google.android.gms.internal.ads;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class zzdy extends zzei {
    private List<Long> zztt;

    public zzdy(zzcz zzczVar, String str, String str2, zzba zzbaVar, int i, int i2) {
        super(zzczVar, str, str2, zzbaVar, i, 31);
        this.zztt = null;
    }

    @Override // com.google.android.gms.internal.ads.zzei
    protected final void zzar() {
        this.zztq.zzdy = -1L;
        this.zztq.zzdz = -1L;
        if (this.zztt == null) {
            this.zztt = (List) this.zztz.invoke(null, this.zzps.getContext());
        }
        if (this.zztt == null || this.zztt.size() != 2) {
            return;
        }
        synchronized (this.zztq) {
            this.zztq.zzdy = Long.valueOf(this.zztt.get(0).longValue());
            this.zztq.zzdz = Long.valueOf(this.zztt.get(1).longValue());
        }
    }
}
