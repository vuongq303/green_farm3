package com.google.android.gms.internal.ads;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzgv extends zzgq {
    private MessageDigest zzaje;

    @Override // com.google.android.gms.internal.ads.zzgq
    public final byte[] zzx(String str) {
        byte[] bArrArray;
        byte[] bArr;
        String[] strArrSplit = str.split(" ");
        if (strArrSplit.length == 1) {
            int iZzz = zzgu.zzz(strArrSplit[0]);
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(4);
            byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
            byteBufferAllocate.putInt(iZzz);
            bArrArray = byteBufferAllocate.array();
        } else if (strArrSplit.length < 5) {
            byte[] bArr2 = new byte[strArrSplit.length << 1];
            for (int i = 0; i < strArrSplit.length; i++) {
                int iZzz2 = zzgu.zzz(strArrSplit[i]);
                int i2 = (iZzz2 >> 16) ^ (65535 & iZzz2);
                byte[] bArr3 = {(byte) i2, (byte) (i2 >> 8)};
                bArr2[i << 1] = bArr3[0];
                bArr2[(i << 1) + 1] = bArr3[1];
            }
            bArrArray = bArr2;
        } else {
            bArrArray = new byte[strArrSplit.length];
            for (int i3 = 0; i3 < strArrSplit.length; i3++) {
                int iZzz3 = zzgu.zzz(strArrSplit[i3]);
                bArrArray[i3] = (byte) ((iZzz3 >> 24) ^ (((iZzz3 & 255) ^ ((iZzz3 >> 8) & 255)) ^ ((iZzz3 >> 16) & 255)));
            }
        }
        this.zzaje = zzhg();
        synchronized (this.mLock) {
            if (this.zzaje == null) {
                bArr = new byte[0];
            } else {
                this.zzaje.reset();
                this.zzaje.update(bArrArray);
                byte[] bArrDigest = this.zzaje.digest();
                bArr = new byte[bArrDigest.length > 4 ? 4 : bArrDigest.length];
                System.arraycopy(bArrDigest, 0, bArr, 0, bArr.length);
            }
        }
        return bArr;
    }
}
