package android.support.v7.c.a;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v7.widget.bb;
import android.support.v7.widget.bt;
import android.support.v7.widget.di;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

/* JADX INFO: compiled from: ActionMenuItemView.java */
/* JADX INFO: loaded from: classes.dex */
public class b extends bb implements al, android.support.v7.widget.r, View.OnClickListener {
    private Drawable jJ;
    private CharSequence jp;
    w pb;
    u pc;
    private bt pd;
    d pe;
    private boolean pf;
    private boolean pg;
    private int ph;
    private int pi;
    private int pj;

    @Override // android.widget.TextView, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.pf = cD();
        cG();
    }

    private boolean cD() {
        Configuration configuration = getContext().getResources().getConfiguration();
        int i = configuration.screenWidthDp;
        return i >= 480 || (i >= 640 && configuration.screenHeightDp >= 480) || configuration.orientation == 2;
    }

    @Override // android.widget.TextView, android.view.View
    public void setPadding(int i, int i2, int i3, int i4) {
        this.pi = i;
        super.setPadding(i, i2, i3, i4);
    }

    @Override // android.support.v7.c.a.al
    public w cE() {
        return this.pb;
    }

    @Override // android.support.v7.c.a.al
    public void a(w wVar, int i) {
        this.pb = wVar;
        setIcon(wVar.getIcon());
        setTitle(wVar.a(this));
        setId(wVar.getItemId());
        setVisibility(wVar.isVisible() ? 0 : 8);
        setEnabled(wVar.isEnabled());
        if (wVar.hasSubMenu() && this.pd == null) {
            this.pd = new c(this);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.pb.hasSubMenu() && this.pd != null && this.pd.onTouch(this, motionEvent)) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (this.pc != null) {
            this.pc.a(this.pb);
        }
    }

    public void a(u uVar) {
        this.pc = uVar;
    }

    public void a(d dVar) {
        this.pe = dVar;
    }

    @Override // android.support.v7.c.a.al
    public boolean cF() {
        return true;
    }

    private void cG() {
        boolean z = false;
        boolean z2 = !TextUtils.isEmpty(this.jp);
        if (this.jJ == null || (this.pb.dv() && (this.pf || this.pg))) {
            z = true;
        }
        boolean z3 = z2 & z;
        setText(z3 ? this.jp : null);
        CharSequence contentDescription = this.pb.getContentDescription();
        if (TextUtils.isEmpty(contentDescription)) {
            setContentDescription(z3 ? null : this.pb.getTitle());
        } else {
            setContentDescription(contentDescription);
        }
        CharSequence tooltipText = this.pb.getTooltipText();
        if (TextUtils.isEmpty(tooltipText)) {
            di.a(this, z3 ? null : this.pb.getTitle());
        } else {
            di.a(this, tooltipText);
        }
    }

    public void setIcon(Drawable drawable) {
        this.jJ = drawable;
        if (drawable != null) {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            if (intrinsicWidth > this.pj) {
                float f = this.pj / intrinsicWidth;
                intrinsicWidth = this.pj;
                intrinsicHeight = (int) (intrinsicHeight * f);
            }
            if (intrinsicHeight > this.pj) {
                float f2 = this.pj / intrinsicHeight;
                intrinsicHeight = this.pj;
                intrinsicWidth = (int) (intrinsicWidth * f2);
            }
            drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
        }
        setCompoundDrawables(drawable, null, null, null);
        cG();
    }

    public boolean hasText() {
        return !TextUtils.isEmpty(getText());
    }

    public void setTitle(CharSequence charSequence) {
        this.jp = charSequence;
        cG();
    }

    @Override // android.support.v7.widget.r
    public boolean cH() {
        return hasText() && this.pb.getIcon() == null;
    }

    @Override // android.support.v7.widget.r
    public boolean cI() {
        return hasText();
    }

    @Override // android.widget.TextView, android.view.View
    protected void onMeasure(int i, int i2) {
        boolean zHasText = hasText();
        if (zHasText && this.pi >= 0) {
            super.setPadding(this.pi, getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        int measuredWidth = getMeasuredWidth();
        int iMin = mode == Integer.MIN_VALUE ? Math.min(size, this.ph) : this.ph;
        if (mode != 1073741824 && this.ph > 0 && measuredWidth < iMin) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(iMin, 1073741824), i2);
        }
        if (!zHasText && this.jJ != null) {
            super.setPadding((getMeasuredWidth() - this.jJ.getBounds().width()) / 2, getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        super.onRestoreInstanceState(null);
    }
}
