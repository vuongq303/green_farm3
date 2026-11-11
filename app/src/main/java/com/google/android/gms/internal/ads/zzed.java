package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzed extends zzei {
    private static volatile Long zztw = null;
    private static final Object zztn = new Object();

    public zzed(zzcz zzczVar, String str, String str2, zzba zzbaVar, int i, int i2) {
        super(zzczVar, str, str2, zzbaVar, i, 33);
    }

    @Override // com.google.android.gms.internal.ads.zzei
    protected final void zzar() {
        if (zztw == null) {
            synchronized (zztn) {
                if (zztw == null) {
                    zztw = (Long) this.zztz.invoke(null, new Object[0]);
                }
            }
        }
        synchronized (this.zztq) {
            this.zztq.zzea = zztw;
        }
    }
}
