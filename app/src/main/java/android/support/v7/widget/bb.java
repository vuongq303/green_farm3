package android.support.v7.widget;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/* JADX INFO: compiled from: AppCompatTextView.java */
/* JADX INFO: loaded from: classes.dex */
public class bb extends TextView implements android.support.v4.view.z {
    private final x tq;
    private final az tr;

    public bb(Context context) {
        this(context, null);
    }

    public bb(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.textViewStyle);
    }

    public bb(Context context, AttributeSet attributeSet, int i) {
        super(cx.t(context), attributeSet, i);
        this.tq = new x(this);
        this.tq.a(attributeSet, i);
        this.tr = az.a(this);
        this.tr.a(attributeSet, i);
        this.tr.em();
    }

    @Override // android.view.View
    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        if (this.tq != null) {
            this.tq.an(i);
        }
    }

    @Override // android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        if (this.tq != null) {
            this.tq.i(drawable);
        }
    }

    @Override // android.support.v4.view.z
    public void a(ColorStateList colorStateList) {
        if (this.tq != null) {
            this.tq.a(colorStateList);
        }
    }

    @Override // android.support.v4.view.z
    public ColorStateList aQ() {
        if (this.tq != null) {
            return this.tq.aQ();
        }
        return null;
    }

    @Override // android.support.v4.view.z
    public void a(PorterDuff.Mode mode) {
        if (this.tq != null) {
            this.tq.a(mode);
        }
    }

    @Override // android.support.v4.view.z
    public PorterDuff.Mode aR() {
        if (this.tq != null) {
            return this.tq.aR();
        }
        return null;
    }

    @Override // android.widget.TextView
    public void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        if (this.tr != null) {
            this.tr.n(context, i);
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.tq != null) {
            this.tq.ec();
        }
        if (this.tr != null) {
            this.tr.em();
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.tr != null) {
            this.tr.onLayout(z, i, i2, i3, i4);
        }
    }

    @Override // android.widget.TextView
    public void setTextSize(int i, float f) {
        if (Build.VERSION.SDK_INT >= 26) {
            super.setTextSize(i, f);
        } else if (this.tr != null) {
            this.tr.setTextSize(i, f);
        }
    }

    @Override // android.widget.TextView
    protected void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        if (this.tr != null && Build.VERSION.SDK_INT < 26 && this.tr.eo()) {
            this.tr.en();
        }
    }

    @Override // android.widget.TextView
    public void setAutoSizeTextTypeWithDefaults(int i) {
        if (Build.VERSION.SDK_INT >= 26) {
            super.setAutoSizeTextTypeWithDefaults(i);
        } else if (this.tr != null) {
            this.tr.setAutoSizeTextTypeWithDefaults(i);
        }
    }

    @Override // android.widget.TextView
    public void setAutoSizeTextTypeUniformWithConfiguration(int i, int i2, int i3, int i4) {
        if (Build.VERSION.SDK_INT >= 26) {
            super.setAutoSizeTextTypeUniformWithConfiguration(i, i2, i3, i4);
        } else if (this.tr != null) {
            this.tr.setAutoSizeTextTypeUniformWithConfiguration(i, i2, i3, i4);
        }
    }

    @Override // android.widget.TextView
    public void setAutoSizeTextTypeUniformWithPresetSizes(int[] iArr, int i) {
        if (Build.VERSION.SDK_INT >= 26) {
            super.setAutoSizeTextTypeUniformWithPresetSizes(iArr, i);
        } else if (this.tr != null) {
            this.tr.setAutoSizeTextTypeUniformWithPresetSizes(iArr, i);
        }
    }

    @Override // android.widget.TextView
    public int getAutoSizeTextType() {
        if (Build.VERSION.SDK_INT >= 26) {
            return super.getAutoSizeTextType() == 1 ? 1 : 0;
        }
        if (this.tr != null) {
            return this.tr.getAutoSizeTextType();
        }
        return 0;
    }

    @Override // android.widget.TextView
    public int getAutoSizeStepGranularity() {
        if (Build.VERSION.SDK_INT >= 26) {
            return super.getAutoSizeStepGranularity();
        }
        if (this.tr != null) {
            return this.tr.getAutoSizeStepGranularity();
        }
        return -1;
    }

    @Override // android.widget.TextView
    public int getAutoSizeMinTextSize() {
        if (Build.VERSION.SDK_INT >= 26) {
            return super.getAutoSizeMinTextSize();
        }
        if (this.tr != null) {
            return this.tr.getAutoSizeMinTextSize();
        }
        return -1;
    }

    @Override // android.widget.TextView
    public int getAutoSizeMaxTextSize() {
        if (Build.VERSION.SDK_INT >= 26) {
            return super.getAutoSizeMaxTextSize();
        }
        if (this.tr != null) {
            return this.tr.getAutoSizeMaxTextSize();
        }
        return -1;
    }

    @Override // android.widget.TextView
    public int[] getAutoSizeTextAvailableSizes() {
        if (Build.VERSION.SDK_INT >= 26) {
            return super.getAutoSizeTextAvailableSizes();
        }
        if (this.tr != null) {
            return this.tr.getAutoSizeTextAvailableSizes();
        }
        return new int[0];
    }
}
