package android.support.v4.a;

import android.graphics.Color;

/* JADX INFO: compiled from: ColorUtils.java */
/* JADX INFO: loaded from: classes.dex */
public final class a {
    private static final ThreadLocal<double[]> fr = new ThreadLocal<>();

    public static int a(int i, int i2) {
        int iAlpha = Color.alpha(i2);
        int iAlpha2 = Color.alpha(i);
        int iB = b(iAlpha2, iAlpha);
        return Color.argb(iB, a(Color.red(i), iAlpha2, Color.red(i2), iAlpha, iB), a(Color.green(i), iAlpha2, Color.green(i2), iAlpha, iB), a(Color.blue(i), iAlpha2, Color.blue(i2), iAlpha, iB));
    }

    private static int b(int i, int i2) {
        return 255 - (((255 - i2) * (255 - i)) / 255);
    }

    private static int a(int i, int i2, int i3, int i4, int i5) {
        if (i5 == 0) {
            return 0;
        }
        return (((i * 255) * i2) + ((i3 * i4) * (255 - i2))) / (i5 * 255);
    }

    public static int c(int i, int i2) {
        if (i2 < 0 || i2 > 255) {
            throw new IllegalArgumentException("alpha must be between 0 and 255.");
        }
        return (16777215 & i) | (i2 << 24);
    }
}
