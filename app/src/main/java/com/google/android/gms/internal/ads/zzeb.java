package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzeb extends zzei {
    public zzeb(zzcz zzczVar, String str, String str2, zzba zzbaVar, int i, int i2) {
        super(zzczVar, str, str2, zzbaVar, i, 51);
    }

    @Override // com.google.android.gms.internal.ads.zzei
    protected final void zzar() {
        synchronized (this.zztq) {
            zzcy zzcyVar = new zzcy((String) this.zztz.invoke(null, new Object[0]));
            this.zztq.zzen = zzcyVar.zzrr;
            this.zztq.zzeo = zzcyVar.zzrs;
        }
    }
}
