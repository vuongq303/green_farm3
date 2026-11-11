package javax.microedition.lcdui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

/* JADX INFO: compiled from: Graphics.java */
/* JADX INFO: loaded from: classes.dex */
public class al {
    public static boolean cmV = true;
    private static int cnd = -1;
    private int SG;
    private int SH;
    private int cmW;
    private int cmX;
    private char[] cmy;
    private u cna;
    private int cnb;
    public Canvas cnc;
    private RectF cne;
    private Path cnf;
    private Paint wN;
    private int acz = -1;
    private int cmY = 0;
    private int cmZ = 0;

    public void b(String str, int i, int i2, int i3) {
        a(str, 0, str.length(), i, i2, i3);
    }

    public void a(String str, int i, int i2, int i3, int i4, int i5) {
        int i6 = ((i5 & 16) == 0 && (i5 & 32) == 0 && (i5 & 2) == 0) ? i5 | 16 : i5;
        if ((i6 & 4) == 0 && (i6 & 1) == 0 && (i6 & 8) == 0) {
            i6 |= 4;
        }
        if ((i6 & 4) != 0) {
            this.wN.setTextAlign(Paint.Align.LEFT);
        } else if ((i6 & 8) != 0) {
            this.wN.setTextAlign(Paint.Align.RIGHT);
        } else if ((i6 & 1) != 0) {
            this.wN.setTextAlign(Paint.Align.CENTER);
        }
        if ((i6 & 16) != 0) {
            i4 = (int) (i4 - this.wN.ascent());
        } else if ((i6 & 2) != 0) {
            i4 = (int) (i4 - (this.wN.ascent() / 2.0f));
        }
        this.cnc.drawText(str, i, i2, i3, i4, pg(0));
    }

    public void ah(int i, int i2, int i3) {
        if ((((i | i2) | i3) >> 8) != 0) {
            throw new IllegalArgumentException();
        }
        this.cnb = (i << 16) | (i2 << 8) | i3;
        this.wN.setColor((-16777216) | this.cnb);
    }

    public void a(u uVar) {
        if (uVar == null) {
            uVar = u.aki();
        }
        this.cna = uVar;
        uVar.a(this.wN);
    }

    public al(Canvas canvas) {
        f(canvas);
        init();
    }

    private void init() {
        this.wN = new Paint();
        this.wN.setDither(false);
        this.wN.setStrokeWidth(1.0f);
        this.wN.setStrokeCap(Paint.Cap.SQUARE);
        this.cna = u.aki();
        this.cmy = new char[1];
        this.cne = new RectF();
        this.cnf = new Path();
    }

    public al() {
        init();
    }

    public void f(Canvas canvas) {
        this.cnc = canvas;
        this.SG = canvas.getWidth();
        this.SH = canvas.getHeight();
        this.cmW = canvas.getWidth();
        this.cmX = canvas.getHeight();
    }

    private Paint pg(int i) {
        Paint paint = this.wN;
        paint.setAntiAlias(true);
        paint.setStyle(i != 1 ? Paint.Style.FILL : Paint.Style.STROKE);
        return paint;
    }
}
