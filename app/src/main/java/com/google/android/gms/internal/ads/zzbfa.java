package com.google.android.gms.internal.ads;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;

/* JADX INFO: loaded from: classes.dex */
public final class zzbfa {
    private final ByteBuffer zzebj;

    private zzbfa(ByteBuffer byteBuffer) {
        this.zzebj = byteBuffer;
        this.zzebj.order(ByteOrder.LITTLE_ENDIAN);
    }

    private zzbfa(byte[] bArr, int i, int i2) {
        this(ByteBuffer.wrap(bArr, i, i2));
    }

    private static int zza(CharSequence charSequence) {
        int i;
        int i2 = 0;
        int length = charSequence.length();
        int i3 = 0;
        while (i3 < length && charSequence.charAt(i3) < 128) {
            i3++;
        }
        int i4 = length;
        while (true) {
            if (i3 < length) {
                char cCharAt = charSequence.charAt(i3);
                if (cCharAt >= 2048) {
                    int length2 = charSequence.length();
                    while (i3 < length2) {
                        char cCharAt2 = charSequence.charAt(i3);
                        if (cCharAt2 < 2048) {
                            i2 += (127 - cCharAt2) >>> 31;
                        } else {
                            i2 += 2;
                            if (55296 <= cCharAt2 && cCharAt2 <= 57343) {
                                if (Character.codePointAt(charSequence, i3) < 65536) {
                                    throw new IllegalArgumentException(new StringBuilder(39).append("Unpaired surrogate at index ").append(i3).toString());
                                }
                                i3++;
                            }
                        }
                        i3++;
                    }
                    i = i4 + i2;
                    break;
                }
                i4 += (127 - cCharAt) >>> 31;
                i3++;
            } else {
                i = i4;
                break;
            }
        }
        if (i >= length) {
            return i;
        }
        throw new IllegalArgumentException(new StringBuilder(54).append("UTF-8 length does not fit in int: ").append(((long) i) + 4294967296L).toString());
    }

