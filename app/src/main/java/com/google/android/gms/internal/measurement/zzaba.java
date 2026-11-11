package com.google.android.gms.internal.measurement;

/* JADX INFO: loaded from: classes.dex */
public final class zzaba {
    private final byte[] buffer;
    private int zzbtu;
    private final int zzbza;
    private final int zzbzb;
    private int zzbzc;
    private int zzbzd;
    private int zzbze;
    private int zzbzf;
    private int zzbtw = Integer.MAX_VALUE;
    private int zzbtp = 64;
    private int zzbtq = 67108864;

    private zzaba(byte[] bArr, int i, int i2) {
        this.buffer = bArr;
        this.zzbza = i;
        int i3 = i + i2;
        this.zzbzc = i3;
        this.zzbzb = i3;
        this.zzbzd = i;
    }

    public static zzaba zza(byte[] bArr, int i, int i2) {
        return new zzaba(bArr, 0, i2);
    }

    private final void zzap(int i) throws zzabi {
        if (i < 0) {
            throw zzabi.zzwc();
        }
        if (this.zzbzd + i > this.zzbtw) {
            zzap(this.zzbtw - this.zzbzd);
            throw zzabi.zzwb();
        }
        if (i > this.zzbzc - this.zzbzd) {
            throw zzabi.zzwb();
        }
        this.zzbzd += i;
    }

    public static zzaba zzj(byte[] bArr) {
        return zza(bArr, 0, bArr.length);
    }

    private final void zzts() {
        this.zzbzc += this.zzbtu;
        int i = this.zzbzc;
        if (i <= this.zzbtw) {
            this.zzbtu = 0;
        } else {
            this.zzbtu = i - this.zzbtw;
            this.zzbzc -= this.zzbtu;
        }
    }

    private final byte zzvx() throws zzabi {
        if (this.zzbzd == this.zzbzc) {
            throw zzabi.zzwb();
        }
        byte[] bArr = this.buffer;
        int i = this.zzbzd;
        this.zzbzd = i + 1;
        return bArr[i];
    }

    public final int getPosition() {
        return this.zzbzd - this.zzbza;
    }

    public final String readString() throws zzabi {
        int iZzvs = zzvs();
        if (iZzvs < 0) {
            throw zzabi.zzwc();
        }
        if (iZzvs > this.zzbzc - this.zzbzd) {
            throw zzabi.zzwb();
        }
        String str = new String(this.buffer, this.zzbzd, iZzvs, zzabh.UTF_8);
        this.zzbzd = iZzvs + this.zzbzd;
        return str;
    }

    public final void zza(zzabj zzabjVar) throws zzabi {
        int iZzvs = zzvs();
        if (this.zzbzf >= this.zzbtp) {
            throw zzabi.zzwe();
        }
        int iZzah = zzah(iZzvs);
        this.zzbzf++;
        zzabjVar.zzb(this);
        zzal(0);
        this.zzbzf--;
        zzan(iZzah);
    }

    public final void zza(zzabj zzabjVar, int i) throws zzabi {
        if (this.zzbzf >= this.zzbtp) {
            throw zzabi.zzwe();
        }
        this.zzbzf++;
        zzabjVar.zzb(this);
        zzal((i << 3) | 4);
        this.zzbzf--;
    }

    public final int zzah(int i) throws zzabi {
        if (i < 0) {
            throw zzabi.zzwc();
        }
        int i2 = this.zzbzd + i;
        int i3 = this.zzbtw;
        if (i2 > i3) {
            throw zzabi.zzwb();
        }
        this.zzbtw = i2;
        zzts();
        return i3;
    }

    public final void zzal(int i) throws zzabi {
        if (this.zzbze != i) {
            throw new zzabi("Protocol message end-group tag did not match expected tag.");
        }
    }

