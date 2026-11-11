package com.google.android.gms.internal.ads;

import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
final class zzbfk {
    final int tag;
    final byte[] zzdpw;

    zzbfk(int i, byte[] bArr) {
        this.tag = i;
        this.zzdpw = bArr;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbfk)) {
            return false;
        }
        zzbfk zzbfkVar = (zzbfk) obj;
        return this.tag == zzbfkVar.tag && Arrays.equals(this.zzdpw, zzbfkVar.zzdpw);
    }

    public final int hashCode() {
        return ((this.tag + 527) * 31) + Arrays.hashCode(this.zzdpw);
    }
}
