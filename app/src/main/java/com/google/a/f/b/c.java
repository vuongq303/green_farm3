package com.google.a.f.b;

import com.google.a.i;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: Encoder.java */
/* JADX INFO: loaded from: classes.dex */
public final class c {
    private static final int[] cdr = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 36, -1, -1, -1, 37, 38, -1, -1, -1, -1, 39, 40, -1, 41, 42, 43, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 44, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, -1, -1, -1, -1, -1};

    private static int b(b bVar) {
        return e.c(bVar) + e.d(bVar) + e.e(bVar) + e.f(bVar);
    }

    public static g a(String str, com.google.a.f.a.a aVar, Map<com.google.a.c, ?> map) throws i {
        com.google.a.f.a.c cVarA;
        com.google.a.b.c cVarIA;
        String string = "ISO-8859-1";
        if (map != null && map.containsKey(com.google.a.c.CHARACTER_SET)) {
            string = map.get(com.google.a.c.CHARACTER_SET).toString();
        }
        com.google.a.f.a.b bVarAA = aA(str, string);
        com.google.a.b.a aVar2 = new com.google.a.b.a();
        if (bVarAA == com.google.a.f.a.b.BYTE && !"ISO-8859-1".equals(string) && (cVarIA = com.google.a.b.c.iA(string)) != null) {
            a(cVarIA, aVar2);
        }
        a(bVarAA, aVar2);
        com.google.a.b.a aVar3 = new com.google.a.b.a();
        a(str, bVarAA, aVar3, string);
        if (map != null && map.containsKey(com.google.a.c.QR_VERSION)) {
            cVarA = com.google.a.f.a.c.oU(Integer.parseInt(map.get(com.google.a.c.QR_VERSION).toString()));
            if (!a(a(bVarAA, aVar2, aVar3, cVarA), cVarA, aVar)) {
                throw new i("Data too big for requested version");
            }
        } else {
            cVarA = a(aVar, bVarAA, aVar2, aVar3);
        }
        com.google.a.b.a aVar4 = new com.google.a.b.a();
        aVar4.a(aVar2);
        a(bVarAA == com.google.a.f.a.b.BYTE ? aVar3.agA() : str.length(), cVarA, bVarAA, aVar4);
        aVar4.a(aVar3);
        com.google.a.f.a.e eVarA = cVarA.a(aVar);
        int iAho = cVarA.aho() - eVarA.ahu();
        a(iAho, aVar4);
        com.google.a.b.a aVarA = a(aVar4, cVarA.aho(), iAho, eVarA.aht());
        g gVar = new g();
        gVar.b(aVar);
        gVar.a(bVarAA);
        gVar.b(cVarA);
        int iAhp = cVarA.ahp();
        b bVar = new b(iAhp, iAhp);
        int iA = a(aVarA, aVar, cVarA, bVar);
        gVar.oY(iA);
        f.a(aVarA, aVar, cVarA, iA, bVar);
        gVar.k(bVar);
        return gVar;
    }

    private static com.google.a.f.a.c a(com.google.a.f.a.a aVar, com.google.a.f.a.b bVar, com.google.a.b.a aVar2, com.google.a.b.a aVar3) {
        return a(a(bVar, aVar2, aVar3, a(a(bVar, aVar2, aVar3, com.google.a.f.a.c.oU(1)), aVar)), aVar);
    }

    private static int a(com.google.a.f.a.b bVar, com.google.a.b.a aVar, com.google.a.b.a aVar2, com.google.a.f.a.c cVar) {
        return aVar.getSize() + bVar.a(cVar) + aVar2.getSize();
    }

    static int oV(int i) {
        if (i < cdr.length) {
            return cdr[i];
        }
        return -1;
    }

    private static com.google.a.f.a.b aA(String str, String str2) {
        boolean z;
        if ("Shift_JIS".equals(str2) && iG(str)) {
            return com.google.a.f.a.b.KANJI;
        }
        int i = 0;
        boolean z2 = false;
        boolean z3 = false;
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            if (cCharAt >= '0' && cCharAt <= '9') {
                z = z2;
                z3 = true;
            } else {
                if (oV(cCharAt) == -1) {
                    return com.google.a.f.a.b.BYTE;
                }
                z = true;
            }
            i++;
            z2 = z;
        }
        if (z2) {
            return com.google.a.f.a.b.ALPHANUMERIC;
        }
        if (z3) {
            return com.google.a.f.a.b.NUMERIC;
        }
        return com.google.a.f.a.b.BYTE;
    }

    private static boolean iG(String str) {
        try {
            byte[] bytes = str.getBytes("Shift_JIS");
            int length = bytes.length;
            if (length % 2 != 0) {
                return false;
            }
            for (int i = 0; i < length; i += 2) {
                int i2 = bytes[i] & 255;
                if ((i2 < 129 || i2 > 159) && (i2 < 224 || i2 > 235)) {
                    return false;
                }
            }
            return true;
        } catch (UnsupportedEncodingException e) {
            return false;
        }
    }

    private static int a(com.google.a.b.a aVar, com.google.a.f.a.a aVar2, com.google.a.f.a.c cVar, b bVar) throws i {
        int i = Integer.MAX_VALUE;
        int i2 = -1;
        int i3 = 0;
        while (i3 < 8) {
            f.a(aVar, aVar2, cVar, i3, bVar);
            int iB = b(bVar);
            if (iB < i) {
                i2 = i3;
            } else {
                iB = i;
            }
            i3++;
            i = iB;
        }
        return i2;
    }

    private static com.google.a.f.a.c a(int i, com.google.a.f.a.a aVar) throws i {
        for (int i2 = 1; i2 <= 40; i2++) {
            com.google.a.f.a.c cVarOU = com.google.a.f.a.c.oU(i2);
            if (a(i, cVarOU, aVar)) {
                return cVarOU;
            }
        }
        throw new i("Data too big");
    }

    private static boolean a(int i, com.google.a.f.a.c cVar, com.google.a.f.a.a aVar) {
        return cVar.aho() - cVar.a(aVar).ahu() >= (i + 7) / 8;
    }

    static void a(int i, com.google.a.b.a aVar) throws i {
        int i2 = i << 3;
        if (aVar.getSize() > i2) {
            throw new i("data bits cannot fit in the QR Code" + aVar.getSize() + " > " + i2);
        }
        for (int i3 = 0; i3 < 4 && aVar.getSize() < i2; i3++) {
            aVar.cg(false);
        }
        int size = aVar.getSize() & 7;
        if (size > 0) {
            while (size < 8) {
                aVar.cg(false);
                size++;
            }
        }
        int iAgA = i - aVar.agA();
        for (int i4 = 0; i4 < iAgA; i4++) {
            aVar.cv((i4 & 1) == 0 ? 236 : 17, 8);
        }
        if (aVar.getSize() != i2) {
            throw new i("Bits size does not equal capacity");
        }
    }

    static void a(int i, int i2, int i3, int i4, int[] iArr, int[] iArr2) throws i {
        if (i4 >= i3) {
            throw new i("Block ID too large");
        }
        int i5 = i % i3;
        int i6 = i3 - i5;
        int i7 = i / i3;
        int i8 = i7 + 1;
        int i9 = i2 / i3;
        int i10 = i9 + 1;
        int i11 = i7 - i9;
        int i12 = i8 - i10;
        if (i11 != i12) {
            throw new i("EC bytes mismatch");
        }
        if (i3 != i6 + i5) {
            throw new i("RS blocks mismatch");
        }
        if (i != (i5 * (i10 + i12)) + ((i9 + i11) * i6)) {
            throw new i("Total bytes mismatch");
        }
        if (i4 < i6) {
            iArr[0] = i9;
            iArr2[0] = i11;
        } else {
            iArr[0] = i10;
            iArr2[0] = i12;
        }
    }

    static com.google.a.b.a a(com.google.a.b.a aVar, int i, int i2, int i3) throws i {
        if (aVar.agA() != i2) {
            throw new i("Number of bits and data bytes does not match");
        }
        ArrayList arrayList = new ArrayList(i3);
        int i4 = 0;
        int iMax = 0;
        int i5 = 0;
        int i6 = 0;
        while (i4 < i3) {
            int[] iArr = new int[1];
            int[] iArr2 = new int[1];
            a(i, i2, i3, i4, iArr, iArr2);
            int i7 = iArr[0];
            byte[] bArr = new byte[i7];
            aVar.a(i6 << 3, bArr, 0, i7);
            byte[] bArrC = C(bArr, iArr2[0]);
            arrayList.add(new a(bArr, bArrC));
            int iMax2 = Math.max(i5, i7);
            i4++;
            iMax = Math.max(iMax, bArrC.length);
            i5 = iMax2;
            i6 = iArr[0] + i6;
        }
        if (i2 != i6) {
            throw new i("Data bytes does not match offset");
        }
        com.google.a.b.a aVar2 = new com.google.a.b.a();
        for (int i8 = 0; i8 < i5; i8++) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                byte[] bArrAhw = ((a) it.next()).ahw();
                if (i8 < bArrAhw.length) {
                    aVar2.cv(bArrAhw[i8], 8);
                }
            }
        }
        for (int i9 = 0; i9 < iMax; i9++) {
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                byte[] bArrAhx = ((a) it2.next()).ahx();
                if (i9 < bArrAhx.length) {
                    aVar2.cv(bArrAhx[i9], 8);
                }
            }
        }
        if (i != aVar2.agA()) {
            throw new i("Interleaving error: " + i + " and " + aVar2.agA() + " differ.");
        }
        return aVar2;
    }

    static byte[] C(byte[] bArr, int i) {
        int length = bArr.length;
        int[] iArr = new int[length + i];
        for (int i2 = 0; i2 < length; i2++) {
            iArr[i2] = bArr[i2] & 255;
        }
        new com.google.a.b.a.c(com.google.a.b.a.a.caX).g(iArr, i);
        byte[] bArr2 = new byte[i];
        for (int i3 = 0; i3 < i; i3++) {
            bArr2[i3] = (byte) iArr[length + i3];
        }
        return bArr2;
    }

    static void a(com.google.a.f.a.b bVar, com.google.a.b.a aVar) {
        aVar.cv(bVar.ahl(), 4);
    }

    static void a(int i, com.google.a.f.a.c cVar, com.google.a.f.a.b bVar, com.google.a.b.a aVar) throws i {
        int iA = bVar.a(cVar);
        if (i >= (1 << iA)) {
            throw new i(i + " is bigger than " + ((1 << iA) - 1));
        }
        aVar.cv(i, iA);
    }

    static void a(String str, com.google.a.f.a.b bVar, com.google.a.b.a aVar, String str2) throws i {
        switch (bVar) {
            case NUMERIC:
                a((CharSequence) str, aVar);
                return;
            case ALPHANUMERIC:
                b(str, aVar);
                return;
            case BYTE:
                a(str, aVar, str2);
                return;
            case KANJI:
                a(str, aVar);
                return;
            default:
                throw new i("Invalid mode: " + bVar);
        }
    }

    static void a(CharSequence charSequence, com.google.a.b.a aVar) {
        int length = charSequence.length();
        int i = 0;
        while (i < length) {
            int iCharAt = charSequence.charAt(i) - '0';
            if (i + 2 < length) {
                aVar.cv((iCharAt * 100) + ((charSequence.charAt(i + 1) - '0') * 10) + (charSequence.charAt(i + 2) - '0'), 10);
                i += 3;
            } else if (i + 1 < length) {
                aVar.cv((iCharAt * 10) + (charSequence.charAt(i + 1) - '0'), 7);
                i += 2;
            } else {
                aVar.cv(iCharAt, 4);
                i++;
            }
        }
    }

    static void b(CharSequence charSequence, com.google.a.b.a aVar) throws i {
        int length = charSequence.length();
        int i = 0;
        while (i < length) {
            int iOV = oV(charSequence.charAt(i));
            if (iOV == -1) {
                throw new i();
            }
            if (i + 1 < length) {
                int iOV2 = oV(charSequence.charAt(i + 1));
                if (iOV2 == -1) {
                    throw new i();
                }
                aVar.cv((iOV * 45) + iOV2, 11);
                i += 2;
            } else {
                aVar.cv(iOV, 6);
                i++;
            }
        }
    }

    static void a(String str, com.google.a.b.a aVar, String str2) throws i {
        try {
            for (byte b : str.getBytes(str2)) {
                aVar.cv(b, 8);
            }
        } catch (UnsupportedEncodingException e) {
            throw new i(e);
        }
    }

    static void a(String str, com.google.a.b.a aVar) throws i {
        int i;
        try {
            byte[] bytes = str.getBytes("Shift_JIS");
            int length = bytes.length;
            for (int i2 = 0; i2 < length; i2 += 2) {
                int i3 = ((bytes[i2] & 255) << 8) | (bytes[i2 + 1] & 255);
                if (i3 >= 33088 && i3 <= 40956) {
                    i = i3 - 33088;
                } else {
                    i = (i3 < 57408 || i3 > 60351) ? -1 : i3 - 49472;
                }
                if (i == -1) {
                    throw new i("Invalid byte sequence");
                }
                aVar.cv((i & 255) + ((i >> 8) * 192), 13);
            }
        } catch (UnsupportedEncodingException e) {
            throw new i(e);
        }
    }

    private static void a(com.google.a.b.c cVar, com.google.a.b.a aVar) {
        aVar.cv(com.google.a.f.a.b.ECI.ahl(), 4);
        aVar.cv(cVar.getValue(), 8);
    }
}
