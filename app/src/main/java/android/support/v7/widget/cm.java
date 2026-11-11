package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/* JADX INFO: compiled from: RoundRectDrawable.java */
/* JADX INFO: loaded from: classes.dex */
class cm extends Drawable {
    private ColorStateList fJ;
    private float wM;
    private final RectF wO;
    private final Rect wP;
    private float wQ;
    private ColorStateList wT;
    private PorterDuffColorFilter wU;
    private boolean wR = false;
    private boolean wS = true;
    private PorterDuff.Mode fK = PorterDuff.Mode.SRC_IN;
    private final Paint wN = new Paint(5);

    public cm(ColorStateList colorStateList, float f) {
        this.wM = f;
        e(colorStateList);
        this.wO = new RectF();
        this.wP = new Rect();
    }

    private void e(ColorStateList colorStateList) {
        if (colorStateList == null) {
            colorStateList = ColorStateList.valueOf(0);
        }
        this.wT = colorStateList;
        this.wN.setColor(this.wT.getColorForState(getState(), this.wT.getDefaultColor()));
    }

    void a(float f, boolean z, boolean z2) {
        if (f != this.wQ || this.wR != z || this.wS != z2) {
            this.wQ = f;
            this.wR = z;
            this.wS = z2;
            d(null);
            invalidateSelf();
        }
    }

    float eQ() {
        return this.wQ;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        boolean z;
        Paint paint = this.wN;
        if (this.wU != null && paint.getColorFilter() == null) {
            paint.setColorFilter(this.wU);
            z = true;
        } else {
            z = false;
        }
        canvas.drawRoundRect(this.wO, this.wM, this.wM, paint);
        if (z) {
            paint.setColorFilter(null);
        }
    }

    private void d(Rect rect) {
        if (rect == null) {
            rect = getBounds();
        }
        this.wO.set(rect.left, rect.top, rect.right, rect.bottom);
        this.wP.set(rect);
        if (this.wR) {
            this.wP.inset((int) Math.ceil(cn.b(this.wQ, this.wM, this.wS)), (int) Math.ceil(cn.a(this.wQ, this.wM, this.wS)));
            this.wO.set(this.wP);
        }
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        d(rect);
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
        outline.setRoundRect(this.wP, this.wM);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.wN.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.wN.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public float getRadius() {
        return this.wM;
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        this.fJ = colorStateList;
        this.wU = a(this.fJ, this.fK);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintMode(PorterDuff.Mode mode) {
        this.fK = mode;
        this.wU = a(this.fJ, this.fK);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        int colorForState = this.wT.getColorForState(iArr, this.wT.getDefaultColor());
        boolean z = colorForState != this.wN.getColor();
        if (z) {
            this.wN.setColor(colorForState);
        }
        if (this.fJ == null || this.fK == null) {
            return z;
        }
        this.wU = a(this.fJ, this.fK);
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return (this.fJ != null && this.fJ.isStateful()) || (this.wT != null && this.wT.isStateful()) || super.isStateful();
    }

    private PorterDuffColorFilter a(ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
    }
}
