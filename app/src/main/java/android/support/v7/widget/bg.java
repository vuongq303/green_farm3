package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.RectF;

/* JADX INFO: compiled from: CardViewGingerbread.java */
/* JADX INFO: loaded from: classes.dex */
class bg implements bi {
    final RectF vg = new RectF();

    bg() {
    }

    @Override // android.support.v7.widget.bi
    public void ex() {
        cn.wX = new bh(this);
    }

    @Override // android.support.v7.widget.bi
    public void a(bf bfVar, Context context, ColorStateList colorStateList, float f, float f2, float f3) {
        cn cnVarA = a(context, colorStateList, f, f2, f3);
        cnVarA.P(bfVar.eu());
        bfVar.m(cnVarA);
        e(bfVar);
    }

    private cn a(Context context, ColorStateList colorStateList, float f, float f2, float f3) {
        return new cn(context.getResources(), colorStateList, f, f2, f3);
    }

    public void e(bf bfVar) {
        Rect rect = new Rect();
        g(bfVar).f(rect);
        bfVar.l((int) Math.ceil(b(bfVar)), (int) Math.ceil(c(bfVar)));
        bfVar.b(rect.left, rect.top, rect.right, rect.bottom);
    }

    @Override // android.support.v7.widget.bi
    public float b(bf bfVar) {
        return g(bfVar).eS();
    }

    @Override // android.support.v7.widget.bi
    public float c(bf bfVar) {
        return g(bfVar).eT();
    }

    private cn g(bf bfVar) {
        return (cn) bfVar.ev();
    }
}
