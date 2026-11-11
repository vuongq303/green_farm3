package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzabj {
    protected volatile int zzbzs = -1;

    public static final <T extends zzabj> T zza(T t, byte[] bArr) {
        return (T) zzb(t, bArr, 0, bArr.length);
    }

    public static final void zza(zzabj zzabjVar, byte[] bArr, int i, int i2) {
        try {
            zzabb zzabbVarZzb = zzabb.zzb(bArr, 0, i2);
            zzabjVar.zza(zzabbVarZzb);
            zzabbVarZzb.zzvy();
        } catch (IOException e) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", e);
        }
    }

    private static final <T extends zzabj> T zzb(T t, byte[] bArr, int i, int i2) throws zzabi {
        try {
            zzaba zzabaVarZza = zzaba.zza(bArr, 0, i2);
            t.zzb(zzabaVarZza);
            zzabaVarZza.zzal(0);
            return t;
        } catch (zzabi e) {
            throw e;
        } catch (IOException e2) {
            throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).", e2);
        }
    }

    public String toString() {
        return zzabk.zzc(this);
    }

    protected int zza() {
        return 0;
    }

    public void zza(zzabb zzabbVar) {
    }

    public abstract zzabj zzb(zzaba zzabaVar);

    @Override // 
    /* JADX INFO: renamed from: zzvz, reason: merged with bridge method [inline-methods] */
    public zzabj clone() {
        return (zzabj) super.clone();
    }

    public final int zzwf() {
        if (this.zzbzs < 0) {
            zzwg();
        }
        return this.zzbzs;
    }

    public final int zzwg() {
        int iZza = zza();
        this.zzbzs = iZza;
        return iZza;
    }
}
