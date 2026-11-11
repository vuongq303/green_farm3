package com.google.a.c.a;

import com.facebook.internal.FacebookRequestErrorClassification;

/* JADX INFO: compiled from: SymbolInfo.java */
/* JADX INFO: loaded from: classes.dex */
public class k {
    static final k[] cbA;
    private static k[] cbB;
    private final boolean cbC;
    private final int cbD;
    private final int cbE;
    public final int cbF;
    public final int cbG;
    private final int cbH;
    private final int cbI;
    private final int cbJ;

    static {
        k[] kVarArr = {new k(false, 3, 5, 8, 8, 1), new k(false, 5, 7, 10, 10, 1), new k(true, 5, 7, 16, 6, 1), new k(false, 8, 10, 12, 12, 1), new k(true, 10, 11, 14, 6, 2), new k(false, 12, 12, 14, 14, 1), new k(true, 16, 14, 24, 10, 1), new k(false, 18, 14, 16, 16, 1), new k(false, 22, 18, 18, 18, 1), new k(true, 22, 18, 16, 10, 2), new k(false, 30, 20, 20, 20, 1), new k(true, 32, 24, 16, 14, 2), new k(false, 36, 24, 22, 22, 1), new k(false, 44, 28, 24, 24, 1), new k(true, 49, 28, 22, 14, 2), new k(false, 62, 36, 14, 14, 4), new k(false, 86, 42, 16, 16, 4), new k(false, 114, 48, 18, 18, 4), new k(false, 144, 56, 20, 20, 4), new k(false, 174, 68, 22, 22, 4), new k(false, 204, 84, 24, 24, 4, FacebookRequestErrorClassification.EC_INVALID_SESSION, 42), new k(false, 280, 112, 14, 14, 16, 140, 56), new k(false, 368, 144, 16, 16, 16, 92, 36), new k(false, 456, 192, 18, 18, 16, 114, 48), new k(false, 576, 224, 20, 20, 16, 144, 56), new k(false, 696, 272, 22, 22, 16, 174, 68), new k(false, 816, 336, 24, 24, 16, 136, 56), new k(false, 1050, 408, 18, 18, 36, 175, 68), new k(false, 1304, 496, 20, 20, 36, 163, 62), new d()};
        cbA = kVarArr;
        cbB = kVarArr;
    }

    public k(boolean z, int i, int i2, int i3, int i4, int i5) {
        this(z, i, i2, i3, i4, i5, i, i2);
    }

    k(boolean z, int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        this.cbC = z;
        this.cbD = i;
        this.cbE = i2;
        this.cbF = i3;
        this.cbG = i4;
        this.cbH = i5;
        this.cbI = i6;
        this.cbJ = i7;
    }

    public static k a(int i, l lVar, com.google.a.b bVar, com.google.a.b bVar2, boolean z) {
        for (k kVar : cbB) {
            if ((lVar != l.FORCE_SQUARE || !kVar.cbC) && ((lVar != l.FORCE_RECTANGLE || kVar.cbC) && ((bVar == null || (kVar.aha() >= bVar.getWidth() && kVar.ahb() >= bVar.getHeight())) && ((bVar2 == null || (kVar.aha() <= bVar2.getWidth() && kVar.ahb() <= bVar2.getHeight())) && i <= kVar.cbD)))) {
                return kVar;
            }
        }
        if (z) {
            throw new IllegalArgumentException("Can't find a symbol arrangement that matches the message. Data codewords: " + i);
        }
        return null;
    }

    private int agW() {
        switch (this.cbH) {
            case 1:
                return 1;
            case 2:
            case 4:
                return 2;
            case 16:
                return 4;
            case 36:
                return 6;
            default:
                throw new IllegalStateException("Cannot handle this number of data regions");
        }
    }

    private int agX() {
        switch (this.cbH) {
            case 1:
            case 2:
                return 1;
            case 4:
                return 2;
            case 16:
                return 4;
            case 36:
                return 6;
            default:
                throw new IllegalStateException("Cannot handle this number of data regions");
        }
    }

    public final int agY() {
        return agW() * this.cbF;
    }

    public final int agZ() {
        return agX() * this.cbG;
    }

    public final int aha() {
        return agY() + (agW() << 1);
    }

    public final int ahb() {
        return agZ() + (agX() << 1);
    }

    public int agJ() {
        return this.cbD / this.cbI;
    }

    public final int ahc() {
        return this.cbD;
    }

    public final int ahd() {
        return this.cbE;
    }

    public int oJ(int i) {
        return this.cbI;
    }

    public final int oR(int i) {
        return this.cbJ;
    }

    public final String toString() {
        return (this.cbC ? "Rectangular Symbol:" : "Square Symbol:") + " data region " + this.cbF + 'x' + this.cbG + ", symbol size " + aha() + 'x' + ahb() + ", symbol data size " + agY() + 'x' + agZ() + ", codewords " + this.cbD + '+' + this.cbE;
    }
}
