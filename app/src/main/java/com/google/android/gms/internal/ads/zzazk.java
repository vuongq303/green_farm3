package com.google.android.gms.internal.ads;

import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
final class zzazk {
    private static long zza(byte[] bArr, int i, int i2) {
        return (zzd(bArr, i) >> i2) & 67108863;
    }

    private static void zza(byte[] bArr, long j, int i) {
        int i2 = 0;
        while (i2 < 4) {
            bArr[i + i2] = (byte) (255 & j);
            i2++;
            j >>= 8;
        }
    }

    private static long zzd(byte[] bArr, int i) {
        return ((long) ((bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 3] & 255) << 24))) & 4294967295L;
    }

    static byte[] zze(byte[] bArr, byte[] bArr2) {
        if (bArr.length != 32) {
            throw new IllegalArgumentException("The key length in bytes must be 32.");
        }
        long j = 0;
        long j2 = 0;
        long j3 = 0;
        long j4 = 0;
        long j5 = 0;
        long jZza = 67108863 & zza(bArr, 0, 0);
        long jZza2 = 67108611 & zza(bArr, 3, 2);
        long jZza3 = 67092735 & zza(bArr, 6, 4);
        long jZza4 = 66076671 & zza(bArr, 9, 6);
        long jZza5 = 1048575 & zza(bArr, 12, 8);
        long j6 = jZza2 * 5;
        long j7 = jZza3 * 5;
        long j8 = jZza4 * 5;
        long j9 = jZza5 * 5;
        byte[] bArr3 = new byte[17];
        for (int i = 0; i < bArr2.length; i += 16) {
            int iMin = Math.min(16, bArr2.length - i);
            System.arraycopy(bArr2, i, bArr3, 0, iMin);
            bArr3[iMin] = 1;
            if (iMin != 16) {
                Arrays.fill(bArr3, iMin + 1, 17, (byte) 0);
            }
            long jZza6 = j + zza(bArr3, 0, 0);
            long jZza7 = j2 + zza(bArr3, 3, 2);
            long jZza8 = j3 + zza(bArr3, 6, 4);
            long jZza9 = j4 + zza(bArr3, 9, 6);
            long jZza10 = j5 + (zza(bArr3, 12, 8) | ((long) (bArr3[16] << 24)));
            long j10 = (jZza6 * jZza) + (jZza7 * j9) + (jZza8 * j8) + (jZza9 * j7) + (jZza10 * j6);
            long j11 = (jZza6 * jZza2) + (jZza7 * jZza) + (jZza8 * j9) + (jZza9 * j8) + (jZza10 * j7);
            long j12 = (jZza6 * jZza3) + (jZza7 * jZza2) + (jZza8 * jZza) + (jZza9 * j9) + (jZza10 * j8);
            long j13 = (jZza6 * jZza4) + (jZza7 * jZza3) + (jZza8 * jZza2) + (jZza9 * jZza) + (jZza10 * j9);
            long j14 = jZza8 * jZza3;
            long j15 = jZza9 * jZza2;
            long j16 = jZza10 * jZza;
            long j17 = j16 + j15 + j14 + (jZza7 * jZza4) + (jZza6 * jZza5);
            long j18 = j10 & 67108863;
            long j19 = (j10 >> 26) + j11;
            long j20 = j19 & 67108863;
            long j21 = j12 + (j19 >> 26);
            j3 = 67108863 & j21;
            long j22 = j13 + (j21 >> 26);
            long j23 = j22 >> 26;
            j4 = j22 & 67108863;
            long j24 = j17 + j23;
            long j25 = j24 >> 26;
            j5 = j24 & 67108863;
            long j26 = j18 + (j25 * 5);
            j = 67108863 & j26;
            j2 = j20 + (j26 >> 26);
        }
        long j27 = (j2 >> 26) + j3;
        long j28 = j27 >> 26;
        long j29 = j27 & 67108863;
        long j30 = j4 + j28;
        long j31 = j30 >> 26;
        long j32 = j30 & 67108863;
        long j33 = j5 + j31;
        long j34 = j33 >> 26;
        long j35 = j33 & 67108863;
        long j36 = (j34 * 5) + j;
        long j37 = j36 >> 26;
        long j38 = j36 & 67108863;
        long j39 = (j2 & 67108863) + j37;
        long j40 = 5 + j38;
        long j41 = (j40 >> 26) + j39;
        long j42 = (j41 >> 26) + j29;
        long j43 = (j42 >> 26) + j32;
        long j44 = ((j43 >> 26) + j35) - 67108864;
        long j45 = j44 >> 63;
        long j46 = j38 & j45;
        long j47 = j39 & j45;
        long j48 = j29 & j45;
        long j49 = j32 & j45;
        long j50 = j35 & j45;
        long j51 = j45 ^ (-1);
        long j52 = j47 | (j41 & 67108863 & j51);
        long j53 = j48 | (j42 & 67108863 & j51);
        long j54 = j49 | (j43 & 67108863 & j51);
        long j55 = (j46 | (j40 & 67108863 & j51) | (j52 << 26)) & 4294967295L;
        long j56 = ((j52 >> 6) | (j53 << 20)) & 4294967295L;
        long j57 = ((j53 >> 12) | (j54 << 14)) & 4294967295L;
        long j58 = (((j50 | (j44 & j51)) << 8) | (j54 >> 18)) & 4294967295L;
        long jZzd = zzd(bArr, 16) + j55;
        long j59 = 4294967295L & jZzd;
        long jZzd2 = (jZzd >> 32) + j56 + zzd(bArr, 20);
        long j60 = 4294967295L & jZzd2;
        long jZzd3 = j57 + zzd(bArr, 24) + (jZzd2 >> 32);
        long j61 = 4294967295L & jZzd3;
        long jZzd4 = ((jZzd3 >> 32) + j58 + zzd(bArr, 28)) & 4294967295L;
        byte[] bArr4 = new byte[16];
        zza(bArr4, j59, 0);
        zza(bArr4, j60, 4);
        zza(bArr4, j61, 8);
        zza(bArr4, jZzd4, 12);
        return bArr4;
    }
}
