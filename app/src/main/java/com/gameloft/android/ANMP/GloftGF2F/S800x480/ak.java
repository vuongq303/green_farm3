package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: compiled from: Gzip.java */
/* JADX INFO: loaded from: classes.dex */
public class ak {
    private String acA;
    private String filename;

    private static void a(cg cgVar, cg cgVar2) throws IOException {
        int iEU = cgVar.eU(2);
        if ((cgVar.eU(2) ^ iEU) != 65535) {
            throw new IOException("Invalid block.");
        }
        while (true) {
            int i = iEU - 1;
            if (iEU > 0) {
                int i2 = cgVar.read();
                if (i2 < 0) {
                    throw new IOException("Unexpected EOF.");
                }
                cgVar2.write(i2);
                iEU = i;
            } else {
                return;
            }
        }
    }

    private static void a(cg cgVar, cg cgVar2, int[] iArr, int[] iArr2) throws IOException {
        while (true) {
            int iA = at.a(cgVar, iArr);
            if (iA != 256) {
                if (iA < 256) {
                    cgVar2.write(iA);
                } else {
                    if (iArr2 == null) {
                        throw new IOException("no distance tree");
                    }
                    cgVar2.at(at.b(at.a(cgVar, iArr2), cgVar), at.a(iA, cgVar));
                }
            } else {
                return;
            }
        }
    }

    private static void b(cg cgVar, cg cgVar2) throws IOException {
        int iEV = cgVar.eV(5) + 257;
        int iEV2 = cgVar.eV(5) + 1;
        int iEV3 = cgVar.eV(4) + 4;
        char[] cArr = new char[19];
        for (int i = 0; i < iEV3; i++) {
            cArr[at.adK[i]] = (char) cgVar.eV(3);
        }
        int[] iArrA = at.a(7, cArr);
        int[] iArrA2 = at.a(15, at.a(cgVar, iArrA, iEV));
        char[] cArrA = at.a(cgVar, iArrA, iEV2);
        int[] iArrA3 = null;
        if (cArrA.length != 1 || cArrA[0] != 0) {
            iArrA3 = at.a(15, cArrA);
        }
        a(cgVar, cgVar2, iArrA2, iArrA3);
    }

    private static long c(cg cgVar, cg cgVar2) throws IOException {
        boolean z;
        do {
            z = cgVar.eV(1) != 0;
            switch (cgVar.eV(2)) {
                case 0:
                    cgVar.oG();
                    a(cgVar, cgVar2);
                    break;
                case 1:
                    a(cgVar, cgVar2, at.adI, at.adJ);
                    break;
                case 2:
                    b(cgVar, cgVar2);
                    break;
                default:
                    throw new IOException("Invalid block.");
            }
        } while (!z);
        cgVar.oG();
        return cgVar2.getSize();
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0042  */
    private static void a(bd bdVar, byte[] bArr, int i, cg cgVar) throws IOException {
        int i2 = 0;
        int iA = 0;
        while (i2 < i) {
            int iF = bdVar.f(bArr, i2, i - i2);
            if (iF >= 0) {
                int i3 = i2 - iF;
                int i4 = 0;
                while (i2 + i4 < i && (bArr[i2 + i4] & 255) == (bArr[iF + i4] & 255) && i4 + 1 < 256 && i3 + i4 + 1 < 2048) {
                    i4++;
                }
                if (i4 > 2) {
                    at.d(i4, cgVar);
                    at.e(i3, cgVar);
                    i2 += i4 - 1;
                } else {
                    int i5 = bArr[i2] & 255;
                    iA = bdVar.a(iA, (byte) i5, i2 - 2);
                    at.c(i5, cgVar);
                }
            } else {
                int i6 = bArr[i2] & 255;
                iA = bdVar.a(iA, (byte) i6, i2 - 2);
                at.c(i6, cgVar);
            }
            i2++;
        }
    }

    private static void d(cg cgVar, cg cgVar2) throws IOException {
        bd bdVar = new bd(2048);
        byte[] bArr = new byte[2048];
        boolean z = false;
        while (!z) {
            int i = cgVar.read(bArr, 0, bArr.length);
            if (i < 0) {
                break;
            }
            z = i < bArr.length;
            bdVar.reset();
            cgVar2.au(z ? 1 : 0, 1);
            cgVar2.au(1, 2);
            a(bdVar, bArr, i, cgVar2);
            at.c(256, cgVar2);
        }
        cgVar2.oH();
    }

    public static int a(InputStream inputStream, OutputStream outputStream) throws IOException {
        cg cgVar = new cg(inputStream, true, 0);
        cg cgVar2 = new cg(outputStream, false, 0);
        cgVar2.as(35615, 2);
        cgVar2.write(8);
        for (int i = 0; i < 7; i++) {
            cgVar2.write(0);
        }
        d(cgVar, cgVar2);
        cgVar2.as(cgVar.oD(), 4);
        cgVar2.as(cgVar.getSize(), 4);
        return cgVar2.getSize();
    }

    private static ak a(cg cgVar) throws IOException {
        ak akVar = new ak();
        cgVar.oC();
        if (cgVar.eU(2) != 35615) {
            throw new IOException("Bad magic number");
        }
        if (cgVar.eU(1) != 8) {
            throw new IOException("Unsupported compression method");
        }
        int iEU = cgVar.eU(1);
        cgVar.eT(6);
        if ((iEU & 4) != 0) {
            int iEU2 = cgVar.eU(2);
            while (true) {
                int i = iEU2 - 1;
                if (iEU2 <= 0) {
                    break;
                }
                cgVar.read();
                iEU2 = i;
            }
        }
        if ((iEU & 8) != 0) {
            akVar.filename = cgVar.oF();
        }
        if ((iEU & 16) != 0) {
            akVar.acA = cgVar.oF();
        }
        if ((iEU & 2) != 0) {
            if (cgVar.eU(2) != (cgVar.oD() & 65535)) {
                throw new IOException("Header CRC check failed.");
            }
        }
        cgVar.ar(false);
        return akVar;
    }

    private static void e(cg cgVar, cg cgVar2) throws IOException {
        if (cgVar.eU(4) != cgVar2.oD()) {
            throw new IOException("CRC check failed.");
        }
        if ((cgVar2.getSize() & (-1)) != cgVar.eU(4)) {
            throw new IOException("Size mismatches.");
        }
    }

    private static ak f(cg cgVar, cg cgVar2) throws IOException {
        ak akVarA = a(cgVar);
        cgVar2.oC();
        c(cgVar, cgVar2);
        e(cgVar, cgVar2);
        return akVarA;
    }

    public static ak b(InputStream inputStream, OutputStream outputStream) {
        try {
            return f(new cg(inputStream, false, 0), new cg(outputStream, true, 15));
        } catch (IOException failure) {
            throw ak.<RuntimeException>propagateChecked(failure);
        }
    }

    // The original DEX propagates the same IOException without a handler here.
    @SuppressWarnings("unchecked")
    private static <Failure extends Throwable> RuntimeException propagateChecked(Throwable failure) throws Failure {
        throw (Failure) failure;
    }
}
