package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzec extends zzei {
    private final zzdi zzqx;
    private long zzti;

    public zzec(zzcz zzczVar, String str, String str2, zzba zzbaVar, int i, int i2, zzdi zzdiVar) {
        super(zzczVar, str, str2, zzbaVar, i, 53);
        this.zzqx = zzdiVar;
        if (zzdiVar != null) {
            this.zzti = zzdiVar.zzap();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzei
    protected final void zzar() {
        if (this.zzqx != null) {
            this.zztq.zzep = (Long) this.zztz.invoke(null, Long.valueOf(this.zzti));
        }
    }
}
