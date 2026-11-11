package android.support.v4.a.a;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/* JADX INFO: compiled from: DrawableWrapperApi21.java */
/* JADX INFO: loaded from: classes.dex */
class n extends i {
    n(i iVar, Resources resources) {
        super(iVar, resources);
    }

    @Override // android.support.v4.a.a.i, android.graphics.drawable.Drawable.ConstantState
    public Drawable newDrawable(Resources resources) {
        return new m(this, resources);
    }
}
