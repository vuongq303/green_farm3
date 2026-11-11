package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class zzabg implements Cloneable {
    private Object value;
    private zzabe<?, ?> zzbzp;
    private List<zzabl> zzbzq = new ArrayList();

    zzabg() {
    }

    private final byte[] toByteArray() throws zzabc {
        byte[] bArr = new byte[zza()];
        zza(zzabb.zzk(bArr));
        return bArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: zzwa, reason: merged with bridge method [inline-methods] */
    public final zzabg clone() {
        int i = 0;
        zzabg zzabgVar = new zzabg();
        try {
            zzabgVar.zzbzp = this.zzbzp;
            if (this.zzbzq == null) {
                zzabgVar.zzbzq = null;
            } else {
                zzabgVar.zzbzq.addAll(this.zzbzq);
            }
            if (this.value != null) {
                if (this.value instanceof zzabj) {
                    zzabgVar.value = (zzabj) ((zzabj) this.value).clone();
                } else if (this.value instanceof byte[]) {
                    zzabgVar.value = ((byte[]) this.value).clone();
                } else if (this.value instanceof byte[][]) {
                    byte[][] bArr = (byte[][]) this.value;
                    byte[][] bArr2 = new byte[bArr.length][];
                    zzabgVar.value = bArr2;
                    for (int i2 = 0; i2 < bArr.length; i2++) {
                        bArr2[i2] = (byte[]) bArr[i2].clone();
                    }
                } else if (this.value instanceof boolean[]) {
                    zzabgVar.value = ((boolean[]) this.value).clone();
                } else if (this.value instanceof int[]) {
                    zzabgVar.value = ((int[]) this.value).clone();
                } else if (this.value instanceof long[]) {
                    zzabgVar.value = ((long[]) this.value).clone();
                } else if (this.value instanceof float[]) {
                    zzabgVar.value = ((float[]) this.value).clone();
                } else if (this.value instanceof double[]) {
                    zzabgVar.value = ((double[]) this.value).clone();
                } else if (this.value instanceof zzabj[]) {
                    zzabj[] zzabjVarArr = (zzabj[]) this.value;
                    zzabj[] zzabjVarArr2 = new zzabj[zzabjVarArr.length];
                    zzabgVar.value = zzabjVarArr2;
                    while (true) {
                        int i3 = i;
                        if (i3 >= zzabjVarArr.length) {
                            break;
                        }
                        zzabjVarArr2[i3] = (zzabj) zzabjVarArr[i3].clone();
                        i = i3 + 1;
                    }
                }
            }
            return zzabgVar;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzabg)) {
            return false;
        }
        zzabg zzabgVar = (zzabg) obj;
        if (this.value == null || zzabgVar.value == null) {
            if (this.zzbzq != null && zzabgVar.zzbzq != null) {
                return this.zzbzq.equals(zzabgVar.zzbzq);
            }
            try {
                return Arrays.equals(toByteArray(), zzabgVar.toByteArray());
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        if (this.zzbzp != zzabgVar.zzbzp) {
            return false;
        }
        if (!this.zzbzp.zzbzi.isArray()) {
            return this.value.equals(zzabgVar.value);
        }
        if (this.value instanceof byte[]) {
            return Arrays.equals((byte[]) this.value, (byte[]) zzabgVar.value);
        }
        if (this.value instanceof int[]) {
            return Arrays.equals((int[]) this.value, (int[]) zzabgVar.value);
        }
        if (this.value instanceof long[]) {
            return Arrays.equals((long[]) this.value, (long[]) zzabgVar.value);
        }
        if (this.value instanceof float[]) {
            return Arrays.equals((float[]) this.value, (float[]) zzabgVar.value);
        }
        if (this.value instanceof double[]) {
            return Arrays.equals((double[]) this.value, (double[]) zzabgVar.value);
        }
        return this.value instanceof boolean[] ? Arrays.equals((boolean[]) this.value, (boolean[]) zzabgVar.value) : Arrays.deepEquals((Object[]) this.value, (Object[]) zzabgVar.value);
    }

    public final int hashCode() {
        try {
            return Arrays.hashCode(toByteArray()) + 527;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    final int zza() {
        int length = 0;
        if (this.value == null) {
            for (zzabl zzablVar : this.zzbzq) {
                length = zzablVar.zzbto.length + zzabb.zzau(zzablVar.tag) + 0 + length;
            }
            return length;
        }
        zzabe<?, ?> zzabeVar = this.zzbzp;
        Object obj = this.value;
        if (!zzabeVar.zzbzj) {
            return zzabeVar.zzx(obj);
        }
        int length2 = Array.getLength(obj);
        int iZzx = 0;
        for (int i = 0; i < length2; i++) {
            if (Array.get(obj, i) != null) {
                iZzx += zzabeVar.zzx(Array.get(obj, i));
            }
        }
        return iZzx;
    }

    final void zza(zzabb zzabbVar) throws zzabc {
        if (this.value == null) {
            for (zzabl zzablVar : this.zzbzq) {
                zzabbVar.zzat(zzablVar.tag);
                zzabbVar.zzl(zzablVar.zzbto);
            }
            return;
        }
        zzabe<?, ?> zzabeVar = this.zzbzp;
        Object obj = this.value;
        if (!zzabeVar.zzbzj) {
            zzabeVar.zza(obj, zzabbVar);
            return;
        }
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            Object obj2 = Array.get(obj, i);
            if (obj2 != null) {
                zzabeVar.zza(obj2, zzabbVar);
            }
        }
    }

    final void zza(zzabl zzablVar) throws zzabi {
        Object objZzi;
        if (this.zzbzq != null) {
            this.zzbzq.add(zzablVar);
            return;
        }
        if (this.value instanceof zzabj) {
            byte[] bArr = zzablVar.zzbto;
            zzaba zzabaVarZza = zzaba.zza(bArr, 0, bArr.length);
            int iZzvs = zzabaVarZza.zzvs();
            if (iZzvs != bArr.length - zzabb.zzaq(iZzvs)) {
                throw zzabi.zzwb();
            }
            objZzi = ((zzabj) this.value).zzb(zzabaVarZza);
        } else if (this.value instanceof zzabj[]) {
            zzabj[] zzabjVarArr = (zzabj[]) this.zzbzp.zzi(Collections.singletonList(zzablVar));
            zzabj[] zzabjVarArr2 = (zzabj[]) this.value;
            objZzi = (zzabj[]) Arrays.copyOf(zzabjVarArr2, zzabjVarArr2.length + zzabjVarArr.length);
            System.arraycopy(zzabjVarArr, 0, objZzi, zzabjVarArr2.length, zzabjVarArr.length);
        } else {
            objZzi = this.zzbzp.zzi(Collections.singletonList(zzablVar));
        }
        this.zzbzp = this.zzbzp;
        this.value = objZzi;
        this.zzbzq = null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    final <T> T zzb(zzabe<?, T> zzabeVar) {
        if (this.value == null) {
            this.zzbzp = zzabeVar;
            this.value = zzabeVar.zzi(this.zzbzq);
            this.zzbzq = null;
        } else if (!this.zzbzp.equals(zzabeVar)) {
            throw new IllegalStateException("Tried to getExtension with a different Extension.");
        }
        return (T) this.value;
    }
}
