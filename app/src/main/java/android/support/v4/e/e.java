package android.support.v4.e;

/* JADX INFO: compiled from: ContainerHelpers.java */
/* JADX INFO: loaded from: classes.dex */
class e {
    static final int[] gB = new int[0];
    static final long[] gC = new long[0];
    static final Object[] gD = new Object[0];

    public static int idealIntArraySize(int i) {
        return s(i * 4) / 4;
    }

    public static int r(int i) {
        return s(i * 8) / 8;
    }

    public static int s(int i) {
        for (int i2 = 4; i2 < 32; i2++) {
            if (i <= (1 << i2) - 12) {
                return (1 << i2) - 12;
            }
        }
        return i;
    }

    public static boolean b(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    static int a(int[] iArr, int i, int i2) {
        int i3 = 0;
        int i4 = i - 1;
        while (i3 <= i4) {
            int i5 = (i3 + i4) >>> 1;
            int i6 = iArr[i5];
            if (i6 < i2) {
                i3 = i5 + 1;
            } else {
                if (i6 <= i2) {
                    return i5;
                }
                i4 = i5 - 1;
            }
        }
        return i3 ^ (-1);
    }

    static int a(long[] jArr, int i, long j) {
        int i2 = 0;
        int i3 = i - 1;
        while (i2 <= i3) {
            int i4 = (i2 + i3) >>> 1;
            long j2 = jArr[i4];
            if (j2 < j) {
                i2 = i4 + 1;
            } else {
                if (j2 <= j) {
                    return i4;
                }
                i3 = i4 - 1;
            }
        }
        return i2 ^ (-1);
    }
}
