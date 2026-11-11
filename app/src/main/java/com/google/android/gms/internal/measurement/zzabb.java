package com.google.android.gms.internal.measurement;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;

/* JADX INFO: loaded from: classes.dex */
public final class zzabb {
    private final ByteBuffer zzbzg;

    private zzabb(ByteBuffer byteBuffer) {
        this.zzbzg = byteBuffer;
        this.zzbzg.order(ByteOrder.LITTLE_ENDIAN);
    }

    private zzabb(byte[] bArr, int i, int i2) {
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

    private final void zzao(long j) throws zzabc {
        while (((-128) & j) != 0) {
            zzar((((int) j) & 127) | 128);
            j >>>= 7;
        }
        zzar((int) j);
    }

    public static int zzap(long j) {
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

    public static int zzaq(int i) {
        if (i >= 0) {
            return zzau(i);
        }
        return 10;
    }

    private final void zzar(int i) throws zzabc {
        byte b = (byte) i;
        if (!this.zzbzg.hasRemaining()) {
            throw new zzabc(this.zzbzg.position(), this.zzbzg.limit());
        }
        this.zzbzg.put(b);
    }

    public static int zzas(int i) {
        return zzau(i << 3);
    }

    public static int zzau(int i) {
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

    public static int zzb(int i, zzabj zzabjVar) {
        int iZzas = zzas(i);
        int iZzwg = zzabjVar.zzwg();
        return iZzas + iZzwg + zzau(iZzwg);
    }

    public static zzabb zzb(byte[] bArr, int i, int i2) {
        return new zzabb(bArr, 0, i2);
    }

    public static int zzc(int i, long j) {
        return zzas(i) + zzap(j);
    }

    public static int zzd(int i, String str) {
        return zzas(i) + zzfp(str);
    }

    public static int zzf(int i, int i2) {
        return zzas(i) + zzaq(i2);
    }

    public static int zzfp(String str) {
        int iZza = zza(str);
        return iZza + zzau(iZza);
    }

    public static zzabb zzk(byte[] bArr) {
        return zzb(bArr, 0, bArr.length);
    }

    public final void zza(int i, double d) throws zzabc {
        zzg(i, 1);
        long jDoubleToLongBits = Double.doubleToLongBits(d);
        if (this.zzbzg.remaining() < 8) {
            throw new zzabc(this.zzbzg.position(), this.zzbzg.limit());
        }
        this.zzbzg.putLong(jDoubleToLongBits);
    }

    public final void zza(int i, float f) throws zzabc {
        zzg(i, 5);
        int iFloatToIntBits = Float.floatToIntBits(f);
        if (this.zzbzg.remaining() < 4) {
            throw new zzabc(this.zzbzg.position(), this.zzbzg.limit());
        }
        this.zzbzg.putInt(iFloatToIntBits);
    }

    public final void zza(int i, long j) throws zzabc {
        zzg(i, 0);
        zzao(j);
    }

    public final void zza(int i, zzabj zzabjVar) throws zzabc {
        zzg(i, 2);
        zzb(zzabjVar);
    }

    public final void zza(int i, boolean z) throws zzabc {
        zzg(i, 0);
        byte b = (byte) (z ? 1 : 0);
        if (!this.zzbzg.hasRemaining()) {
            throw new zzabc(this.zzbzg.position(), this.zzbzg.limit());
        }
        this.zzbzg.put(b);
    }

    public final void zzat(int i) throws zzabc {
        while ((i & (-128)) != 0) {
            zzar((i & 127) | 128);
            i >>>= 7;
        }
        zzar(i);
    }

    public final void zzb(int i, long j) throws zzabc {
        zzg(i, 0);
        zzao(j);
    }

    public final void zzb(zzabj zzabjVar) throws zzabc {
        zzat(zzabjVar.zzwf());
        zzabjVar.zza(this);
    }

    public final void zzc(int i, String str) throws zzabc {
        zzg(i, 2);
        try {
            int iZzau = zzau(str.length());
            if (iZzau != zzau(str.length() * 3)) {
                zzat(zza(str));
                zza(str, this.zzbzg);
                return;
            }
            int iPosition = this.zzbzg.position();
            if (this.zzbzg.remaining() < iZzau) {
                throw new zzabc(iZzau + iPosition, this.zzbzg.limit());
            }
            this.zzbzg.position(iPosition + iZzau);
            zza(str, this.zzbzg);
            int iPosition2 = this.zzbzg.position();
            this.zzbzg.position(iPosition);
            zzat((iPosition2 - iPosition) - iZzau);
            this.zzbzg.position(iPosition2);
        } catch (BufferOverflowException e) {
            zzabc zzabcVar = new zzabc(this.zzbzg.position(), this.zzbzg.limit());
            zzabcVar.initCause(e);
            throw zzabcVar;
        }
    }

    public final void zze(int i, int i2) throws zzabc {
        zzg(i, 0);
        if (i2 >= 0) {
            zzat(i2);
        } else {
            zzao(i2);
        }
    }

    public final void zzg(int i, int i2) throws zzabc {
        zzat((i << 3) | i2);
    }

    public final void zzl(byte[] bArr) throws zzabc {
        int length = bArr.length;
        if (this.zzbzg.remaining() < length) {
            throw new zzabc(this.zzbzg.position(), this.zzbzg.limit());
        }
        this.zzbzg.put(bArr, 0, length);
    }

    public final void zzvy() {
        if (this.zzbzg.remaining() != 0) {
            throw new IllegalStateException(String.format("Did not write as much data as expected, %s bytes remaining.", Integer.valueOf(this.zzbzg.remaining())));
        }
    }
}
