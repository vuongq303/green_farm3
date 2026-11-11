package javax.microedition.lcdui;

import android.graphics.Paint;
import android.graphics.Typeface;

/* JADX INFO: compiled from: Font.java */
/* JADX INFO: loaded from: classes.dex */
public class u {
    static Paint cmz;
    private byte cmt;
    private byte cmu;
    private byte cmv;
    private byte cmw;
    private byte cmx;
    private char[] cmy;
    private static float[] cmA = {14.0f, 11.0f, 18.0f};
    private static u cmD = null;
    private static Typeface[] cmB = new Typeface[8];
    private static byte[] cmC = new byte[8];

    public int a(char[] cArr, int i, int i2) {
        Paint paint = cmz;
        return Math.round(akl().measureText(cArr, i, i2));
    }

    public int z(char c) {
        if (this.cmy == null) {
            this.cmy = new char[1];
        }
        this.cmy[0] = c;
        return a(this.cmy, 0, 1);
    }

    public static u aki() {
        return akk();
    }

    public int akj() {
        return this.cmv;
    }

    private static u akk() {
        if (cmD == null) {
            cmD = new u(0, 0, 0);
        }
        return cmD;
    }

    public static u ag(int i, int i2, int i3) {
        if (i != 32 && i != 64 && i != 0) {
            throw new IllegalArgumentException();
        }
        if (i2 != 1 && i2 != 2 && i2 != 0 && i2 != 4) {
            throw new IllegalArgumentException();
        }
        if (i3 == 16 || i3 == 0 || i3 == 8) {
            return (i == 0 && i2 == 0 && i3 == 0) ? akk() : new u(i, i2, i3);
        }
        throw new IllegalArgumentException();
    }

    public int getHeight() {
        return this.cmw;
    }

    public int getSize() {
        return this.cmt;
    }

    public int getStyle() {
        return this.cmu;
    }

    public int je(String str) {
        return A(str, 0, str.length());
    }

    public int A(String str, int i, int i2) {
        Paint paint = cmz;
        return Math.round(akl().measureText(str, i, i2));
    }

    private Paint akl() {
        return a(cmz);
    }

    public Paint a(Paint paint) {
        int i = (this.cmu & 3) | ((this.cmv & 32) >> 3);
        byte b = cmC[i];
        paint.setTypeface(cmB[i]);
        paint.setTextSize(cmA[this.cmt >> 3]);
        paint.setFakeBoldText((b & 1) != 0);
        paint.setTextSkewX((b & 2) == 0 ? 0.0f : 0.25f);
        return paint;
    }

    private u(int i, int i2, int i3) {
        this.cmv = (byte) i;
        this.cmu = (byte) i2;
        this.cmt = (byte) i3;
        synchronized (cmz) {
            Paint paintAkl = akl();
            int iRound = Math.round(paintAkl.ascent());
            this.cmw = (byte) (Math.round(paintAkl.descent()) - iRound);
            this.cmx = (byte) (-iRound);
        }
    }

    static {
        String str = null;
        for (int i = 0; i < 8; i++) {
            if ((i & 4) != 0) {
                str = "monospace";
            }
            cmB[i] = Typeface.create(str, i & 3);
            cmC[i] = (byte) (i & 3 & (cmB[i].getStyle() ^ (-1)));
        }
        cmz = new Paint();
        cmz.setAntiAlias(true);
    }
}
