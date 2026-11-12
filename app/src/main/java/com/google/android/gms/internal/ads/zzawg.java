package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzawg extends zzbbo<zzawg, zza> implements zzbcw {
    private static volatile zzbdf<zzawg> zzakh;
    private static final zzawg zzdja = new zzawg();
    private int zzdir;

    public final class zza extends zzbbo.zza<zzawg, zza> implements zzbcw {
        private zza() {
            super(zzawg.zzdja);
        }

        /* synthetic */ zza(zzawh zzawhVar) {
            this();
        }
    }

    static {
        zzbbo.zza((Class<zzawg>) zzawg.class, zzdja);
    }

    private zzawg() {
    }

    public static zzawg zzt(zzbah zzbahVar) {
        return (zzawg) zzbbo.zza(zzdja, zzbahVar);
    }

    public final int getKeySize() {
        return this.zzdir;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.google.android.gms.internal.ads.zzbbo$zzb, com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzawg>] */
    @Override // com.google.android.gms.internal.ads.zzbbo
    protected final Object zza(int i, Object obj, Object obj2) {
        Object obj3;
        zzawh zzawhVar = null;
        switch (zzawh.zzakf[i - 1]) {
            case 1:
                return new zzawg();
            case 2:
                return new zza(zzawhVar);
            case 3:
                return zza(zzdja, "\u0000\u0001\u0000\u0000\u0002\u0002\u0001\u0003\u0000\u0000\u0000\u0002\u000b", new Object[]{"zzdir"});
            case 4:
                return zzdja;
            case 5:
                zzbdf<zzawg> zzbdfVar = zzakh;
                if (zzbdfVar != null) {
                    return zzbdfVar;
                }
                synchronized (zzawg.class) {
                    zzbdf<zzawg> zzbdfVar2 = zzakh;
                    obj3 = zzbdfVar2;
                    if (zzbdfVar2 == null) {
                        zzbbo.zzb zzbVar = new zzbbo.zzb(zzdja);
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
}
