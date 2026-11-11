package com.google.android.gms.internal.ads;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;

/* JADX INFO: loaded from: classes.dex */
abstract class zzazn implements zzazi {
    static final int[] zzdoo = zza(ByteBuffer.wrap(new byte[]{101, 120, 112, 97, 110, 100, 32, 51, 50, 45, 98, 121, 116, 101, 32, 107}));
    final zzazh zzdop;
    private final int zzdoq;

    zzazn(byte[] bArr, int i) throws InvalidKeyException {
        if (bArr.length != 32) {
            throw new InvalidKeyException("The key length in bytes must be 32.");
        }
        this.zzdop = zzazh.zzm(bArr);
        this.zzdoq = i;
    }

    static int rotateLeft(int i, int i2) {
        return (i << i2) | (i >>> (-i2));
    }

    static int[] zza(ByteBuffer byteBuffer) {
        IntBuffer intBufferAsIntBuffer = byteBuffer.order(ByteOrder.LITTLE_ENDIAN).asIntBuffer();
        int[] iArr = new int[intBufferAsIntBuffer.remaining()];
        intBufferAsIntBuffer.get(iArr);
        return iArr;
    }

    final void zza(ByteBuffer byteBuffer, byte[] bArr) {
        if (byteBuffer.remaining() - zzaao() < bArr.length) {
            throw new IllegalArgumentException("Given ByteBuffer output is too small");
        }
        byte[] bArrZzbh = zzazl.zzbh(zzaao());
        byteBuffer.put(bArrZzbh);
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
        int iRemaining = byteBufferWrap.remaining();
        int i = (iRemaining / 64) + 1;
        for (int i2 = 0; i2 < i; i2++) {
            ByteBuffer byteBufferZzb = zzb(bArrZzbh, this.zzdoq + i2);
            if (i2 == i - 1) {
                zzayk.zza(byteBuffer, byteBufferWrap, byteBufferZzb, iRemaining % 64);
            } else {
                zzayk.zza(byteBuffer, byteBufferWrap, byteBufferZzb, 64);
            }
        }
    }

    abstract int zzaao();

    abstract ByteBuffer zzb(byte[] bArr, int i);

    @Override // com.google.android.gms.internal.ads.zzazi
    public final byte[] zzk(byte[] bArr) throws GeneralSecurityException {
        int length = bArr.length;
        zzaao();
        if (length > 2147483635) {
            throw new GeneralSecurityException("plaintext too long");
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(zzaao() + bArr.length);
        zza(byteBufferAllocate, bArr);
        return byteBufferAllocate.array();
    }
}
