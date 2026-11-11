package android.support.v4.a.a;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/* JADX INFO: compiled from: DrawableWrapperApi19.java */
/* JADX INFO: loaded from: classes.dex */
class k extends h {
    k(Drawable drawable) {
        super(drawable);
    }

    k(i iVar, Resources resources) {
        super(iVar, resources);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAutoMirrored(boolean z) {
        this.mDrawable.setAutoMirrored(z);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isAutoMirrored() {
        return this.mDrawable.isAutoMirrored();
    }

    @Override // android.support.v4.a.a.h
    i au() {
        return new l(this.fG, null);
    }
}
