package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.v7.cardview.R;

/* JADX INFO: compiled from: RoundRectDrawableWithShadow.java */
/* JADX INFO: loaded from: classes.dex */
class cn extends Drawable {
    static final double wV = Math.cos(Math.toRadians(45.0d));
    static co wX;
    private ColorStateList wT;
    final int wW;
    Paint wY;
    Paint wZ;
    final RectF xa;
    float xb;
    Path xc;
    float xd;
    float xe;
    float xf;
    float xg;
    private final int xi;
    private final int xj;
    private boolean xh = true;
    private boolean xk = true;
    private boolean xl = false;
    Paint wN = new Paint(5);

    cn(Resources resources, ColorStateList colorStateList, float f, float f2, float f3) {
        this.xi = resources.getColor(R.color.cardview_shadow_start_color);
        this.xj = resources.getColor(R.color.cardview_shadow_end_color);
        this.wW = resources.getDimensionPixelSize(R.dimen.cardview_compat_inset_shadow);
        e(colorStateList);
        this.wY = new Paint(5);
        this.wY.setStyle(Paint.Style.FILL);
        this.xb = (int) (0.5f + f);
        this.xa = new RectF();
        this.wZ = new Paint(this.wY);
        this.wZ.setAntiAlias(false);
        i(f2, f3);
    }

    private void e(ColorStateList colorStateList) {
        if (colorStateList == null) {
            colorStateList = ColorStateList.valueOf(0);
        }
        this.wT = colorStateList;
        this.wN.setColor(this.wT.getColorForState(getState(), this.wT.getDefaultColor()));
    }

    private int e(float f) {
        int i = (int) (0.5f + f);
        if (i % 2 == 1) {
            return i - 1;
        }
        return i;
    }

    public void P(boolean z) {
        this.xk = z;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.wN.setAlpha(i);
        this.wY.setAlpha(i);
        this.wZ.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.xh = true;
    }

