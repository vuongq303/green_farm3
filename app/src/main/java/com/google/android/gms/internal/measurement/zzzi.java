package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
final class zzzi implements zzaab {
    private static final zzzp zzbvq = new zzzj();
    private final zzzp zzbvp;

    public zzzi() {
        this(new zzzk(zzyu.zzud(), zzuk()));
    }

    private zzzi(zzzp zzzpVar) {
        this.zzbvp = (zzzp) zzyw.zza(zzzpVar, "messageInfoFactory");
    }

    private static boolean zza(zzzo zzzoVar) {
        return zzzoVar.zzuo() == zzyv.zzb.zzbuw;
    }

    private static zzzp zzuk() {
        try {
            return (zzzp) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception e) {
            return zzbvq;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzaab
    public final <T> zzaaa<T> zzg(Class<T> cls) {
        zzaac.zzh(cls);
        zzzo zzzoVarZze = this.zzbvp.zze(cls);
        if (zzzoVarZze.zzup()) {
            return zzyv.class.isAssignableFrom(cls) ? zzzu.zza(zzaac.zzuy(), zzyq.zzty(), zzzoVarZze.zzuq()) : zzzu.zza(zzaac.zzuw(), zzyq.zztz(), zzzoVarZze.zzuq());
        }
        if (zzyv.class.isAssignableFrom(cls)) {
            return zza(zzzoVarZze) ? zzzt.zza(cls, zzzoVarZze, zzzx.zzut(), zzze.zzuj(), zzaac.zzuy(), zzyq.zzty(), zzzn.zzum()) : zzzt.zza(cls, zzzoVarZze, zzzx.zzut(), zzze.zzuj(), zzaac.zzuy(), null, zzzn.zzum());
        }
        return zza(zzzoVarZze) ? zzzt.zza(cls, zzzoVarZze, zzzx.zzus(), zzze.zzui(), zzaac.zzuw(), zzyq.zztz(), zzzn.zzul()) : zzzt.zza(cls, zzzoVarZze, zzzx.zzus(), zzze.zzui(), zzaac.zzux(), null, zzzn.zzul());
    }
}
