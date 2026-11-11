package com.google.a.d;

/* JADX INFO: compiled from: CodaBarWriter.java */
/* JADX INFO: loaded from: classes.dex */
public final class b extends p {
    private static final char[] cbR = {'A', 'B', 'C', 'D'};
    private static final char[] cbS = {'T', 'N', '*', 'E'};
    private static final char[] cbT = {'/', ':', '+', '.'};
    private static final char cbU = cbR[0];

    @Override // com.google.a.d.p
    public boolean[] iC(String str) {
        int i;
        if (str.length() < 2) {
            str = cbU + str + cbU;
        } else {
            char upperCase = Character.toUpperCase(str.charAt(0));
            char upperCase2 = Character.toUpperCase(str.charAt(str.length() - 1));
            boolean zA = a.a(cbR, upperCase);
            boolean zA2 = a.a(cbR, upperCase2);
            boolean zA3 = a.a(cbS, upperCase);
            boolean zA4 = a.a(cbS, upperCase2);
            if (zA) {
                if (!zA2) {
                    throw new IllegalArgumentException("Invalid start/end guards: " + str);
                }
            } else if (zA3) {
                if (!zA4) {
                    throw new IllegalArgumentException("Invalid start/end guards: " + str);
                }
            } else {
                if (zA2 || zA4) {
                    throw new IllegalArgumentException("Invalid start/end guards: " + str);
                }
                str = cbU + str + cbU;
            }
        }
        int i2 = 20;
        for (int i3 = 1; i3 < str.length() - 1; i3++) {
            if (Character.isDigit(str.charAt(i3)) || str.charAt(i3) == '-' || str.charAt(i3) == '$') {
                i2 += 9;
            } else if (a.a(cbT, str.charAt(i3))) {
                i2 += 10;
            } else {
                throw new IllegalArgumentException("Cannot encode : '" + str.charAt(i3) + '\'');
            }
        }
        boolean[] zArr = new boolean[(str.length() - 1) + i2];
        int i4 = 0;
        for (int i5 = 0; i5 < str.length(); i5++) {
            char upperCase3 = Character.toUpperCase(str.charAt(i5));
            if (i5 == 0 || i5 == str.length() - 1) {
                switch (upperCase3) {
                    case '*':
                        upperCase3 = 'C';
                        break;
                    case 'E':
                        upperCase3 = 'D';
                        break;
                    case 'N':
                        upperCase3 = 'B';
                        break;
                    case 'T':
                        upperCase3 = 'A';
                        break;
                }
            }
            int i6 = 0;
            while (true) {
                if (i6 >= a.cbO.length) {
                    i = 0;
                } else if (upperCase3 != a.cbO[i6]) {
                    i6++;
                } else {
                    i = a.cbP[i6];
                }
            }
            int i7 = 0;
            int i8 = 0;
            boolean z = true;
            int i9 = i4;
            while (i7 < 7) {
                zArr[i9] = z;
                i9++;
                if (((i >> (6 - i7)) & 1) == 0 || i8 == 1) {
                    i7++;
                    i8 = 0;
                    z = !z;
                } else {
                    i8++;
                }
            }
            if (i5 < str.length() - 1) {
                zArr[i9] = false;
                i4 = i9 + 1;
            } else {
                i4 = i9;
            }
        }
        return zArr;
    }
}
