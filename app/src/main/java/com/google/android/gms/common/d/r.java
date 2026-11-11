package com.google.android.gms.common.d;

/* JADX INFO: loaded from: classes.dex */
public class r {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int b(byte[] bArr, int i, int i2, int i3) {
        int i4;
        int i5 = i + (i2 & (-4));
        int i6 = i3;
        while (i < i5) {
            int i7 = ((bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | (bArr[i + 3] << 24)) * (-862048943);
            int i8 = (((i7 >>> 17) | (i7 << 15)) * 461845907) ^ i6;
            i6 = (-430675100) + (((i8 >>> 19) | (i8 << 13)) * 5);
            i += 4;
        }
        int i9 = 0;
        switch (i2 & 3) {
            case 1:
                int i10 = (i9 | (bArr[i5] & 255)) * (-862048943);
                i4 = (((i10 >>> 17) | (i10 << 15)) * 461845907) ^ i6;
                break;
            case 2:
                i9 |= (bArr[i5 + 1] & 255) << 8;
                int i11 = (i9 | (bArr[i5] & 255)) * (-862048943);
                i4 = (((i11 >>> 17) | (i11 << 15)) * 461845907) ^ i6;
                break;
            case 3:
                i9 = (bArr[i5 + 2] & 255) << 16;
                i9 |= (bArr[i5 + 1] & 255) << 8;
                int i12 = (i9 | (bArr[i5] & 255)) * (-862048943);
                i4 = (((i12 >>> 17) | (i12 << 15)) * 461845907) ^ i6;
                break;
            default:
                i4 = i6;
                break;
        }
        int i13 = i4 ^ i2;
        int i14 = (i13 ^ (i13 >>> 16)) * (-2048144789);
        int i15 = (i14 ^ (i14 >>> 13)) * (-1028477387);
        return i15 ^ (i15 >>> 16);
    }
}
