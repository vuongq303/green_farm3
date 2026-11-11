package android.support.v7.widget;

import android.graphics.drawable.Drawable;
import android.view.View;

/* JADX INFO: compiled from: CardView.java */
/* JADX INFO: loaded from: classes.dex */
class bd implements bf {
    private Drawable ve;
    final /* synthetic */ CardView vf;

    bd(CardView cardView) {
        this.vf = cardView;
    }

    @Override // android.support.v7.widget.bf
    public void m(Drawable drawable) {
        this.ve = drawable;
        this.vf.setBackgroundDrawable(drawable);
    }

    @Override // android.support.v7.widget.bf
    public boolean et() {
        return this.vf.et();
    }

    @Override // android.support.v7.widget.bf
    public boolean eu() {
        return this.vf.eu();
    }

    @Override // android.support.v7.widget.bf
    public void b(int i, int i2, int i3, int i4) {
        this.vf.vc.set(i, i2, i3, i4);
        super/*android.widget.FrameLayout*/.setPadding(this.vf.vb.left + i, this.vf.vb.top + i2, this.vf.vb.right + i3, this.vf.vb.bottom + i4);
    }

    @Override // android.support.v7.widget.bf
    public void l(int i, int i2) {
        if (i > this.vf.uZ) {
            super/*android.widget.FrameLayout*/.setMinimumWidth(i);
        }
        if (i2 > this.vf.va) {
            super/*android.widget.FrameLayout*/.setMinimumHeight(i2);
        }
    }

    @Override // android.support.v7.widget.bf
    public Drawable ev() {
        return this.ve;
    }

    @Override // android.support.v7.widget.bf
    public View ew() {
        return this.vf;
    }
}