    private static void zza(CharSequence charSequence, ByteBuffer byteBuffer) {
        int i;
        int i2;
        int i3 = 0;
        if (byteBuffer.isReadOnly()) {
            throw new ReadOnlyBufferException();
        }
        if (!byteBuffer.hasArray()) {
            int length = charSequence.length();
            while (i3 < length) {
                char cCharAt = charSequence.charAt(i3);
                if (cCharAt < 128) {
                    byteBuffer.put((byte) cCharAt);
                } else if (cCharAt < 2048) {
                    byteBuffer.put((byte) ((cCharAt >>> 6) | 960));
                    byteBuffer.put((byte) ((cCharAt & '?') | 128));
                } else {
                    if (cCharAt >= 55296 && 57343 >= cCharAt) {
                        if (i3 + 1 != charSequence.length()) {
                            i3++;
                            char cCharAt2 = charSequence.charAt(i3);
                            if (Character.isSurrogatePair(cCharAt, cCharAt2)) {
                                int codePoint = Character.toCodePoint(cCharAt, cCharAt2);
                                byteBuffer.put((byte) ((codePoint >>> 18) | 240));
                                byteBuffer.put((byte) (((codePoint >>> 12) & 63) | 128));
                                byteBuffer.put((byte) (((codePoint >>> 6) & 63) | 128));
                                byteBuffer.put((byte) ((codePoint & 63) | 128));
                            }
                        }
                        throw new IllegalArgumentException(new StringBuilder(39).append("Unpaired surrogate at index ").append(i3 - 1).toString());
                    }
                    byteBuffer.put((byte) ((cCharAt >>> '\f') | 480));
                    byteBuffer.put((byte) (((cCharAt >>> 6) & 63) | 128));
                    byteBuffer.put((byte) ((cCharAt & '?') | 128));
                }
                i3++;
            }
            return;
        }
        try {
            byte[] bArrArray = byteBuffer.array();
            int iArrayOffset = byteBuffer.arrayOffset() + byteBuffer.position();
            int iRemaining = byteBuffer.remaining();
            int length2 = charSequence.length();
            int i4 = iArrayOffset + iRemaining;
            while (i3 < length2 && i3 + iArrayOffset < i4) {
                char cCharAt3 = charSequence.charAt(i3);
                if (cCharAt3 >= 128) {
                    break;
                }
                bArrArray[iArrayOffset + i3] = (byte) cCharAt3;
                i3++;
            }
            if (i3 == length2) {
                i = iArrayOffset + length2;
            } else {
                int i5 = iArrayOffset + i3;
                while (i3 < length2) {
                    char cCharAt4 = charSequence.charAt(i3);
                    if (cCharAt4 < 128 && i5 < i4) {
                        i2 = i5 + 1;
                        bArrArray[i5] = (byte) cCharAt4;
                    } else if (cCharAt4 < 2048 && i5 <= i4 - 2) {
                        int i6 = i5 + 1;
                        bArrArray[i5] = (byte) ((cCharAt4 >>> 6) | 960);
                        i2 = i6 + 1;
                        bArrArray[i6] = (byte) ((cCharAt4 & '?') | 128);
                    } else {
                        if ((cCharAt4 >= 55296 && 57343 >= cCharAt4) || i5 > i4 - 3) {
                            if (i5 > i4 - 4) {
                                throw new ArrayIndexOutOfBoundsException(new StringBuilder(37).append("Failed writing ").append(cCharAt4).append(" at index ").append(i5).toString());
                            }
                            if (i3 + 1 != charSequence.length()) {
                                i3++;
                                char cCharAt5 = charSequence.charAt(i3);
                                if (Character.isSurrogatePair(cCharAt4, cCharAt5)) {
                                    int codePoint2 = Character.toCodePoint(cCharAt4, cCharAt5);
                                    int i7 = i5 + 1;
                                    bArrArray[i5] = (byte) ((codePoint2 >>> 18) | 240);
                                    int i8 = i7 + 1;
                                    bArrArray[i7] = (byte) (((codePoint2 >>> 12) & 63) | 128);
                                    int i9 = i8 + 1;
                                    bArrArray[i8] = (byte) (((codePoint2 >>> 6) & 63) | 128);
                                    i2 = i9 + 1;
                                    bArrArray[i9] = (byte) ((codePoint2 & 63) | 128);
                                }
                            }
                            throw new IllegalArgumentException(new StringBuilder(39).append("Unpaired surrogate at index ").append(i3 - 1).toString());
                        }
                        int i10 = i5 + 1;
                        bArrArray[i5] = (byte) ((cCharAt4 >>> '\f') | 480);
                        int i11 = i10 + 1;
                        bArrArray[i10] = (byte) (((cCharAt4 >>> 6) & 63) | 128);
                        i2 = i11 + 1;
                        bArrArray[i11] = (byte) ((cCharAt4 & '?') | 128);
                    }
                    i3++;
                    i5 = i2;
                }
                i = i5;
            }
            byteBuffer.position(i - byteBuffer.arrayOffset());
        } catch (ArrayIndexOutOfBoundsException e) {
            BufferOverflowException bufferOverflowException = new BufferOverflowException();
            bufferOverflowException.initCause(e);
            throw bufferOverflowException;
        }
    }

    public static int zzb(int i, zzbfi zzbfiVar) {
        int iZzcd = zzcd(i);
        int iZzacw = zzbfiVar.zzacw();
        return iZzcd + iZzacw + zzcl(iZzacw);
    }

    public static int zzb(int i, byte[] bArr) {
        return zzcd(i) + zzv(bArr);
    }

    public static int zzcd(int i) {
        return zzcl(i << 3);
    }

    public static int zzce(int i) {
        if (i >= 0) {
            return zzcl(i);
        }
        return 10;
    }

    public static int zzcl(int i) {
        if ((i & (-128)) == 0) {
            return 1;
        }
        if ((i & (-16384)) == 0) {
            return 2;
        }
        if (((-2097152) & i) == 0) {
            return 3;
        }
        return ((-268435456) & i) == 0 ? 4 : 5;
    }

    public static int zzd(int i, long j) {
        return zzcd(i) + zzy(j);
    }

    private final void zzdd(int i) throws zzbfb {
        byte b = (byte) i;
        if (!this.zzebj.hasRemaining()) {
            throw new zzbfb(this.zzebj.position(), this.zzebj.limit());
        }
        this.zzebj.put(b);
    }

    public static int zze(int i, long j) {
        return zzcd(i) + zzy(j);
    }

    public static int zzeo(String str) {
        int iZza = zza(str);
        return iZza + zzcl(iZza);
    }

    public static int zzg(int i, String str) {
        return zzcd(i) + zzeo(str);
    }

