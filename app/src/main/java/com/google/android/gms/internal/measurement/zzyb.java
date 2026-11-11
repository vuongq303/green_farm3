package com.google.android.gms.internal.measurement;

import java.io.Serializable;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzyb implements Serializable, Iterable<Byte> {
    public static final zzyb zzbtj = new zzyh(zzyw.zzbvc);
    private static final zzyf zzbtk;
    private int zzbne = 0;

    /* JADX WARN: Multi-variable type inference failed */
    static {
        zzyc zzycVar = null;
        zzbtk = zzya.zztn() ? new zzyi(zzycVar) : new zzyd(zzycVar);
    }

    zzyb() {
    }

    static int zzb(int i, int i2, int i3) {
        int i4 = i2 - i;
        if ((i | i2 | i4 | (i3 - i2)) >= 0) {
            return i4;
        }
        if (i < 0) {
            throw new IndexOutOfBoundsException(new StringBuilder(32).append("Beginning index: ").append(i).append(" < 0").toString());
        }
        if (i2 < i) {
            throw new IndexOutOfBoundsException(new StringBuilder(66).append("Beginning index larger than ending index: ").append(i).append(", ").append(i2).toString());
        }
        throw new IndexOutOfBoundsException(new StringBuilder(37).append("End index: ").append(i2).append(" >= ").append(i3).toString());
    }

    public static zzyb zzfl(String str) {
        return new zzyh(str.getBytes(zzyw.UTF_8));
    }

    public abstract boolean equals(Object obj);

    public final int hashCode() {
        int iZza = this.zzbne;
        if (iZza == 0) {
            int size = size();
            iZza = zza(size, 0, size);
            if (iZza == 0) {
                iZza = 1;
            }
            this.zzbne = iZza;
        }
        return iZza;
    }

    @Override // java.lang.Iterable
    public /* synthetic */ Iterator<Byte> iterator() {
        return new zzyc(this);
    }

    public abstract int size();

    public final String toString() {
        return String.format("<ByteString@%s size=%d>", Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(size()));
    }

    protected abstract int zza(int i, int i2, int i3);

    public abstract byte zzag(int i);

    public abstract zzyb zzb(int i, int i2);

    protected final int zztp() {
        return this.zzbne;
    }
}
