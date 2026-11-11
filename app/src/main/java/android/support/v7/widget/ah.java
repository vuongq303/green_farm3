package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.widget.EditText;

/* JADX INFO: compiled from: AppCompatEditText.java */
/* JADX INFO: loaded from: classes.dex */
public class ah extends EditText implements android.support.v4.view.z {
    private final x tq;
    private final az tr;

    public ah(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.editTextStyle);
    }

    public ah(Context context, AttributeSet attributeSet, int i) {
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

    @Override // android.widget.TextView
    public void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        if (this.tr != null) {
            this.tr.n(context, i);
        }
    }
}
