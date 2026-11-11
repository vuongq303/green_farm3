package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
final class zzbeq extends zzben {
    zzbeq() {
    }

    private static int zza(byte[] bArr, int i, long j, int i2) {
        switch (i2) {
            case 0:
                return zzbem.zzda(i);
            case 1:
                return zzbem.zzz(i, zzbek.zza(bArr, j));
            case 2:
                return zzbem.zzf(i, zzbek.zza(bArr, j), zzbek.zza(bArr, 1 + j));
            default:
                throw new AssertionError();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzben
    final int zzb(int i, byte[] bArr, int i2, int i3) {
        int i4;
        if ((i2 | i3 | (bArr.length - i3)) < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("Array length=%d, index=%d, limit=%d", Integer.valueOf(bArr.length), Integer.valueOf(i2), Integer.valueOf(i3)));
        }
        long j = i2;
        int i5 = (int) (((long) i3) - j);
        if (i5 >= 16) {
            i4 = 0;
            long j2 = j;
            while (true) {
                if (i4 >= i5) {
                    i4 = i5;
                    break;
                }
                long j3 = 1 + j2;
                if (zzbek.zza(bArr, j2) < 0) {
                    break;
                }
                i4++;
                j2 = j3;
            }
        } else {
            i4 = 0;
        }
        long j4 = ((long) i4) + j;
        int i6 = i5 - i4;
        while (true) {
            byte bZza = 0;
            long j5 = j4;
            while (i6 > 0) {
                long j6 = 1 + j5;
                bZza = zzbek.zza(bArr, j5);
                if (bZza < 0) {
                    j5 = j6;
                    break;
                }
                i6--;
                j5 = j6;
            }
            if (i6 == 0) {
                return 0;
            }
            int i7 = i6 - 1;
            if (bZza < -32) {
                if (i7 == 0) {
                    return bZza;
                }
                i6 = i7 - 1;
                if (bZza >= -62) {
                    j4 = 1 + j5;
                    if (zzbek.zza(bArr, j5) > -65) {
                    }
                }
                return -1;
            }
            if (bZza >= -16) {
                if (i7 < 3) {
                    return zza(bArr, bZza, j5, i7);
                }
                i6 = i7 - 3;
                long j7 = 1 + j5;
                byte bZza2 = zzbek.zza(bArr, j5);
                if (bZza2 <= -65 && (((bZza << 28) + (bZza2 + 112)) >> 30) == 0) {
                    long j8 = 1 + j7;
                    if (zzbek.zza(bArr, j7) <= -65) {
                        j4 = 1 + j8;
                        if (zzbek.zza(bArr, j8) > -65) {
                        }
                    }
                }
                return -1;
            }
            if (i7 < 2) {
                return zza(bArr, bZza, j5, i7);
            }
            i6 = i7 - 2;
            long j9 = j5 + 1;
            byte bZza3 = zzbek.zza(bArr, j5);
            if (bZza3 <= -65 && ((bZza != -32 || bZza3 >= -96) && (bZza != -19 || bZza3 < -96))) {
                j4 = 1 + j9;
                if (zzbek.zza(bArr, j9) > -65) {
                }
            }
            return -1;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzben
    final int zzb(CharSequence charSequence, byte[] bArr, int i, int i2) {
        long j;
        long j2 = i;
        long j3 = j2 + ((long) i2);
        int length = charSequence.length();
        if (length > i2 || bArr.length - i2 < i) {
            throw new ArrayIndexOutOfBoundsException(new StringBuilder(37).append("Failed writing ").append(charSequence.charAt(length - 1)).append(" at index ").append(i + i2).toString());
        }
        int i3 = 0;
        while (i3 < length) {
            char cCharAt = charSequence.charAt(i3);
            if (cCharAt >= 128) {
                break;
            }
            zzbek.zza(bArr, j2, (byte) cCharAt);
            i3++;
            j2 = 1 + j2;
        }
        if (i3 == length) {
            return (int) j2;
        }
        long j4 = j2;
        while (i3 < length) {
            char cCharAt2 = charSequence.charAt(i3);
            if (cCharAt2 < 128 && j4 < j3) {
                j = 1 + j4;
                zzbek.zza(bArr, j4, (byte) cCharAt2);
            } else if (cCharAt2 < 2048 && j4 <= j3 - 2) {
                long j5 = j4 + 1;
                zzbek.zza(bArr, j4, (byte) ((cCharAt2 >>> 6) | 960));
                j = 1 + j5;
                zzbek.zza(bArr, j5, (byte) ((cCharAt2 & '?') | 128));
            } else {
                if ((cCharAt2 >= 55296 && 57343 >= cCharAt2) || j4 > j3 - 3) {
                    if (j4 > j3 - 4) {
                        if (55296 > cCharAt2 || cCharAt2 > 57343 || (i3 + 1 != length && Character.isSurrogatePair(cCharAt2, charSequence.charAt(i3 + 1)))) {
                            throw new ArrayIndexOutOfBoundsException(new StringBuilder(46).append("Failed writing ").append(cCharAt2).append(" at index ").append(j4).toString());
                        }
                        throw new zzbep(i3, length);
                    }
                    if (i3 + 1 != length) {
                        i3++;
                        char cCharAt3 = charSequence.charAt(i3);
                        if (Character.isSurrogatePair(cCharAt2, cCharAt3)) {
                            int codePoint = Character.toCodePoint(cCharAt2, cCharAt3);
                            long j6 = 1 + j4;
                            zzbek.zza(bArr, j4, (byte) ((codePoint >>> 18) | 240));
                            long j7 = 1 + j6;
                            zzbek.zza(bArr, j6, (byte) (((codePoint >>> 12) & 63) | 128));
                            long j8 = j7 + 1;
                            zzbek.zza(bArr, j7, (byte) (((codePoint >>> 6) & 63) | 128));
                            j = 1 + j8;
                            zzbek.zza(bArr, j8, (byte) ((codePoint & 63) | 128));
                        }
                    }
                    throw new zzbep(i3 - 1, length);
                }
                long j9 = 1 + j4;
                zzbek.zza(bArr, j4, (byte) ((cCharAt2 >>> '\f') | 480));
                long j10 = 1 + j9;
                zzbek.zza(bArr, j9, (byte) (((cCharAt2 >>> 6) & 63) | 128));
                j = 1 + j10;
                zzbek.zza(bArr, j10, (byte) ((cCharAt2 & '?') | 128));
            }
            i3++;
            j4 = j;
        }
        return (int) j4;
    }
}
