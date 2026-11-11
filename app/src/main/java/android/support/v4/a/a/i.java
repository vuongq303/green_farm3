package android.support.v4.a.a;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

/* JADX INFO: compiled from: DrawableWrapperApi14.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class i extends Drawable.ConstantState {
    Drawable.ConstantState fI;
    ColorStateList fJ;
    PorterDuff.Mode fK;
    int mChangingConfigurations;

    @Override // android.graphics.drawable.Drawable.ConstantState
    public abstract Drawable newDrawable(Resources resources);

    i(i iVar, Resources resources) {
        this.fJ = null;
        this.fK = h.fC;
        if (iVar != null) {
            this.mChangingConfigurations = iVar.mChangingConfigurations;
            this.fI = iVar.fI;
            this.fJ = iVar.fJ;
            this.fK = iVar.fK;
        }
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public Drawable newDrawable() {
        return newDrawable(null);
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public int getChangingConfigurations() {
        return (this.fI != null ? this.fI.getChangingConfigurations() : 0) | this.mChangingConfigurations;
    }

    boolean canConstantState() {
        return this.fI != null;
    }
}