    public static zzbfa zzj(byte[] bArr, int i, int i2) {
        return new zzbfa(bArr, 0, i2);
    }

    public static int zzq(int i, int i2) {
        return zzcd(i) + zzce(i2);
    }

    public static zzbfa zzu(byte[] bArr) {
        return zzj(bArr, 0, bArr.length);
    }

    public static int zzv(byte[] bArr) {
        return zzcl(bArr.length) + bArr.length;
    }

    private final void zzx(long j) throws zzbfb {
        while (((-128) & j) != 0) {
            zzdd((((int) j) & 127) | 128);
            j >>>= 7;
        }
        zzdd((int) j);
    }

    public static int zzy(long j) {
        if (((-128) & j) == 0) {
            return 1;
        }
        if (((-16384) & j) == 0) {
            return 2;
        }
        if (((-2097152) & j) == 0) {
            return 3;
        }
        if (((-268435456) & j) == 0) {
            return 4;
        }
        if (((-34359738368L) & j) == 0) {
            return 5;
        }
        if (((-4398046511104L) & j) == 0) {
            return 6;
        }
        if (((-562949953421312L) & j) == 0) {
            return 7;
        }
        if (((-72057594037927936L) & j) == 0) {
            return 8;
        }
        return (Long.MIN_VALUE & j) == 0 ? 9 : 10;
    }

    public final void zza(int i, long j) throws zzbfb {
        zzl(i, 0);
        zzx(j);
    }

    public final void zza(int i, zzbfi zzbfiVar) throws zzbfb {
        zzl(i, 2);
        if (zzbfiVar.zzebt < 0) {
            zzbfiVar.zzacw();
        }
        zzde(zzbfiVar.zzebt);
        zzbfiVar.zza(this);
    }

    public final void zza(int i, byte[] bArr) throws zzbfb {
        zzl(i, 2);
        zzde(bArr.length);
        zzw(bArr);
    }

    public final void zzacl() {
        if (this.zzebj.remaining() != 0) {
            throw new IllegalStateException(String.format("Did not write as much data as expected, %s bytes remaining.", Integer.valueOf(this.zzebj.remaining())));
        }
    }

    public final void zzde(int i) throws zzbfb {
        while ((i & (-128)) != 0) {
            zzdd((i & 127) | 128);
            i >>>= 7;
        }
        zzdd(i);
    }

    public final void zzf(int i, String str) throws zzbfb {
        zzl(i, 2);
        try {
            int iZzcl = zzcl(str.length());
            if (iZzcl != zzcl(str.length() * 3)) {
                zzde(zza(str));
                zza(str, this.zzebj);
                return;
            }
            int iPosition = this.zzebj.position();
            if (this.zzebj.remaining() < iZzcl) {
                throw new zzbfb(iZzcl + iPosition, this.zzebj.limit());
            }
            this.zzebj.position(iPosition + iZzcl);
            zza(str, this.zzebj);
            int iPosition2 = this.zzebj.position();
            this.zzebj.position(iPosition);
            zzde((iPosition2 - iPosition) - iZzcl);
            this.zzebj.position(iPosition2);
        } catch (BufferOverflowException e) {
            zzbfb zzbfbVar = new zzbfb(this.zzebj.position(), this.zzebj.limit());
            zzbfbVar.initCause(e);
            throw zzbfbVar;
        }
    }

    public final void zzf(int i, boolean z) throws zzbfb {
        zzl(i, 0);
        byte b = (byte) (z ? 1 : 0);
        if (!this.zzebj.hasRemaining()) {
            throw new zzbfb(this.zzebj.position(), this.zzebj.limit());
        }
        this.zzebj.put(b);
    }

    public final void zzi(int i, long j) throws zzbfb {
        zzl(i, 0);
        zzx(j);
    }

    public final void zzl(int i, int i2) throws zzbfb {
        zzde((i << 3) | i2);
    }

    public final void zzm(int i, int i2) throws zzbfb {
        zzl(i, 0);
        if (i2 >= 0) {
            zzde(i2);
        } else {
            zzx(i2);
        }
    }

    public final void zzw(byte[] bArr) throws zzbfb {
        int length = bArr.length;
        if (this.zzebj.remaining() < length) {
            throw new zzbfb(this.zzebj.position(), this.zzebj.limit());
        }
        this.zzebj.put(bArr, 0, length);
    }
}
