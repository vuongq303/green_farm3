package com.google.a.a.a;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

/* JADX INFO: compiled from: HighLevelEncoder.java */
/* JADX INFO: loaded from: classes.dex */
public final class d {
    static final String[] bZY = {"UPPER", "LOWER", "DIGIT", "MIXED", "PUNCT"};
    static final int[][] bZZ = {new int[]{0, 327708, 327710, 327709, 656318}, new int[]{590318, 0, 327710, 327709, 656318}, new int[]{262158, 590300, 0, 590301, 932798}, new int[]{327709, 327708, 656318, 0, 327710}, new int[]{327711, 656380, 656382, 656381, 0}};
    private static final int[][] caa;
    static final int[][] cab;
    private final byte[] cac;

    static {
        int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 5, 256);
        caa = iArr;
        iArr[0][32] = 1;
        for (int i = 65; i <= 90; i++) {
            caa[0][i] = (i - 65) + 2;
        }
        caa[1][32] = 1;
        for (int i2 = 97; i2 <= 122; i2++) {
            caa[1][i2] = (i2 - 97) + 2;
        }
        caa[2][32] = 1;
        for (int i3 = 48; i3 <= 57; i3++) {
            caa[2][i3] = (i3 - 48) + 2;
        }
        caa[2][44] = 12;
        caa[2][46] = 13;
        int[] iArr2 = {0, 32, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 27, 28, 29, 30, 31, 64, 92, 94, 95, 96, 124, 126, 127};
        for (int i4 = 0; i4 < 28; i4++) {
            caa[3][iArr2[i4]] = i4;
        }
        int[] iArr3 = {0, 13, 0, 0, 0, 0, 33, 39, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 58, 59, 60, 61, 62, 63, 91, 93, 123, 125};
        for (int i5 = 0; i5 < 31; i5++) {
            if (iArr3[i5] > 0) {
                caa[4][iArr3[i5]] = i5;
            }
        }
        int[][] iArr4 = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 6, 6);
        cab = iArr4;
        for (int[] iArr5 : iArr4) {
            Arrays.fill(iArr5, -1);
        }
        cab[0][4] = 0;
        cab[1][4] = 0;
        cab[1][0] = 28;
        cab[3][4] = 0;
        cab[2][4] = 0;
        cab[2][0] = 15;
    }

    public d(byte[] bArr) {
        this.cac = bArr;
    }

    public com.google.a.b.a agw() {
        int i;
        Collection<g> collectionSingletonList = Collections.singletonList(g.caf);
        int i2 = 0;
        while (i2 < this.cac.length) {
            byte b = i2 + 1 < this.cac.length ? this.cac[i2 + 1] : (byte) 0;
            switch (this.cac[i2]) {
                case 13:
                    i = b != 10 ? 0 : 2;
                    break;
                case 44:
                    i = b != 32 ? 0 : 4;
                    break;
                case 46:
                    i = b != 32 ? 0 : 3;
                    break;
                case 58:
                    i = b != 32 ? 0 : 5;
                    break;
                default:
                    i = 0;
                    break;
            }
            if (i > 0) {
                collectionSingletonList = a(collectionSingletonList, i2, i);
                i2++;
            } else {
                collectionSingletonList = a(collectionSingletonList, i2);
            }
            i2++;
        }
        return ((g) Collections.min(collectionSingletonList, new e(this))).t(this.cac);
    }

    private Collection<g> a(Iterable<g> iterable, int i) {
        LinkedList linkedList = new LinkedList();
        Iterator<g> it = iterable.iterator();
        while (it.hasNext()) {
            a(it.next(), i, linkedList);
        }
        return b(linkedList);
    }

    private void a(g gVar, int i, Collection<g> collection) {
        g gVarOC;
        char c = (char) (this.cac[i] & 255);
        boolean z = caa[gVar.getMode()][c] > 0;
        g gVar2 = null;
        int i2 = 0;
        while (i2 <= 4) {
            int i3 = caa[i2][c];
            if (i3 > 0) {
                gVarOC = gVar2 == null ? gVar.oC(i) : gVar2;
                if (!z || i2 == gVar.getMode() || i2 == 2) {
                    collection.add(gVarOC.cr(i2, i3));
                }
                if (!z && cab[gVar.getMode()][i2] >= 0) {
                    collection.add(gVarOC.cs(i2, i3));
                }
            } else {
                gVarOC = gVar2;
            }
            i2++;
            gVar2 = gVarOC;
        }
        if (gVar.agx() > 0 || caa[gVar.getMode()][c] == 0) {
            collection.add(gVar.oB(i));
        }
    }

    private static Collection<g> a(Iterable<g> iterable, int i, int i2) {
        LinkedList linkedList = new LinkedList();
        Iterator<g> it = iterable.iterator();
        while (it.hasNext()) {
            a(it.next(), i, i2, linkedList);
        }
        return b(linkedList);
    }

    private static void a(g gVar, int i, int i2, Collection<g> collection) {
        g gVarOC = gVar.oC(i);
        collection.add(gVarOC.cr(4, i2));
        if (gVar.getMode() != 4) {
            collection.add(gVarOC.cs(4, i2));
        }
        if (i2 == 3 || i2 == 4) {
            collection.add(gVarOC.cr(2, 16 - i2).cr(2, 1));
        }
        if (gVar.agx() > 0) {
            collection.add(gVar.oB(i).oB(i + 1));
        }
    }

    private static Collection<g> b(Iterable<g> iterable) {
        boolean z;
        LinkedList linkedList = new LinkedList();
        for (g gVar : iterable) {
            Iterator it = linkedList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = true;
                    break;
                }
                g gVar2 = (g) it.next();
                if (gVar2.a(gVar)) {
                    z = false;
                    break;
                }
                if (gVar.a(gVar2)) {
                    it.remove();
                }
            }
            if (z) {
                linkedList.add(gVar);
            }
        }
        return linkedList;
    }
}
