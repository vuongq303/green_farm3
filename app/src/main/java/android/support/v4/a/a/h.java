package android.support.v4.a.a;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;

/* JADX INFO: compiled from: DrawableWrapperApi14.java */
/* JADX INFO: loaded from: classes.dex */
class h extends Drawable implements Drawable.Callback, g, o {
    static final PorterDuff.Mode fC = PorterDuff.Mode.SRC_IN;
    private int fD;
    private PorterDuff.Mode fE;
    private boolean fF;
    i fG;
    private boolean fH;
    Drawable mDrawable;

    h(i iVar, Resources resources) {
        this.fG = iVar;
        a(resources);
    }

    h(Drawable drawable) {
        this.fG = au();
        e(drawable);
    }

    private void a(Resources resources) {
        if (this.fG != null && this.fG.fI != null) {
            e(a(this.fG.fI, resources));
        }
    }

    protected Drawable a(Drawable.ConstantState constantState, Resources resources) {
        return constantState.newDrawable(resources);
    }

    @Override // android.graphics.drawable.Drawable
    public void jumpToCurrentState() {
        this.mDrawable.jumpToCurrentState();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        this.mDrawable.draw(canvas);
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        if (this.mDrawable != null) {
            this.mDrawable.setBounds(rect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setChangingConfigurations(int i) {
        this.mDrawable.setChangingConfigurations(i);
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return (this.fG != null ? this.fG.getChangingConfigurations() : 0) | super.getChangingConfigurations() | this.mDrawable.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z) {
        this.mDrawable.setDither(z);
    }

    @Override // android.graphics.drawable.Drawable
    public void setFilterBitmap(boolean z) {
        this.mDrawable.setFilterBitmap(z);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mDrawable.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mDrawable.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        ColorStateList colorStateList;
        if (av() && this.fG != null) {
            colorStateList = this.fG.fJ;
        } else {
            colorStateList = null;
        }
        return (colorStateList != null && colorStateList.isStateful()) || this.mDrawable.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setState(int[] iArr) {
        return a(iArr) || this.mDrawable.setState(iArr);
    }

    @Override // android.graphics.drawable.Drawable
    public int[] getState() {
        return this.mDrawable.getState();
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable getCurrent() {
        return this.mDrawable.getCurrent();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        return super.setVisible(z, z2) || this.mDrawable.setVisible(z, z2);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return this.mDrawable.getOpacity();
    }

    @Override // android.graphics.drawable.Drawable
    public Region getTransparentRegion() {
        return this.mDrawable.getTransparentRegion();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mDrawable.getIntrinsicWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mDrawable.getIntrinsicHeight();
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumWidth() {
        return this.mDrawable.getMinimumWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumHeight() {
        return this.mDrawable.getMinimumHeight();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(Rect rect) {
        return this.mDrawable.getPadding(rect);
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        if (this.fG == null || !this.fG.canConstantState()) {
            return null;
        }
        this.fG.mChangingConfigurations = getChangingConfigurations();
        return this.fG;
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.fH && super.mutate() == this) {
            this.fG = au();
            if (this.mDrawable != null) {
                this.mDrawable.mutate();
            }
            if (this.fG != null) {
                this.fG.fI = this.mDrawable != null ? this.mDrawable.getConstantState() : null;
            }
            this.fH = true;
        }
        return this;
    }

    i au() {
        return new j(this.fG, null);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        scheduleSelf(runnable, j);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        unscheduleSelf(runnable);
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onLevelChange(int i) {
        return this.mDrawable.setLevel(i);
    }

    @Override // android.graphics.drawable.Drawable, android.support.v4.a.a.o
    public void setTint(int i) {
        setTintList(ColorStateList.valueOf(i));
    }

    @Override // android.graphics.drawable.Drawable, android.support.v4.a.a.o
    public void setTintList(ColorStateList colorStateList) {
        this.fG.fJ = colorStateList;
        a(getState());
    }

    @Override // android.graphics.drawable.Drawable, android.support.v4.a.a.o
    public void setTintMode(PorterDuff.Mode mode) {
        this.fG.fK = mode;
        a(getState());
    }

    private boolean a(int[] iArr) {
        if (!av()) {
            return false;
        }
        ColorStateList colorStateList = this.fG.fJ;
        PorterDuff.Mode mode = this.fG.fK;
        if (colorStateList != null && mode != null) {
            int colorForState = colorStateList.getColorForState(iArr, colorStateList.getDefaultColor());
            if (this.fF && colorForState == this.fD && mode == this.fE) {
                return false;
            }
            setColorFilter(colorForState, mode);
            this.fD = colorForState;
            this.fE = mode;
            this.fF = true;
            return true;
        }
        this.fF = false;
        clearColorFilter();
        return false;
    }

    @Override // android.support.v4.a.a.g
    public final Drawable at() {
        return this.mDrawable;
    }

    @Override // android.support.v4.a.a.g
    public final void e(Drawable drawable) {
        if (this.mDrawable != null) {
            this.mDrawable.setCallback(null);
        }
        this.mDrawable = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            setVisible(drawable.isVisible(), true);
            setState(drawable.getState());
            setLevel(drawable.getLevel());
            setBounds(drawable.getBounds());
            if (this.fG != null) {
                this.fG.fI = drawable.getConstantState();
            }
        }
        invalidateSelf();
    }

    protected boolean av() {
        return true;
    }
}
