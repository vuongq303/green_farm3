package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.widget.SeekBar;

/* JADX INFO: compiled from: AppCompatSeekBarHelper.java */
/* JADX INFO: loaded from: classes.dex */
class ar extends an {
    private final SeekBar uh;
    private Drawable ui;
    private ColorStateList uj;
    private PorterDuff.Mode uk;
    private boolean ul;
    private boolean um;

    ar(SeekBar seekBar) {
        super(seekBar);
        this.uj = null;
        this.uk = null;
        this.ul = false;
        this.um = false;
        this.uh = seekBar;
    }

    @Override // android.support.v7.widget.an
    void a(AttributeSet attributeSet, int i) {
        super.a(attributeSet, i);
        da daVarA = da.a(this.uh.getContext(), attributeSet, R.styleable.AppCompatSeekBar, i, 0);
        Drawable drawableAu = daVarA.au(R.styleable.AppCompatSeekBar_android_thumb);
        if (drawableAu != null) {
            this.uh.setThumb(drawableAu);
        }
        setTickMark(daVarA.getDrawable(R.styleable.AppCompatSeekBar_tickMark));
        if (daVarA.hasValue(R.styleable.AppCompatSeekBar_tickMarkTintMode)) {
            this.uk = bp.d(daVarA.getInt(R.styleable.AppCompatSeekBar_tickMarkTintMode, -1), this.uk);
            this.um = true;
        }
        if (daVarA.hasValue(R.styleable.AppCompatSeekBar_tickMarkTint)) {
            this.uj = daVarA.getColorStateList(R.styleable.AppCompatSeekBar_tickMarkTint);
            this.ul = true;
        }
        daVarA.recycle();
        ek();
    }

    void setTickMark(Drawable drawable) {
        if (this.ui != null) {
            this.ui.setCallback(null);
        }
        this.ui = drawable;
        if (drawable != null) {
            drawable.setCallback(this.uh);
            android.support.v4.a.a.a.b(drawable, android.support.v4.view.aa.f(this.uh));
            if (drawable.isStateful()) {
                drawable.setState(this.uh.getDrawableState());
            }
            ek();
        }
        this.uh.invalidate();
    }

    private void ek() {
        if (this.ui != null) {
            if (this.ul || this.um) {
                this.ui = android.support.v4.a.a.a.d(this.ui.mutate());
                if (this.ul) {
                    android.support.v4.a.a.a.a(this.ui, this.uj);
                }
                if (this.um) {
                    android.support.v4.a.a.a.a(this.ui, this.uk);
                }
                if (this.ui.isStateful()) {
                    this.ui.setState(this.uh.getDrawableState());
                }
            }
        }
    }

    void jumpDrawablesToCurrentState() {
        if (this.ui != null) {
            this.ui.jumpToCurrentState();
        }
    }

    void drawableStateChanged() {
        Drawable drawable = this.ui;
        if (drawable != null && drawable.isStateful() && drawable.setState(this.uh.getDrawableState())) {
            this.uh.invalidateDrawable(drawable);
        }
    }

    void a(Canvas canvas) {
        int max;
        if (this.ui != null && (max = this.uh.getMax()) > 1) {
            int intrinsicWidth = this.ui.getIntrinsicWidth();
            int intrinsicHeight = this.ui.getIntrinsicHeight();
            int i = intrinsicWidth >= 0 ? intrinsicWidth / 2 : 1;
            int i2 = intrinsicHeight >= 0 ? intrinsicHeight / 2 : 1;
            this.ui.setBounds(-i, -i2, i, i2);
            float width = ((this.uh.getWidth() - this.uh.getPaddingLeft()) - this.uh.getPaddingRight()) / max;
            int iSave = canvas.save();
            canvas.translate(this.uh.getPaddingLeft(), this.uh.getHeight() / 2);
            for (int i3 = 0; i3 <= max; i3++) {
                this.ui.draw(canvas);
                canvas.translate(width, 0.0f);
            }
            canvas.restoreToCount(iSave);
        }
    }
}
