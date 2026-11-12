package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzavs extends zzbbo<zzavs, zza> implements zzbcw {
    private static volatile zzbdf<zzavs> zzakh;
    private static final zzavs zzdiq = new zzavs();
    private int zzdih;
    private zzavw zzdio;
    private zzbah zzdip = zzbah.zzdpq;

    public final class zza extends zzbbo.zza<zzavs, zza> implements zzbcw {
        private zza() {
            super(zzavs.zzdiq);
        }

        /* synthetic */ zza(zzavt zzavtVar) {
            this();
        }

        public final zza zzam(int i) {
            zzadh();
            ((zzavs) this.zzdtx).setVersion(0);
            return this;
        }

        public final zza zzc(zzavw zzavwVar) {
            zzadh();
            ((zzavs) this.zzdtx).zzb(zzavwVar);
            return this;
        }

        public final zza zzm(zzbah zzbahVar) {
            zzadh();
            ((zzavs) this.zzdtx).zzk(zzbahVar);
            return this;
        }
    }

    static {
        zzbbo.zza((Class<zzavs>) zzavs.class, zzdiq);
    }

    private zzavs() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setVersion(int i) {
        this.zzdih = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzb(zzavw zzavwVar) {
        if (zzavwVar == null) {
            throw new NullPointerException();
        }
        this.zzdio = zzavwVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzk(zzbah zzbahVar) {
        if (zzbahVar == null) {
            throw new NullPointerException();
        }
        this.zzdip = zzbahVar;
    }

    public static zzavs zzl(zzbah zzbahVar) {
        return (zzavs) zzbbo.zza(zzdiq, zzbahVar);
    }

    public static zza zzww() {
        return (zza) ((zzbbo.zza) zzdiq.zza(zzbbo.zze.zzdue, (Object) null, (Object) null));
    }

    public static zzavs zzwx() {
        return zzdiq;
    }

    public final int getVersion() {
        return this.zzdih;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.google.android.gms.internal.ads.zzbbo$zzb, com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzavs>] */
    @Override // com.google.android.gms.internal.ads.zzbbo
    protected final Object zza(int i, Object obj, Object obj2) {
        Object obj3;
        zzavt zzavtVar = null;
        switch (zzavt.zzakf[i - 1]) {
            case 1:
                return new zzavs();
            case 2:
                return new zza(zzavtVar);
            case 3:
                return zza(zzdiq, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0004\u0000\u0000\u0000\u0001\u000b\u0002\t\u0003\n", new Object[]{"zzdih", "zzdio", "zzdip"});
            case 4:
                return zzdiq;
            case 5:
                zzbdf<zzavs> zzbdfVar = zzakh;
                if (zzbdfVar != null) {
                    return zzbdfVar;
                }
                synchronized (zzavs.class) {
                    zzbdf<zzavs> zzbdfVar2 = zzakh;
                    obj3 = zzbdfVar2;
                    if (zzbdfVar2 == null) {
                        zzbbo.zzb zzbVar = new zzbbo.zzb(zzdiq);
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

    public final zzavw zzwu() {
        return this.zzdio == null ? zzavw.zzxc() : this.zzdio;
    }

    public final zzbah zzwv() {
        return this.zzdip;
    }
}
