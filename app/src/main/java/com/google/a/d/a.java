package com.google.a.d;

/* JADX INFO: compiled from: CodaBarReader.java */
/* JADX INFO: loaded from: classes.dex */
public final class a extends o {
    static final char[] cbO = "0123456789-$:/.+ABCD".toCharArray();
    static final int[] cbP = {3, 6, 9, 96, 18, 66, 33, 36, 48, 72, 12, 24, 69, 81, 84, 21, 26, 41, 11, 14};
    private static final char[] cbQ = {'A', 'B', 'C', 'D'};

    static boolean a(char[] cArr, char c) {
        if (cArr == null) {
            return false;
        }
        for (char c2 : cArr) {
            if (c2 == c) {
                return true;
            }
        }
        return false;
    }
}
