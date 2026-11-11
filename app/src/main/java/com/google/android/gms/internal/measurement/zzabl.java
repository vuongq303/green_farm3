package com.google.android.gms.internal.measurement;

import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
final class zzabl {
    final int tag;
    final byte[] zzbto;

    zzabl(int i, byte[] bArr) {
        this.tag = i;
        this.zzbto = bArr;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzabl)) {
            return false;
        }
        zzabl zzablVar = (zzabl) obj;
        return this.tag == zzablVar.tag && Arrays.equals(this.zzbto, zzablVar.zzbto);
    }

    public final int hashCode() {
        return ((this.tag + 527) * 31) + Arrays.hashCode(this.zzbto);
    }
}
