package com.google.a.f.a;

import com.facebook.internal.FacebookRequestErrorClassification;

/* JADX INFO: compiled from: Version.java */
/* JADX INFO: loaded from: classes.dex */
public final class c {
    private static final int[] cdf = {31892, 34236, 39577, 42195, 48118, 51042, 55367, 58893, 63784, 68472, 70749, 76311, 79154, 84390, 87683, 92361, 96236, 102084, 102881, 110507, 110734, 117786, 119615, 126325, 127568, 133589, 136944, 141498, 145311, 150283, 152622, 158308, 161089, 167017};
    private static final c[] cdg = ahq();
    private final int cdh;
    private final int[] cdi;
    private final e[] cdj;
    private final int cdk;

    private c(int i, int[] iArr, e... eVarArr) {
        this.cdh = i;
        this.cdi = iArr;
        this.cdj = eVarArr;
        int iAhs = eVarArr[0].ahs();
        d[] dVarArrAhv = eVarArr[0].ahv();
        int length = dVarArrAhv.length;
        int i2 = 0;
        int iAhr = 0;
        while (i2 < length) {
            d dVar = dVarArrAhv[i2];
            i2++;
            iAhr = ((dVar.ahr() + iAhs) * dVar.getCount()) + iAhr;
        }
        this.cdk = iAhr;
    }

    public int ahn() {
        return this.cdh;
    }

    public int aho() {
        return this.cdk;
    }

    public int ahp() {
        return (this.cdh * 4) + 17;
    }

    public e a(a aVar) {
        return this.cdj[aVar.ordinal()];
    }

    public static c oU(int i) {
        if (i <= 0 || i > 40) {
            throw new IllegalArgumentException();
        }
        return cdg[i - 1];
    }

    public String toString() {
        return String.valueOf(this.cdh);
    }

