package android.support.v7.widget;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.MultiAutoCompleteTextView;

/* JADX INFO: compiled from: AppCompatMultiAutoCompleteTextView.java */
/* JADX INFO: loaded from: classes.dex */
public class ak extends MultiAutoCompleteTextView implements android.support.v4.view.z {
    private static final int[] pY = {R.attr.popupBackground};
    private final x tq;
    private final az tr;

    public ak(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, android.support.v7.appcompat.R.attr.autoCompleteTextViewStyle);
    }

    public ak(Context context, AttributeSet attributeSet, int i) {
        super(cx.t(context), attributeSet, i);
        da daVarA = da.a(getContext(), attributeSet, pY, i, 0);
        if (daVarA.hasValue(0)) {
            setDropDownBackgroundDrawable(daVarA.getDrawable(0));
        }
        daVarA.recycle();
        this.tq = new x(this);
        this.tq.a(attributeSet, i);
        this.tr = az.a(this);
        this.tr.a(attributeSet, i);
        this.tr.em();
    }

    @Override // android.widget.AutoCompleteTextView
    public void setDropDownBackgroundResource(int i) {
        setDropDownBackgroundDrawable(android.support.v7.a.a.b.a(getContext(), i));
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
