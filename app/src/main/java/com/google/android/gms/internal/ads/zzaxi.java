package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzaxi extends zzbbo<zzaxi, zza> implements zzbcw {
    private static volatile zzbdf<zzaxi> zzakh;
    private static final zzaxi zzdkv = new zzaxi();
    private String zzdks = "";
    private zzbah zzdkt = zzbah.zzdpq;
    private int zzdku;

    public final class zza extends zzbbo.zza<zzaxi, zza> implements zzbcw {
        private zza() {
            super(zzaxi.zzdkv);
        }

        /* synthetic */ zza(zzaxj zzaxjVar) {
            this();
        }

        public final zza zzai(zzbah zzbahVar) {
            zzadh();
            ((zzaxi) this.zzdtx).zzah(zzbahVar);
            return this;
        }

        public final zza zzb(zzb zzbVar) {
            zzadh();
            ((zzaxi) this.zzdtx).zza(zzbVar);
            return this;
        }

        public final zza zzeb(String str) {
            zzadh();
            ((zzaxi) this.zzdtx).zzea(str);
            return this;
        }
    }

    public enum zzb implements zzbbr {
        UNKNOWN_KEYMATERIAL(0),
        SYMMETRIC(1),
        ASYMMETRIC_PRIVATE(2),
        ASYMMETRIC_PUBLIC(3),
        REMOTE(4),
        UNRECOGNIZED(-1);

        private static final zzbbs<zzb> zzall = new zzaxk();
        private final int value;

        zzb(int i) {
            this.value = i;
        }

        public static zzb zzaw(int i) {
            switch (i) {
                case 0:
                    return UNKNOWN_KEYMATERIAL;
                case 1:
                    return SYMMETRIC;
                case 2:
                    return ASYMMETRIC_PRIVATE;
                case 3:
                    return ASYMMETRIC_PUBLIC;
                case 4:
                    return REMOTE;
                default:
                    return null;
            }
        }

        @Override // com.google.android.gms.internal.ads.zzbbr
        public final int zzhq() {
            if (this == UNRECOGNIZED) {
                throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
            }
            return this.value;
        }
    }

    static {
        zzbbo.zza((Class<zzaxi>) zzaxi.class, zzdkv);
    }

    private zzaxi() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(zzb zzbVar) {
        if (zzbVar == null) {
            throw new NullPointerException();
        }
        this.zzdku = zzbVar.zzhq();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzah(zzbah zzbahVar) {
        if (zzbahVar == null) {
            throw new NullPointerException();
        }
        this.zzdkt = zzbahVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzea(String str) {
        if (str == null) {
            throw new NullPointerException();
        }
        this.zzdks = str;
    }

    public static zza zzyz() {
        return (zza) ((zzbbo.zza) zzdkv.zza(zzbbo.zze.zzdue, (Object) null, (Object) null));
    }

    public static zzaxi zzza() {
        return zzdkv;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.google.android.gms.internal.ads.zzbbo$zzb, com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxi>] */
    @Override // com.google.android.gms.internal.ads.zzbbo
    protected final Object zza(int i, Object obj, Object obj2) {
        Object obj3;
        zzaxj zzaxjVar = null;
        switch (zzaxj.zzakf[i - 1]) {
            case 1:
                return new zzaxi();
            case 2:
                return new zza(zzaxjVar);
            case 3:
                return zza(zzdkv, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0004\u0000\u0000\u0000\u0001Ȉ\u0002\n\u0003\f", new Object[]{"zzdks", "zzdkt", "zzdku"});
            case 4:
                return zzdkv;
            case 5:
                zzbdf<zzaxi> zzbdfVar = zzakh;
                if (zzbdfVar != null) {
                    return zzbdfVar;
                }
                synchronized (zzaxi.class) {
                    zzbdf<zzaxi> zzbdfVar2 = zzakh;
                    obj3 = zzbdfVar2;
                    if (zzbdfVar2 == null) {
                        zzbbo.zzb zzbVar = new zzbbo.zzb(zzdkv);
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

    public final String zzyw() {
        return this.zzdks;
    }

    public final zzbah zzyx() {
        return this.zzdkt;
    }

    public final zzb zzyy() {
        zzb zzbVarZzaw = zzb.zzaw(this.zzdku);
        return zzbVarZzaw == null ? zzb.UNRECOGNIZED : zzbVarZzaw;
    }
}
