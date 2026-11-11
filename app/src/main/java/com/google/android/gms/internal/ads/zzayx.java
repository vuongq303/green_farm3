package com.google.android.gms.internal.ads;

import java.nio.ByteBuffer;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public final class zzayx implements zzatz {
    private final zzazi zzdnv;
    private final zzauk zzdnw;
    private final int zzdnx;

    public zzayx(zzazi zzaziVar, zzauk zzaukVar, int i) {
        this.zzdnv = zzaziVar;
        this.zzdnw = zzaukVar;
        this.zzdnx = i;
    }

    @Override // com.google.android.gms.internal.ads.zzatz
    public final byte[] zzc(byte[] bArr, byte[] bArr2) {
        byte[] bArrZzk = this.zzdnv.zzk(bArr);
        if (bArr2 == null) {
            bArr2 = new byte[0];
        }
        return zzayk.zza(bArrZzk, this.zzdnw.zzg(zzayk.zza(bArr2, bArrZzk, Arrays.copyOf(ByteBuffer.allocate(8).putLong(8 * ((long) bArr2.length)).array(), 8))));
    }
}
