package android.support.v7.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/* JADX INFO: compiled from: Toolbar.java */
/* JADX INFO: loaded from: classes.dex */
public class dd extends android.support.v7.app.b {
    int yA;

    public dd(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.yA = 0;
    }

    public dd(int i, int i2) {
        super(i, i2);
        this.yA = 0;
        this.gravity = 8388627;
    }

    public dd(dd ddVar) {
        super((android.support.v7.app.b) ddVar);
        this.yA = 0;
        this.yA = ddVar.yA;
    }

    public dd(android.support.v7.app.b bVar) {
        super(bVar);
        this.yA = 0;
    }

    public dd(ViewGroup.MarginLayoutParams marginLayoutParams) {
        super(marginLayoutParams);
        this.yA = 0;
        c(marginLayoutParams);
    }

    public dd(ViewGroup.LayoutParams layoutParams) {
        super(layoutParams);
        this.yA = 0;
    }

    void c(ViewGroup.MarginLayoutParams marginLayoutParams) {
        this.leftMargin = marginLayoutParams.leftMargin;
        this.topMargin = marginLayoutParams.topMargin;
        this.rightMargin = marginLayoutParams.rightMargin;
        this.bottomMargin = marginLayoutParams.bottomMargin;
    }
}
