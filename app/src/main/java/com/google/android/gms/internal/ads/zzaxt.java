package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzaxt extends zzbbo<zzaxt, zza> implements zzbcw {
    private static volatile zzbdf<zzaxt> zzakh;
    private static final zzaxt zzdlz = new zzaxt();
    private int zzdlq;
    private int zzdlr;
    private zzbbt<zzb> zzdly = zzadd();

    public final class zza extends zzbbo.zza<zzaxt, zza> implements zzbcw {
        private zza() {
            super(zzaxt.zzdlz);
        }

        /* synthetic */ zza(zzaxu zzaxuVar) {
            this();
        }

        public final zza zzb(zzb zzbVar) {
            zzadh();
            ((zzaxt) this.zzdtx).zza(zzbVar);
            return this;
        }

        public final zza zzbb(int i) {
            zzadh();
            ((zzaxt) this.zzdtx).zzba(i);
            return this;
        }
    }

    public final class zzb extends zzbbo<zzb, zza> implements zzbcw {
        private static volatile zzbdf<zzb> zzakh;
        private static final zzb zzdma = new zzb();
        private String zzdks = "";
        private int zzdlj;
        private int zzdlv;
        private int zzdlw;

        public final class zza extends zzbbo.zza<zzb, zza> implements zzbcw {
            private zza() {
                super(zzb.zzdma);
            }

            /* synthetic */ zza(zzaxu zzaxuVar) {
                this();
            }

            public final zza zzb(zzaxl zzaxlVar) {
                zzadh();
                ((zzb) this.zzdtx).zza(zzaxlVar);
                return this;
            }

            public final zza zzb(zzayd zzaydVar) {
                zzadh();
                ((zzb) this.zzdtx).zza(zzaydVar);
                return this;
            }

            public final zza zzbd(int i) {
                zzadh();
                ((zzb) this.zzdtx).zzbc(i);
                return this;
            }

            public final zza zzeh(String str) {
                zzadh();
                ((zzb) this.zzdtx).zzea(str);
                return this;
            }
        }

        static {
            zzbbo.zza((Class<zzb>) zzb.class, zzdma);
        }

        private zzb() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzaxl zzaxlVar) {
            if (zzaxlVar == null) {
                throw new NullPointerException();
            }
            this.zzdlv = zzaxlVar.zzhq();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zza(zzayd zzaydVar) {
            if (zzaydVar == null) {
                throw new NullPointerException();
            }
            this.zzdlj = zzaydVar.zzhq();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzbc(int i) {
            this.zzdlw = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void zzea(String str) {
            if (str == null) {
                throw new NullPointerException();
            }
            this.zzdks = str;
        }

        public static zza zzzw() {
            return (zza) ((zzbbo.zza) zzdma.zza(zzbbo.zze.zzdue, (Object) null, (Object) null));
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v8, types: [com.google.android.gms.internal.ads.zzbbo$zzb, com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxt$zzb>] */
        @Override // com.google.android.gms.internal.ads.zzbbo
        protected final Object zza(int i, Object obj, Object obj2) {
            Object obj3;
            zzaxu zzaxuVar = null;
            switch (zzaxu.zzakf[i - 1]) {
                case 1:
                    return new zzb();
                case 2:
                    return new zza(zzaxuVar);
                case 3:
                    return zza(zzdma, "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0005\u0000\u0000\u0000\u0001Ȉ\u0002\f\u0003\u000b\u0004\f", new Object[]{"zzdks", "zzdlv", "zzdlw", "zzdlj"});
                case 4:
                    return zzdma;
                case 5:
                    zzbdf<zzb> zzbdfVar = zzakh;
                    if (zzbdfVar != null) {
                        return zzbdfVar;
                    }
                    synchronized (zzb.class) {
                        zzbdf<zzb> zzbdfVar2 = zzakh;
                        obj3 = zzbdfVar2;
                        if (zzbdfVar2 == null) {
                            zzbbo.zzb zzbVar = new zzbbo.zzb(zzdma);
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

    static {
        zzbbo.zza((Class<zzaxt>) zzaxt.class, zzdlz);
    }

    private zzaxt() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(zzb zzbVar) {
        if (zzbVar == null) {
            throw new NullPointerException();
        }
        if (!this.zzdly.zzaay()) {
            zzbbt<zzb> zzbbtVar = this.zzdly;
            int size = zzbbtVar.size();
            this.zzdly = zzbbtVar.zzbm(size == 0 ? 10 : size << 1);
        }
        this.zzdly.add(zzbVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzba(int i) {
        this.zzdlr = i;
    }

    public static zza zzzu() {
        return (zza) ((zzbbo.zza) zzdlz.zza(zzbbo.zze.zzdue, (Object) null, (Object) null));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.google.android.gms.internal.ads.zzbbo$zzb, com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxt>] */
    @Override // com.google.android.gms.internal.ads.zzbbo
    protected final Object zza(int i, Object obj, Object obj2) {
        Object obj3;
        zzaxu zzaxuVar = null;
        switch (zzaxu.zzakf[i - 1]) {
            case 1:
                return new zzaxt();
            case 2:
                return new zza(zzaxuVar);
            case 3:
                return zza(zzdlz, "\u0000\u0002\u0000\u0001\u0001\u0002\u0002\u0003\u0000\u0001\u0000\u0001\u000b\u0002\u001b", new Object[]{"zzdlq", "zzdlr", "zzdly", zzb.class});
            case 4:
                return zzdlz;
            case 5:
                zzbdf<zzaxt> zzbdfVar = zzakh;
                if (zzbdfVar != null) {
                    return zzbdfVar;
                }
                synchronized (zzaxt.class) {
                    zzbdf<zzaxt> zzbdfVar2 = zzakh;
                    obj3 = zzbdfVar2;
                    if (zzbdfVar2 == null) {
                        zzbbo.zzb zzbVar = new zzbbo.zzb(zzdlz);
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
