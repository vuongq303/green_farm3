package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.widget.ImageButton;

/* JADX INFO: compiled from: AppCompatImageButton.java */
/* JADX INFO: loaded from: classes.dex */
public class ai extends ImageButton implements android.support.v4.view.z, android.support.v4.widget.z {
    private final aj tU;
    private final x tq;

    public ai(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.imageButtonStyle);
    }

    public ai(Context context, AttributeSet attributeSet, int i) {
        super(cx.t(context), attributeSet, i);
        this.tq = new x(this);
        this.tq.a(attributeSet, i);
        this.tU = new aj(this);
        this.tU.a(attributeSet, i);
    }

    @Override // android.widget.ImageView
    public void setImageResource(int i) {
        this.tU.setImageResource(i);
    }

    @Override // android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        if (this.tU != null) {
            this.tU.eh();
        }
    }

    @Override // android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        if (this.tU != null) {
            this.tU.eh();
        }
    }

    @Override // android.widget.ImageView
    public void setImageIcon(Icon icon) {
        super.setImageIcon(icon);
        if (this.tU != null) {
            this.tU.eh();
        }
    }

    @Override // android.widget.ImageView
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        if (this.tU != null) {
            this.tU.eh();
        }
    }

    @Override // android.view.View
    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        if (this.tq != null) {
            this.tq.an(i);
        }
    }

    @Override // android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        if (this.tq != null) {
            this.tq.i(drawable);
        }
    }

    @Override // android.support.v4.view.z
    public void a(ColorStateList colorStateList) {
        if (this.tq != null) {
            this.tq.a(colorStateList);
        }
    }

    @Override // android.support.v4.view.z
    public ColorStateList aQ() {
        if (this.tq != null) {
            return this.tq.aQ();
        }
        return null;
    }

    @Override // android.support.v4.view.z
    public void a(PorterDuff.Mode mode) {
        if (this.tq != null) {
            this.tq.a(mode);
        }
    }

    @Override // android.support.v4.view.z
    public PorterDuff.Mode aR() {
        if (this.tq != null) {
            return this.tq.aR();
        }
        return null;
    }

    @Override // android.support.v4.widget.z
    public void c(ColorStateList colorStateList) {
        if (this.tU != null) {
            this.tU.c(colorStateList);
        }
    }

    @Override // android.support.v4.widget.z
    public ColorStateList bm() {
        if (this.tU != null) {
            return this.tU.bm();
        }
        return null;
    }

    @Override // android.support.v4.widget.z
    public void c(PorterDuff.Mode mode) {
        if (this.tU != null) {
            this.tU.c(mode);
        }
    }

    @Override // android.support.v4.widget.z
    public PorterDuff.Mode bn() {
        if (this.tU != null) {
            return this.tU.bn();
        }
        return null;
    }

    @Override // android.widget.ImageView, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.tq != null) {
            this.tq.ec();
        }
        if (this.tU != null) {
            this.tU.eh();
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public boolean hasOverlappingRendering() {
        return this.tU.hasOverlappingRendering() && super.hasOverlappingRendering();
    }
}