    private static c[] ahq() {
        return new c[]{new c(1, new int[0], new e(7, new d(1, 19)), new e(10, new d(1, 16)), new e(13, new d(1, 13)), new e(17, new d(1, 9))), new c(2, new int[]{6, 18}, new e(10, new d(1, 34)), new e(16, new d(1, 28)), new e(22, new d(1, 22)), new e(28, new d(1, 16))), new c(3, new int[]{6, 22}, new e(15, new d(1, 55)), new e(26, new d(1, 44)), new e(18, new d(2, 17)), new e(22, new d(2, 13))), new c(4, new int[]{6, 26}, new e(20, new d(1, 80)), new e(18, new d(2, 32)), new e(26, new d(2, 24)), new e(16, new d(4, 9))), new c(5, new int[]{6, 30}, new e(26, new d(1, 108)), new e(24, new d(2, 43)), new e(18, new d(2, 15), new d(2, 16)), new e(22, new d(2, 11), new d(2, 12))), new c(6, new int[]{6, 34}, new e(18, new d(2, 68)), new e(16, new d(4, 27)), new e(24, new d(4, 19)), new e(28, new d(4, 15))), new c(7, new int[]{6, 22, 38}, new e(20, new d(2, 78)), new e(18, new d(4, 31)), new e(18, new d(2, 14), new d(4, 15)), new e(26, new d(4, 13), new d(1, 14))), new c(8, new int[]{6, 24, 42}, new e(24, new d(2, 97)), new e(22, new d(2, 38), new d(2, 39)), new e(22, new d(4, 18), new d(2, 19)), new e(26, new d(4, 14), new d(2, 15))), new c(9, new int[]{6, 26, 46}, new e(30, new d(2, 116)), new e(22, new d(3, 36), new d(2, 37)), new e(20, new d(4, 16), new d(4, 17)), new e(24, new d(4, 12), new d(4, 13))), new c(10, new int[]{6, 28, 50}, new e(18, new d(2, 68), new d(2, 69)), new e(26, new d(4, 43), new d(1, 44)), new e(24, new d(6, 19), new d(2, 20)), new e(28, new d(6, 15), new d(2, 16))), new c(11, new int[]{6, 30, 54}, new e(20, new d(4, 81)), new e(30, new d(1, 50), new d(4, 51)), new e(28, new d(4, 22), new d(4, 23)), new e(24, new d(3, 12), new d(8, 13))), new c(12, new int[]{6, 32, 58}, new e(24, new d(2, 92), new d(2, 93)), new e(22, new d(6, 36), new d(2, 37)), new e(26, new d(4, 20), new d(6, 21)), new e(28, new d(7, 14), new d(4, 15))), new c(13, new int[]{6, 34, 62}, new e(26, new d(4, 107)), new e(22, new d(8, 37), new d(1, 38)), new e(24, new d(8, 20), new d(4, 21)), new e(22, new d(12, 11), new d(4, 12))), new c(14, new int[]{6, 26, 46, 66}, new e(30, new d(3, 115), new d(1, 116)), new e(24, new d(4, 40), new d(5, 41)), new e(20, new d(11, 16), new d(5, 17)), new e(24, new d(11, 12), new d(5, 13))), new c(15, new int[]{6, 26, 48, 70}, new e(22, new d(5, 87), new d(1, 88)), new e(24, new d(5, 41), new d(5, 42)), new e(30, new d(5, 24), new d(7, 25)), new e(24, new d(11, 12), new d(7, 13))), new c(16, new int[]{6, 26, 50, 74}, new e(24, new d(5, 98), new d(1, 99)), new e(28, new d(7, 45), new d(3, 46)), new e(24, new d(15, 19), new d(2, 20)), new e(30, new d(3, 15), new d(13, 16))), new c(17, new int[]{6, 30, 54, 78}, new e(28, new d(1, 107), new d(5, 108)), new e(28, new d(10, 46), new d(1, 47)), new e(28, new d(1, 22), new d(15, 23)), new e(28, new d(2, 14), new d(17, 15))), new c(18, new int[]{6, 30, 56, 82}, new e(30, new d(5, 120), new d(1, 121)), new e(26, new d(9, 43), new d(4, 44)), new e(28, new d(17, 22), new d(1, 23)), new e(28, new d(2, 14), new d(19, 15))), new c(19, new int[]{6, 30, 58, 86}, new e(28, new d(3, 113), new d(4, 114)), new e(26, new d(3, 44), new d(11, 45)), new e(26, new d(17, 21), new d(4, 22)), new e(26, new d(9, 13), new d(16, 14))), new c(20, new int[]{6, 34, 62, 90}, new e(28, new d(3, 107), new d(5, 108)), new e(26, new d(3, 41), new d(13, 42)), new e(30, new d(15, 24), new d(5, 25)), new e(28, new d(15, 15), new d(10, 16))), new c(21, new int[]{6, 28, 50, 72, 94}, new e(28, new d(4, 116), new d(4, 117)), new e(26, new d(17, 42)), new e(28, new d(17, 22), new d(6, 23)), new e(30, new d(19, 16), new d(6, 17))), new c(22, new int[]{6, 26, 50, 74, 98}, new e(28, new d(2, 111), new d(7, 112)), new e(28, new d(17, 46)), new e(30, new d(7, 24), new d(16, 25)), new e(24, new d(34, 13))), new c(23, new int[]{6, 30, 54, 78, FacebookRequestErrorClassification.EC_INVALID_SESSION}, new e(30, new d(4, 121), new d(5, 122)), new e(28, new d(4, 47), new d(14, 48)), new e(30, new d(11, 24), new d(14, 25)), new e(30, new d(16, 15), new d(14, 16))), new c(24, new int[]{6, 28, 54, 80, 106}, new e(30, new d(6, 117), new d(4, 118)), new e(28, new d(6, 45), new d(14, 46)), new e(30, new d(11, 24), new d(16, 25)), new e(30, new d(30, 16), new d(2, 17))), new c(25, new int[]{6, 32, 58, 84, 110}, new e(26, new d(8, 106), new d(4, 107)), new e(28, new d(8, 47), new d(13, 48)), new e(30, new d(7, 24), new d(22, 25)), new e(30, new d(22, 15), new d(13, 16))), new c(26, new int[]{6, 30, 58, 86, 114}, new e(28, new d(10, 114), new d(2, 115)), new e(28, new d(19, 46), new d(4, 47)), new e(28, new d(28, 22), new d(6, 23)), new e(30, new d(33, 16), new d(4, 17))), new c(27, new int[]{6, 34, 62, 90, 118}, new e(30, new d(8, 122), new d(4, 123)), new e(28, new d(22, 45), new d(3, 46)), new e(30, new d(8, 23), new d(26, 24)), new e(30, new d(12, 15), new d(28, 16))), new c(28, new int[]{6, 26, 50, 74, 98, 122}, new e(30, new d(3, 117), new d(10, 118)), new e(28, new d(3, 45), new d(23, 46)), new e(30, new d(4, 24), new d(31, 25)), new e(30, new d(11, 15), new d(31, 16))), new c(29, new int[]{6, 30, 54, 78, FacebookRequestErrorClassification.EC_INVALID_SESSION, 126}, new e(30, new d(7, 116), new d(7, 117)), new e(28, new d(21, 45), new d(7, 46)), new e(30, new d(1, 23), new d(37, 24)), new e(30, new d(19, 15), new d(26, 16))), new c(30, new int[]{6, 26, 52, 78, 104, 130}, new e(30, new d(5, 115), new d(10, 116)), new e(28, new d(19, 47), new d(10, 48)), new e(30, new d(15, 24), new d(25, 25)), new e(30, new d(23, 15), new d(25, 16))), new c(31, new int[]{6, 30, 56, 82, 108, 134}, new e(30, new d(13, 115), new d(3, 116)), new e(28, new d(2, 46), new d(29, 47)), new e(30, new d(42, 24), new d(1, 25)), new e(30, new d(23, 15), new d(28, 16))), new c(32, new int[]{6, 34, 60, 86, 112, 138}, new e(30, new d(17, 115)), new e(28, new d(10, 46), new d(23, 47)), new e(30, new d(10, 24), new d(35, 25)), new e(30, new d(19, 15), new d(35, 16))), new c(33, new int[]{6, 30, 58, 86, 114, 142}, new e(30, new d(17, 115), new d(1, 116)), new e(28, new d(14, 46), new d(21, 47)), new e(30, new d(29, 24), new d(19, 25)), new e(30, new d(11, 15), new d(46, 16))), new c(34, new int[]{6, 34, 62, 90, 118, 146}, new e(30, new d(13, 115), new d(6, 116)), new e(28, new d(14, 46), new d(23, 47)), new e(30, new d(44, 24), new d(7, 25)), new e(30, new d(59, 16), new d(1, 17))), new c(35, new int[]{6, 30, 54, 78, FacebookRequestErrorClassification.EC_INVALID_SESSION, 126, 150}, new e(30, new d(12, 121), new d(7, 122)), new e(28, new d(12, 47), new d(26, 48)), new e(30, new d(39, 24), new d(14, 25)), new e(30, new d(22, 15), new d(41, 16))), new c(36, new int[]{6, 24, 50, 76, FacebookRequestErrorClassification.EC_INVALID_SESSION, 128, 154}, new e(30, new d(6, 121), new d(14, 122)), new e(28, new d(6, 47), new d(34, 48)), new e(30, new d(46, 24), new d(10, 25)), new e(30, new d(2, 15), new d(64, 16))), new c(37, new int[]{6, 28, 54, 80, 106, 132, 158}, new e(30, new d(17, 122), new d(4, 123)), new e(28, new d(29, 46), new d(14, 47)), new e(30, new d(49, 24), new d(10, 25)), new e(30, new d(24, 15), new d(46, 16))), new c(38, new int[]{6, 32, 58, 84, 110, 136, 162}, new e(30, new d(4, 122), new d(18, 123)), new e(28, new d(13, 46), new d(32, 47)), new e(30, new d(48, 24), new d(14, 25)), new e(30, new d(42, 15), new d(32, 16))), new c(39, new int[]{6, 26, 54, 82, 110, 138, 166}, new e(30, new d(20, 117), new d(4, 118)), new e(28, new d(40, 47), new d(7, 48)), new e(30, new d(43, 24), new d(22, 25)), new e(30, new d(10, 15), new d(67, 16))), new c(40, new int[]{6, 30, 58, 86, 114, 142, 170}, new e(30, new d(19, 118), new d(6, 119)), new e(28, new d(18, 47), new d(31, 48)), new e(30, new d(34, 24), new d(34, 25)), new e(30, new d(20, 15), new d(61, 16)))};
    }
}
