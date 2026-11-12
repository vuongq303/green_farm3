package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzaxe extends zzbbo<zzaxe, zza> implements zzbcw {
    private static volatile zzbdf<zzaxe> zzakh;
    private static final zzaxe zzdko = new zzaxe();
    private int zzdir;
    private zzaxg zzdkm;

    public final class zza extends zzbbo.zza<zzaxe, zza> implements zzbcw {
        private zza() {
            super(zzaxe.zzdko);
        }

        /* synthetic */ zza(zzaxf zzaxfVar) {
            this();
        }
    }

    static {
        zzbbo.zza((Class<zzaxe>) zzaxe.class, zzdko);
    }

    private zzaxe() {
    }

    public static zzaxe zzag(zzbah zzbahVar) {
        return (zzaxe) zzbbo.zza(zzdko, zzbahVar);
    }

    public static zzaxe zzyq() {
        return zzdko;
    }

    public final int getKeySize() {
        return this.zzdir;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.google.android.gms.internal.ads.zzbbo$zzb, com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxe>] */
    @Override // com.google.android.gms.internal.ads.zzbbo
    protected final Object zza(int i, Object obj, Object obj2) {
        Object obj3;
        zzaxf zzaxfVar = null;
        switch (zzaxf.zzakf[i - 1]) {
            case 1:
                return new zzaxe();
            case 2:
                return new zza(zzaxfVar);
            case 3:
                return zza(zzdko, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0003\u0000\u0000\u0000\u0001\t\u0002\u000b", new Object[]{"zzdkm", "zzdir"});
            case 4:
                return zzdko;
            case 5:
                zzbdf<zzaxe> zzbdfVar = zzakh;
                if (zzbdfVar != null) {
                    return zzbdfVar;
                }
                synchronized (zzaxe.class) {
                    zzbdf<zzaxe> zzbdfVar2 = zzakh;
                    obj3 = zzbdfVar2;
                    if (zzbdfVar2 == null) {
                        zzbbo.zzb zzbVar = new zzbbo.zzb(zzdko);
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

    public final zzaxg zzym() {
        return this.zzdkm == null ? zzaxg.zzyu() : this.zzdkm;
    }
}
