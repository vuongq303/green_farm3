package android.support.v7.widget;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

/* JADX INFO: compiled from: ListViewCompat.java */
/* JADX INFO: loaded from: classes.dex */
class ch extends android.support.v7.b.a.a {
    private boolean il;

    public ch(Drawable drawable) {
        super(drawable);
        this.il = true;
    }

    void setEnabled(boolean z) {
        this.il = z;
    }

    @Override // android.support.v7.b.a.a, android.graphics.drawable.Drawable
    public boolean setState(int[] iArr) {
        if (this.il) {
            return super.setState(iArr);
        }
        return false;
    }

    @Override // android.support.v7.b.a.a, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (this.il) {
            super.draw(canvas);
        }
    }

    @Override // android.support.v7.b.a.a, android.graphics.drawable.Drawable
    public void setHotspot(float f, float f2) {
        if (this.il) {
            super.setHotspot(f, f2);
        }
    }

    @Override // android.support.v7.b.a.a, android.graphics.drawable.Drawable
    public void setHotspotBounds(int i, int i2, int i3, int i4) {
        if (this.il) {
            super.setHotspotBounds(i, i2, i3, i4);
        }
    }

    @Override // android.support.v7.b.a.a, android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        if (this.il) {
            return super.setVisible(z, z2);
        }
        return false;
    }
}
