package com.google.android.gms.internal.ads;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.GeneralSecurityException;

/* JADX INFO: loaded from: classes.dex */
abstract class zzazo implements zzatz {
    private final byte[] key;
    private final zzazn zzdor;
    private final zzazn zzdos;

    zzazo(byte[] bArr) {
        this.key = (byte[]) bArr.clone();
        this.zzdor = zzc(bArr, 1);
        this.zzdos = zzc(bArr, 0);
    }

    abstract zzazn zzc(byte[] bArr, int i);

    @Override // com.google.android.gms.internal.ads.zzatz
    public byte[] zzc(byte[] bArr, byte[] bArr2) throws GeneralSecurityException {
        int length = bArr.length;
        this.zzdor.zzaao();
        if (length > 2147483619) {
            throw new GeneralSecurityException("plaintext too long");
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArr.length + this.zzdor.zzaao() + 16);
        if (byteBufferAllocate.remaining() < bArr.length + this.zzdor.zzaao() + 16) {
            throw new IllegalArgumentException("Given ByteBuffer output is too small");
        }
        int iPosition = byteBufferAllocate.position();
        this.zzdor.zza(byteBufferAllocate, bArr);
        byteBufferAllocate.position(iPosition);
        byte[] bArr3 = new byte[this.zzdor.zzaao()];
        byteBufferAllocate.get(bArr3);
        byteBufferAllocate.limit(byteBufferAllocate.limit() - 16);
        if (bArr2 == null) {
            bArr2 = new byte[0];
        }
        byte[] bArr4 = new byte[32];
        this.zzdos.zzb(bArr3, 0).get(bArr4);
        int length2 = bArr2.length % 16 == 0 ? bArr2.length : (bArr2.length + 16) - (bArr2.length % 16);
        int iRemaining = byteBufferAllocate.remaining();
        int i = iRemaining % 16 == 0 ? iRemaining : (iRemaining + 16) - (iRemaining % 16);
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(length2 + i + 16).order(ByteOrder.LITTLE_ENDIAN);
        byteBufferOrder.put(bArr2);
        byteBufferOrder.position(length2);
        byteBufferOrder.put(byteBufferAllocate);
        byteBufferOrder.position(length2 + i);
        byteBufferOrder.putLong(bArr2.length);
        byteBufferOrder.putLong(iRemaining);
        byte[] bArrZze = zzazk.zze(bArr4, byteBufferOrder.array());
        byteBufferAllocate.limit(byteBufferAllocate.limit() + 16);
        byteBufferAllocate.put(bArrZze);
        return byteBufferAllocate.array();
    }
}
