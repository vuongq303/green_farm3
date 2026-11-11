package com.google.a.f.a;

/* JADX INFO: compiled from: Mode.java */
/* JADX INFO: loaded from: classes.dex */
public enum b {
    TERMINATOR(new int[]{0, 0, 0}, 0),
    NUMERIC(new int[]{10, 12, 14}, 1),
    ALPHANUMERIC(new int[]{9, 11, 13}, 2),
    STRUCTURED_APPEND(new int[]{0, 0, 0}, 3),
    BYTE(new int[]{8, 16, 16}, 4),
    ECI(new int[]{0, 0, 0}, 7),
    KANJI(new int[]{8, 10, 12}, 8),
    FNC1_FIRST_POSITION(new int[]{0, 0, 0}, 5),
    FNC1_SECOND_POSITION(new int[]{0, 0, 0}, 9),
    HANZI(new int[]{8, 10, 12}, 13);

    private final int ccR;
    private final int[] cdd;

    b(int[] iArr, int i) {
        this.cdd = iArr;
        this.ccR = i;
    }

    public int a(c cVar) {
        char c;
        int iAhn = cVar.ahn();
        if (iAhn <= 9) {
            c = 0;
        } else if (iAhn <= 26) {
            c = 1;
        } else {
            c = 2;
        }
        return this.cdd[c];
    }

    public int ahl() {
        return this.ccR;
    }
}
