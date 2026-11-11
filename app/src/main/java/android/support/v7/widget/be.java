package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;

/* JADX INFO: compiled from: CardViewApi21.java */
/* JADX INFO: loaded from: classes.dex */
class be implements bi {
    be() {
    }

    @Override // android.support.v7.widget.bi
    public void a(bf bfVar, Context context, ColorStateList colorStateList, float f, float f2, float f3) {
        bfVar.m(new cm(colorStateList, f));
        View viewEw = bfVar.ew();
        viewEw.setClipToOutline(true);
        viewEw.setElevation(f2);
        a(bfVar, f3);
    }

    @Override // android.support.v7.widget.bi
    public void ex() {
    }

    public void a(bf bfVar, float f) {
        f(bfVar).a(f, bfVar.et(), bfVar.eu());
        e(bfVar);
    }

    public float a(bf bfVar) {
        return f(bfVar).eQ();
    }

    @Override // android.support.v7.widget.bi
    public float b(bf bfVar) {
        return d(bfVar) * 2.0f;
    }

    @Override // android.support.v7.widget.bi
    public float c(bf bfVar) {
        return d(bfVar) * 2.0f;
    }

    public float d(bf bfVar) {
        return f(bfVar).getRadius();
    }

    public void e(bf bfVar) {
        if (!bfVar.et()) {
            bfVar.b(0, 0, 0, 0);
            return;
        }
        float fA = a(bfVar);
        float fD = d(bfVar);
        int iCeil = (int) Math.ceil(cn.b(fA, fD, bfVar.eu()));
        int iCeil2 = (int) Math.ceil(cn.a(fA, fD, bfVar.eu()));
        bfVar.b(iCeil, iCeil2, iCeil, iCeil2);
    }

    private cm f(bf bfVar) {
        return (cm) bfVar.ev();
    }
}
