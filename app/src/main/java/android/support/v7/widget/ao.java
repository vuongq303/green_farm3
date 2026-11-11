package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.widget.RadioButton;

/* JADX INFO: compiled from: AppCompatRadioButton.java */
/* JADX INFO: loaded from: classes.dex */
public class ao extends RadioButton implements android.support.v4.widget.y {
    private final ab tx;

    public ao(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.radioButtonStyle);
    }

    public ao(Context context, AttributeSet attributeSet, int i) {
        super(cx.t(context), attributeSet, i);
        this.tx = new ab(this);
        this.tx.a(attributeSet, i);
    }

    @Override // android.widget.CompoundButton
    public void setButtonDrawable(Drawable drawable) {
        super.setButtonDrawable(drawable);
        if (this.tx != null) {
            this.tx.ee();
        }
    }

    @Override // android.widget.CompoundButton
    public void setButtonDrawable(int i) {
        setButtonDrawable(android.support.v7.a.a.b.a(getContext(), i));
    }

    @Override // android.widget.CompoundButton, android.widget.TextView
    public int getCompoundPaddingLeft() {
        int compoundPaddingLeft = super.getCompoundPaddingLeft();
        return this.tx != null ? this.tx.ao(compoundPaddingLeft) : compoundPaddingLeft;
    }

    @Override // android.support.v4.widget.y
    public void b(ColorStateList colorStateList) {
        if (this.tx != null) {
            this.tx.b(colorStateList);
        }
    }

    @Override // android.support.v4.widget.y
    public void b(PorterDuff.Mode mode) {
        if (this.tx != null) {
            this.tx.b(mode);
        }
    }
}
