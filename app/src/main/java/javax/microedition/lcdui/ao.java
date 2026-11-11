package javax.microedition.lcdui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import com.gameloft.android.wrapper.au;
import java.io.InputStream;

/* JADX INFO: compiled from: Image.java */
/* JADX INFO: loaded from: classes.dex */
public class ao {
    public static boolean cng = false;
    public static boolean cnh = false;
    public final int cni;
    public final int cnj;
    private al cnk;
    private int cnl;
    public Bitmap mBitmap;

    public static ao m(InputStream inputStream) {
        return new ao(BitmapFactory.decodeStream(inputStream));
    }

    public static ao cL(int i, int i2) {
        return new ao(Bitmap.createBitmap(i, i2, cnh ? Bitmap.Config.RGB_565 : Bitmap.Config.ARGB_8888));
    }

    public static ao a(InputStream inputStream, int i, int i2) {
        Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStream);
        Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapDecodeStream, i, i2, true);
        if (bitmapDecodeStream == bitmapCreateScaledBitmap) {
            return new ao(bitmapDecodeStream);
        }
        if (bitmapDecodeStream != null && !bitmapDecodeStream.isRecycled()) {
            try {
                bitmapDecodeStream.recycle();
            } catch (Exception e) {
            }
        }
        return new ao(bitmapCreateScaledBitmap);
    }

    public al akr() {
        if (!this.mBitmap.isMutable()) {
            throw new IllegalStateException();
        }
        if (this.cnk == null) {
            this.cnk = new al(new Canvas(this.mBitmap));
            this.cnl = this.cnk.cnc.save();
        } else {
            if (this.cnl > 0) {
                this.cnk.cnc.restoreToCount(this.cnl);
            }
            this.cnl = this.cnk.cnc.save();
        }
        return this.cnk;
    }

    public void a(int[] iArr, int i, int i2, int i3, int i4, int i5, int i6) {
        this.mBitmap.getPixels(iArr, i, i2, i3, i4, i5, i6);
    }

    public ao(Bitmap bitmap) {
        this.mBitmap = bitmap;
        this.cni = bitmap.getWidth();
        this.cnj = bitmap.getHeight();
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    public boolean equals(Object obj) {
        boolean zEquals = super.equals(obj);
        if (cng && obj == "freeBitmap" && this.mBitmap != null && !this.mBitmap.isRecycled()) {
            try {
                this.mBitmap.recycle();
                this.mBitmap = null;
            } catch (Exception e) {
                if (au.bhL) {
                    e.printStackTrace();
                }
                this.mBitmap = null;
            }
        }
        return zEquals;
    }

    public void recycle() {
        if (this.mBitmap != null && !this.mBitmap.isRecycled()) {
            try {
                this.mBitmap.recycle();
                this.mBitmap = null;
            } catch (Exception e) {
                if (au.bhL) {
                    e.printStackTrace();
                }
                this.mBitmap = null;
            }
        }
    }
}