    void i(float f, float f2) {
        if (f < 0.0f) {
            throw new IllegalArgumentException("Invalid shadow size " + f + ". Must be >= 0");
        }
        if (f2 < 0.0f) {
            throw new IllegalArgumentException("Invalid max shadow size " + f2 + ". Must be >= 0");
        }
        float fE = e(f);
        float fE2 = e(f2);
        if (fE > fE2) {
            if (!this.xl) {
                this.xl = true;
            }
            fE = fE2;
        }
        if (this.xg != fE || this.xe != fE2) {
            this.xg = fE;
            this.xe = fE2;
            this.xf = (int) ((fE * 1.5f) + this.wW + 0.5f);
            this.xd = this.wW + fE2;
            this.xh = true;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(Rect rect) {
        int iCeil = (int) Math.ceil(a(this.xe, this.xb, this.xk));
        int iCeil2 = (int) Math.ceil(b(this.xe, this.xb, this.xk));
        rect.set(iCeil2, iCeil, iCeil2, iCeil);
        return true;
    }

    static float a(float f, float f2, boolean z) {
        return z ? (float) (((double) (1.5f * f)) + ((1.0d - wV) * ((double) f2))) : 1.5f * f;
    }

    static float b(float f, float f2, boolean z) {
        if (z) {
            return (float) (((double) f) + ((1.0d - wV) * ((double) f2)));
        }
        return f;
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        int colorForState = this.wT.getColorForState(iArr, this.wT.getDefaultColor());
        if (this.wN.getColor() == colorForState) {
            return false;
        }
        this.wN.setColor(colorForState);
        this.xh = true;
        invalidateSelf();
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return (this.wT != null && this.wT.isStateful()) || super.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.wN.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (this.xh) {
            e(getBounds());
            this.xh = false;
        }
        canvas.translate(0.0f, this.xg / 2.0f);
        e(canvas);
        canvas.translate(0.0f, (-this.xg) / 2.0f);
        wX.a(canvas, this.xa, this.xb, this.wN);
    }

    private void e(Canvas canvas) {
        float f = (-this.xb) - this.xf;
        float f2 = this.xb + this.wW + (this.xg / 2.0f);
        boolean z = this.xa.width() - (2.0f * f2) > 0.0f;
        boolean z2 = this.xa.height() - (2.0f * f2) > 0.0f;
        int iSave = canvas.save();
        canvas.translate(this.xa.left + f2, this.xa.top + f2);
        canvas.drawPath(this.xc, this.wY);
        if (z) {
            canvas.drawRect(0.0f, f, this.xa.width() - (2.0f * f2), -this.xb, this.wZ);
        }
        canvas.restoreToCount(iSave);
        int iSave2 = canvas.save();
        canvas.translate(this.xa.right - f2, this.xa.bottom - f2);
        canvas.rotate(180.0f);
        canvas.drawPath(this.xc, this.wY);
        if (z) {
            canvas.drawRect(0.0f, f, this.xa.width() - (2.0f * f2), this.xf + (-this.xb), this.wZ);
        }
        canvas.restoreToCount(iSave2);
        int iSave3 = canvas.save();
        canvas.translate(this.xa.left + f2, this.xa.bottom - f2);
        canvas.rotate(270.0f);
        canvas.drawPath(this.xc, this.wY);
        if (z2) {
            canvas.drawRect(0.0f, f, this.xa.height() - (2.0f * f2), -this.xb, this.wZ);
        }
        canvas.restoreToCount(iSave3);
        int iSave4 = canvas.save();
        canvas.translate(this.xa.right - f2, this.xa.top + f2);
        canvas.rotate(90.0f);
        canvas.drawPath(this.xc, this.wY);
        if (z2) {
            canvas.drawRect(0.0f, f, this.xa.height() - (2.0f * f2), -this.xb, this.wZ);
        }
        canvas.restoreToCount(iSave4);
    }

    private void eR() {
        RectF rectF = new RectF(-this.xb, -this.xb, this.xb, this.xb);
        RectF rectF2 = new RectF(rectF);
        rectF2.inset(-this.xf, -this.xf);
        if (this.xc == null) {
            this.xc = new Path();
        } else {
            this.xc.reset();
        }
        this.xc.setFillType(Path.FillType.EVEN_ODD);
        this.xc.moveTo(-this.xb, 0.0f);
        this.xc.rLineTo(-this.xf, 0.0f);
        this.xc.arcTo(rectF2, 180.0f, 90.0f, false);
        this.xc.arcTo(rectF, 270.0f, -90.0f, false);
        this.xc.close();
        this.wY.setShader(new RadialGradient(0.0f, 0.0f, this.xb + this.xf, new int[]{this.xi, this.xi, this.xj}, new float[]{0.0f, this.xb / (this.xb + this.xf), 1.0f}, Shader.TileMode.CLAMP));
        this.wZ.setShader(new LinearGradient(0.0f, (-this.xb) + this.xf, 0.0f, (-this.xb) - this.xf, new int[]{this.xi, this.xi, this.xj}, new float[]{0.0f, 0.5f, 1.0f}, Shader.TileMode.CLAMP));
        this.wZ.setAntiAlias(false);
    }

    private void e(Rect rect) {
        float f = this.xe * 1.5f;
        this.xa.set(rect.left + this.xe, rect.top + f, rect.right - this.xe, rect.bottom - f);
        eR();
    }

    void f(Rect rect) {
        getPadding(rect);
    }

    float eS() {
        return (Math.max(this.xe, this.xb + this.wW + (this.xe / 2.0f)) * 2.0f) + ((this.xe + this.wW) * 2.0f);
    }

    float eT() {
        return (Math.max(this.xe, this.xb + this.wW + ((this.xe * 1.5f) / 2.0f)) * 2.0f) + (((this.xe * 1.5f) + this.wW) * 2.0f);
    }
}