    public final boolean zzam(int i) throws zzabi {
        int iZzvo;
        switch (i & 7) {
            case 0:
                zzvs();
                return true;
            case 1:
                zzvv();
                return true;
            case 2:
                zzap(zzvs());
                return true;
            case 3:
                break;
            case 4:
                return false;
            case 5:
                zzvu();
                return true;
            default:
                throw new zzabi("Protocol message tag had invalid wire type.");
        }
        do {
            iZzvo = zzvo();
            if (iZzvo != 0) {
            }
            zzal(((i >>> 3) << 3) | 4);
            return true;
        } while (zzam(iZzvo));
        zzal(((i >>> 3) << 3) | 4);
        return true;
    }

    public final void zzan(int i) {
        this.zzbtw = i;
        zzts();
    }

    public final void zzao(int i) {
        zzd(i, this.zzbze);
    }

    public final byte[] zzc(int i, int i2) {
        if (i2 == 0) {
            return zzabm.zzcae;
        }
        byte[] bArr = new byte[i2];
        System.arraycopy(this.buffer, this.zzbza + i, bArr, 0, i2);
        return bArr;
    }

    final void zzd(int i, int i2) {
        if (i > this.zzbzd - this.zzbza) {
            throw new IllegalArgumentException(new StringBuilder(50).append("Position ").append(i).append(" is beyond current ").append(this.zzbzd - this.zzbza).toString());
        }
        if (i < 0) {
            throw new IllegalArgumentException(new StringBuilder(24).append("Bad position ").append(i).toString());
        }
        this.zzbzd = this.zzbza + i;
        this.zzbze = i2;
    }

    public final int zzvo() throws zzabi {
        if (this.zzbzd == this.zzbzc) {
            this.zzbze = 0;
            return 0;
        }
        this.zzbze = zzvs();
        if (this.zzbze == 0) {
            throw new zzabi("Protocol message contained an invalid tag (zero).");
        }
        return this.zzbze;
    }

    public final long zzvp() {
        return zzvt();
    }

    public final int zzvq() {
        return zzvs();
    }

    public final boolean zzvr() {
        return zzvs() != 0;
    }

    public final int zzvs() throws zzabi {
        byte bZzvx = zzvx();
        if (bZzvx >= 0) {
            return bZzvx;
        }
        int i = bZzvx & 127;
        byte bZzvx2 = zzvx();
        if (bZzvx2 >= 0) {
            return i | (bZzvx2 << 7);
        }
        int i2 = i | ((bZzvx2 & 127) << 7);
        byte bZzvx3 = zzvx();
        if (bZzvx3 >= 0) {
            return i2 | (bZzvx3 << 14);
        }
        int i3 = i2 | ((bZzvx3 & 127) << 14);
        byte bZzvx4 = zzvx();
        if (bZzvx4 >= 0) {
            return i3 | (bZzvx4 << 21);
        }
        int i4 = i3 | ((bZzvx4 & 127) << 21);
        byte bZzvx5 = zzvx();
        int i5 = i4 | (bZzvx5 << 28);
        if (bZzvx5 >= 0) {
            return i5;
        }
        for (int i6 = 0; i6 < 5; i6++) {
            if (zzvx() >= 0) {
                return i5;
            }
        }
        throw zzabi.zzwd();
    }

    public final long zzvt() throws zzabi {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte bZzvx = zzvx();
            j |= ((long) (bZzvx & 127)) << i;
            if ((bZzvx & 128) == 0) {
                return j;
            }
        }
        throw zzabi.zzwd();
    }

    public final int zzvu() throws zzabi {
        return (zzvx() & 255) | ((zzvx() & 255) << 8) | ((zzvx() & 255) << 16) | ((zzvx() & 255) << 24);
    }

    public final long zzvv() throws zzabi {
        byte bZzvx = zzvx();
        return ((((long) zzvx()) & 255) << 8) | (((long) bZzvx) & 255) | ((((long) zzvx()) & 255) << 16) | ((((long) zzvx()) & 255) << 24) | ((((long) zzvx()) & 255) << 32) | ((((long) zzvx()) & 255) << 40) | ((((long) zzvx()) & 255) << 48) | ((((long) zzvx()) & 255) << 56);
    }

    public final int zzvw() {
        if (this.zzbtw == Integer.MAX_VALUE) {
            return -1;
        }
        return this.zzbtw - this.zzbzd;
    }
}
