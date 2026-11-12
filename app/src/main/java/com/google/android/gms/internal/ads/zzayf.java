package com.google.android.gms.internal.ads;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class zzayf extends zzbbo<zzayf, zza> implements zzbcw {
    private static volatile zzbdf<zzayf> zzakh;
    private static final zzayf zzdmt = new zzayf();
    private int zzdlq;
    private String zzdmr = "";
    private zzbbt<zzaxp> zzdms = zzadd();

    public final class zza extends zzbbo.zza<zzayf, zza> implements zzbcw {
        private zza() {
            super(zzayf.zzdmt);
        }

        /* synthetic */ zza(zzayg zzaygVar) {
            this();
        }

        public final zza zzb(zzaxp zzaxpVar) {
            zzadh();
            ((zzayf) this.zzdtx).zza(zzaxpVar);
            return this;
        }

        public final zza zzej(String str) {
            zzadh();
            ((zzayf) this.zzdtx).zzei(str);
            return this;
        }
    }

    static {
        zzbbo.zza((Class<zzayf>) zzayf.class, zzdmt);
    }

    private zzayf() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(zzaxp zzaxpVar) {
        if (zzaxpVar == null) {
            throw new NullPointerException();
        }
        if (!this.zzdms.zzaay()) {
            zzbbt<zzaxp> zzbbtVar = this.zzdms;
            int size = zzbbtVar.size();
            this.zzdms = zzbbtVar.zzbm(size == 0 ? 10 : size << 1);
        }
        this.zzdms.add(zzaxpVar);
    }

    public static zza zzaam() {
        return (zza) ((zzbbo.zza) zzdmt.zza(zzbbo.zze.zzdue, (Object) null, (Object) null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzei(String str) {
        if (str == null) {
            throw new NullPointerException();
        }
        this.zzdmr = str;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.google.android.gms.internal.ads.zzbbo$zzb, com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzayf>] */
    @Override // com.google.android.gms.internal.ads.zzbbo
    protected final Object zza(int i, Object obj, Object obj2) {
        Object obj3;
        zzayg zzaygVar = null;
        switch (zzayg.zzakf[i - 1]) {
            case 1:
                return new zzayf();
            case 2:
                return new zza(zzaygVar);
            case 3:
                return zza(zzdmt, "\u0000\u0002\u0000\u0001\u0001\u0002\u0002\u0003\u0000\u0001\u0000\u0001Ȉ\u0002\u001b", new Object[]{"zzdlq", "zzdmr", "zzdms", zzaxp.class});
            case 4:
                return zzdmt;
            case 5:
                zzbdf<zzayf> zzbdfVar = zzakh;
                if (zzbdfVar != null) {
                    return zzbdfVar;
                }
                synchronized (zzayf.class) {
                    zzbdf<zzayf> zzbdfVar2 = zzakh;
                    obj3 = zzbdfVar2;
                    if (zzbdfVar2 == null) {
                        zzbbo.zzb zzbVar = new zzbbo.zzb(zzdmt);
                        zzakh = zzbVar;
                        obj3 = zzbVar;
                    }
                    break;
                }
                return obj3;
            case 6:
                return (byte) 1;
            case 7:
                return null;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public final List<zzaxp> zzaal() {
        return this.zzdms;
    }
}
