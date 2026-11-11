package com.google.android.gms.common.d;

/* JADX INFO: loaded from: classes.dex */
public class m {
    private static final char[] bQA = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final char[] bQB = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String r(byte[] bArr) {
        int i = 0;
        char[] cArr = new char[bArr.length << 1];
        int i2 = 0;
        while (true) {
            int i3 = i;
            if (i2 >= bArr.length) {
                return new String(cArr);
            }
            int i4 = bArr[i2] & 255;
            int i5 = i3 + 1;
            cArr[i3] = bQB[i4 >>> 4];
            i = i5 + 1;
            cArr[i5] = bQB[i4 & 15];
            i2++;
        }
    }
}
