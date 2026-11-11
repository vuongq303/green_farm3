package android.support.v7.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;

/* JADX INFO: compiled from: AppCompatSpinner.java */
/* JADX INFO: loaded from: classes.dex */
class av extends by {
    ListAdapter jO;
    final /* synthetic */ as ut;
    private CharSequence uw;
    private final Rect ux;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public av(as asVar, Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.ut = asVar;
        this.ux = new Rect();
        setAnchorView(asVar);
        setModal(true);
        setPromptPosition(0);
        setOnItemClickListener(new aw(this, asVar));
    }

    @Override // android.support.v7.widget.by
    public void setAdapter(ListAdapter listAdapter) {
        super.setAdapter(listAdapter);
        this.jO = listAdapter;
    }

    public CharSequence getHintText() {
        return this.uw;
    }

    public void l(CharSequence charSequence) {
        this.uw = charSequence;
    }

    void el() {
        int i;
        int width;
        Drawable background = getBackground();
        if (background != null) {
            background.getPadding(this.ut.iH);
            i = du.N(this.ut) ? this.ut.iH.right : -this.ut.iH.left;
        } else {
            Rect rect = this.ut.iH;
            this.ut.iH.right = 0;
            rect.left = 0;
            i = 0;
        }
        int paddingLeft = this.ut.getPaddingLeft();
        int paddingRight = this.ut.getPaddingRight();
        int width2 = this.ut.getWidth();
        if (this.ut.ur != -2) {
            if (this.ut.ur != -1) {
                setContentWidth(this.ut.ur);
            } else {
                setContentWidth((width2 - paddingLeft) - paddingRight);
            }
        } else {
            int iA = this.ut.a((SpinnerAdapter) this.jO, getBackground());
            int i2 = (this.ut.getContext().getResources().getDisplayMetrics().widthPixels - this.ut.iH.left) - this.ut.iH.right;
            if (iA <= i2) {
                i2 = iA;
            }
            setContentWidth(Math.max(i2, (width2 - paddingLeft) - paddingRight));
        }
        if (du.N(this.ut)) {
            width = ((width2 - paddingRight) - getWidth()) + i;
        } else {
            width = i + paddingLeft;
        }
        setHorizontalOffset(width);
    }

    @Override // android.support.v7.widget.by, android.support.v7.c.a.ao
    public void show() {
        ViewTreeObserver viewTreeObserver;
        boolean zIsShowing = isShowing();
        el();
        setInputMethodMode(2);
        super.show();
        getListView().setChoiceMode(1);
        setSelection(this.ut.getSelectedItemPosition());
        if (!zIsShowing && (viewTreeObserver = this.ut.getViewTreeObserver()) != null) {
            ax axVar = new ax(this);
            viewTreeObserver.addOnGlobalLayoutListener(axVar);
            setOnDismissListener(new ay(this, axVar));
        }
    }

    boolean F(View view) {
        return android.support.v4.view.aa.isAttachedToWindow(view) && view.getGlobalVisibleRect(this.ux);
    }
}
