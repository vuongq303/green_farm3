package com.google.android.gms.internal.ads;

import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public final class zzauy implements zzatz {
    private static final byte[] zzdhv = new byte[0];
    private final zzaxn zzdhw;
    private final zzatz zzdhx;

    public zzauy(zzaxn zzaxnVar, zzatz zzatzVar) {
        this.zzdhw = zzaxnVar;
        this.zzdhx = zzatzVar;
    }

    @Override // com.google.android.gms.internal.ads.zzatz
    public final byte[] zzc(byte[] bArr, byte[] bArr2) {
        byte[] byteArray = zzauo.zzb(this.zzdhw).toByteArray();
        byte[] bArrZzc = this.zzdhx.zzc(byteArray, zzdhv);
        byte[] bArrZzc2 = ((zzatz) zzauo.zza(this.zzdhw.zzyw(), byteArray)).zzc(bArr, bArr2);
        return ByteBuffer.allocate(bArrZzc.length + 4 + bArrZzc2.length).putInt(bArrZzc.length).put(bArrZzc).put(bArrZzc2).array();
    }
}
