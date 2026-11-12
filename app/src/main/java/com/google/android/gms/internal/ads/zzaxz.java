package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzaxz extends zzbbo<zzaxz, zza> implements zzbcw {
    private static volatile zzbdf<zzaxz> zzakh;
    private static final zzaxz zzdmg = new zzaxz();
    private int zzdih;
    private zzayb zzdmf;

    public final class zza extends zzbbo.zza<zzaxz, zza> implements zzbcw {
        private zza() {
            super(zzaxz.zzdmg);
        }

        /* synthetic */ zza(zzaya zzayaVar) {
            this();
        }

        public final zza zzb(zzayb zzaybVar) {
            zzadh();
            ((zzaxz) this.zzdtx).zza(zzaybVar);
            return this;
        }

        public final zza zzbf(int i) {
            zzadh();
            ((zzaxz) this.zzdtx).setVersion(0);
            return this;
        }
    }

    static {
        zzbbo.zza((Class<zzaxz>) zzaxz.class, zzdmg);
    }

    private zzaxz() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setVersion(int i) {
        this.zzdih = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(zzayb zzaybVar) {
        if (zzaybVar == null) {
            throw new NullPointerException();
        }
        this.zzdmf = zzaybVar;
    }

    public static zza zzaaf() {
        return (zza) ((zzbbo.zza) zzdmg.zza(zzbbo.zze.zzdue, (Object) null, (Object) null));
    }

    public static zzaxz zzal(zzbah zzbahVar) {
        return (zzaxz) zzbbo.zza(zzdmg, zzbahVar);
    }

    public final int getVersion() {
        return this.zzdih;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.google.android.gms.internal.ads.zzbbo$zzb, com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxz>] */
    @Override // com.google.android.gms.internal.ads.zzbbo
    protected final Object zza(int i, Object obj, Object obj2) {
        Object obj3;
        zzaya zzayaVar = null;
        switch (zzaya.zzakf[i - 1]) {
            case 1:
                return new zzaxz();
            case 2:
                return new zza(zzayaVar);
            case 3:
                return zza(zzdmg, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0003\u0000\u0000\u0000\u0001\u000b\u0002\t", new Object[]{"zzdih", "zzdmf"});
            case 4:
                return zzdmg;
            case 5:
                zzbdf<zzaxz> zzbdfVar = zzakh;
                if (zzbdfVar != null) {
                    return zzbdfVar;
                }
                synchronized (zzaxz.class) {
                    zzbdf<zzaxz> zzbdfVar2 = zzakh;
                    obj3 = zzbdfVar2;
                    if (zzbdfVar2 == null) {
                        zzbbo.zzb zzbVar = new zzbbo.zzb(zzdmg);
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

    public final zzayb zzaae() {
        return this.zzdmf == null ? zzayb.zzaaj() : this.zzdmf;
    }
}
