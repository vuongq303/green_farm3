package com.google.a.d;

import com.facebook.internal.FacebookRequestErrorClassification;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: Code128Writer.java */
/* JADX INFO: loaded from: classes.dex */
public final class d extends p {
    @Override // com.google.a.d.p, com.google.a.h
    public com.google.a.b.b a(String str, com.google.a.a aVar, int i, int i2, Map<com.google.a.c, ?> map) {
        if (aVar != com.google.a.a.CODE_128) {
            throw new IllegalArgumentException("Can only encode CODE_128, but got " + aVar);
        }
        return super.a(str, aVar, i, i2, map);
    }

    @Override // com.google.a.d.p
    public boolean[] iC(String str) {
        int iCharAt;
        int iA = 0;
        int length = str.length();
        if (length <= 0 || length > 80) {
            throw new IllegalArgumentException("Contents length should be between 1 and 80 characters, but got " + length);
        }
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt < ' ' || cCharAt > '~') {
                switch (cCharAt) {
                    case 241:
                    case 242:
                    case 243:
                    case 244:
                        break;
                    default:
                        throw new IllegalArgumentException("Bad character in input: " + cCharAt);
                }
            }
        }
        ArrayList<int[]> arrayList = new ArrayList();
        int i2 = 0;
        int i3 = 0;
        int i4 = 1;
        int i5 = 0;
        while (i2 < length) {
            int iB = b(str, i2, i3);
            if (iB == i3) {
                switch (str.charAt(i2)) {
                    case 241:
                        iCharAt = FacebookRequestErrorClassification.EC_INVALID_SESSION;
                        break;
                    case 242:
                        iCharAt = 97;
                        break;
                    case 243:
                        iCharAt = 96;
                        break;
                    case 244:
                        iCharAt = 100;
                        break;
                    default:
                        if (i3 == 100) {
                            iCharAt = str.charAt(i2) - ' ';
                        } else {
                            iCharAt = Integer.parseInt(str.substring(i2, i2 + 2));
                            i2++;
                        }
                        break;
                }
                i2++;
                iB = i3;
            } else if (i3 != 0) {
                iCharAt = iB;
            } else if (iB == 100) {
                iCharAt = 104;
            } else {
                iCharAt = 105;
            }
            arrayList.add(c.cbV[iCharAt]);
            i5 += iCharAt * i4;
            i3 = iB;
            i4 = i2 != 0 ? i4 + 1 : i4;
        }
        arrayList.add(c.cbV[i5 % 103]);
        arrayList.add(c.cbV[106]);
        int i6 = 0;
        for (int[] iArr : arrayList) {
            for (int i7 : iArr) {
                i6 += i7;
            }
        }
        boolean[] zArr = new boolean[i6];
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            iA += a(zArr, iA, (int[]) it.next(), true);
        }
        return zArr;
    }

    private static e d(CharSequence charSequence, int i) {
        int length = charSequence.length();
        if (i >= length) {
            return e.UNCODABLE;
        }
        char cCharAt = charSequence.charAt(i);
        if (cCharAt == 241) {
            return e.FNC_1;
        }
        if (cCharAt < '0' || cCharAt > '9') {
            return e.UNCODABLE;
        }
        if (i + 1 >= length) {
            return e.ONE_DIGIT;
        }
        char cCharAt2 = charSequence.charAt(i + 1);
        if (cCharAt2 < '0' || cCharAt2 > '9') {
            return e.ONE_DIGIT;
        }
        return e.TWO_DIGITS;
    }

    private static int b(CharSequence charSequence, int i, int i2) {
        e eVarD;
        e eVarD2;
        e eVarD3 = d(charSequence, i);
        if (eVarD3 == e.UNCODABLE || eVarD3 == e.ONE_DIGIT) {
            return 100;
        }
        if (i2 != 99) {
            if (i2 == 100) {
                if (eVarD3 != e.FNC_1 && (eVarD = d(charSequence, i + 2)) != e.UNCODABLE && eVarD != e.ONE_DIGIT) {
                    if (eVarD == e.FNC_1) {
                        return d(charSequence, i + 3) == e.TWO_DIGITS ? 99 : 100;
                    }
                    int i3 = i + 4;
                    while (true) {
                        eVarD2 = d(charSequence, i3);
                        if (eVarD2 != e.TWO_DIGITS) {
                            break;
                        }
                        i3 += 2;
                    }
                    return eVarD2 == e.ONE_DIGIT ? 100 : 99;
                }
                return i2;
            }
            if (eVarD3 == e.FNC_1) {
                eVarD3 = d(charSequence, i + 1);
            }
            return eVarD3 == e.TWO_DIGITS ? 99 : 100;
        }
        return i2;
    }
}
