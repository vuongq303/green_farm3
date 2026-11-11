package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzavo extends zzbbo<zzavo, zza> implements zzbcw {
    private static volatile zzbdf<zzavo> zzakh;
    private static final zzavo zzdik = new zzavo();
    private int zzdih;
    private zzavs zzdii;
    private zzaxc zzdij;

    public final class zza extends zzbbo.zza<zzavo, zza> implements zzbcw {
        private zza() {
            super(zzavo.zzdik);
        }

        /* synthetic */ zza(zzavp zzavpVar) {
            this();
        }

        public final zza zzal(int i) {
            zzadh();
            ((zzavo) this.zzdtx).setVersion(i);
            return this;
        }

        public final zza zzb(zzavs zzavsVar) {
            zzadh();
            ((zzavo) this.zzdtx).zza(zzavsVar);
            return this;
        }

        public final zza zzb(zzaxc zzaxcVar) {
            zzadh();
            ((zzavo) this.zzdtx).zza(zzaxcVar);
            return this;
        }
    }

    static {
        zzbbo.zza((Class<zzavo>) zzavo.class, zzdik);
    }

    private zzavo() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setVersion(int i) {
        this.zzdih = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(zzavs zzavsVar) {
        if (zzavsVar == null) {
            throw new NullPointerException();
        }
        this.zzdii = zzavsVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(zzaxc zzaxcVar) {
        if (zzaxcVar == null) {
            throw new NullPointerException();
        }
        this.zzdij = zzaxcVar;
    }

    public static zzavo zzi(zzbah zzbahVar) {
        return (zzavo) zzbbo.zza(zzdik, zzbahVar);
    }

    public static zza zzwp() {
        return (zza) ((zzbbo.zza) zzdik.zza(zzbbo.zze.zzdue, (Object) null, (Object) null));
    }

    public final int getVersion() {
        return this.zzdih;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.google.android.gms.internal.ads.zzbbo$zzb, com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzavo>] */
    @Override // com.google.android.gms.internal.ads.zzbbo
    protected final Object zza(int i, Object obj, Object obj2) {
        Object obj3;
        zzavp zzavpVar = null;
        switch (zzavp.zzakf[i - 1]) {
            case 1:
                return new zzavo();
            case 2:
                return new zza(zzavpVar);
            case 3:
                return zza(zzdik, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0004\u0000\u0000\u0000\u0001\u000b\u0002\t\u0003\t", new Object[]{"zzdih", "zzdii", "zzdij"});
            case 4:
                return zzdik;
            case 5:
                zzbdf<zzavo> zzbdfVar = zzakh;
                if (zzbdfVar != null) {
                    return zzbdfVar;
                }
                synchronized (zzavo.class) {
                    zzbdf<zzavo> zzbdfVar2 = zzakh;
                    obj3 = zzbdfVar2;
                    if (zzbdfVar2 == null) {
                        ?? zzbVar = new zzbbo.zzb(zzdik);
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

    public final zzavs zzwn() {
        return this.zzdii == null ? zzavs.zzwx() : this.zzdii;
    }

    public final zzaxc zzwo() {
        return this.zzdij == null ? zzaxc.zzyo() : this.zzdij;
    }
}
