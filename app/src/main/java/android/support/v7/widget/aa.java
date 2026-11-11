package android.support.v7.widget;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckedTextView;

/* JADX INFO: compiled from: AppCompatCheckedTextView.java */
/* JADX INFO: loaded from: classes.dex */
public class aa extends CheckedTextView {
    private static final int[] pY = {R.attr.checkMark};
    private final az tr;

    public aa(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.checkedTextViewStyle);
    }

    public aa(Context context, AttributeSet attributeSet, int i) {
        super(cx.t(context), attributeSet, i);
        this.tr = az.a(this);
        this.tr.a(attributeSet, i);
        this.tr.em();
        da daVarA = da.a(getContext(), attributeSet, pY, i, 0);
        setCheckMarkDrawable(daVarA.getDrawable(0));
        daVarA.recycle();
    }

    @Override // android.widget.CheckedTextView
    public void setCheckMarkDrawable(int i) {
        setCheckMarkDrawable(android.support.v7.a.a.b.a(getContext(), i));
    }

    @Override // android.widget.TextView
    public void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        if (this.tr != null) {
            this.tr.n(context, i);
        }
    }

    @Override // android.widget.CheckedTextView, android.widget.TextView, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.tr != null) {
            this.tr.em();
        }
    }
}
