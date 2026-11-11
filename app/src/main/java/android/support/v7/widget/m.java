package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.appcompat.R;

/* JADX INFO: compiled from: ActionMenuPresenter.java */
/* JADX INFO: loaded from: classes.dex */
class m extends AppCompatImageView implements r {
    final /* synthetic */ i sV;
    private final float[] sX;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public m(i iVar, Context context) {
        super(context, null, R.attr.actionOverflowButtonStyle);
        this.sV = iVar;
        this.sX = new float[2];
        setClickable(true);
        setFocusable(true);
        setVisibility(0);
        setEnabled(true);
        di.a(this, getContentDescription());
        setOnTouchListener(new n(this, this, iVar));
    }

    @Override // android.view.View
    public boolean performClick() {
        if (!super.performClick()) {
            playSoundEffect(0);
            this.sV.showOverflowMenu();
        }
        return true;
    }

    @Override // android.support.v7.widget.r
    public boolean cH() {
        return false;
    }

    @Override // android.support.v7.widget.r
    public boolean cI() {
        return false;
    }

    @Override // android.widget.ImageView
    protected boolean setFrame(int i, int i2, int i3, int i4) {
        boolean frame = super.setFrame(i, i2, i3, i4);
        Drawable drawable = getDrawable();
        Drawable background = getBackground();
        if (drawable != null && background != null) {
            int width = getWidth();
            int height = getHeight();
            int iMax = Math.max(width, height) / 2;
            int paddingLeft = (width + (getPaddingLeft() - getPaddingRight())) / 2;
            int paddingTop = (height + (getPaddingTop() - getPaddingBottom())) / 2;
            android.support.v4.a.a.a.a(background, paddingLeft - iMax, paddingTop - iMax, paddingLeft + iMax, paddingTop + iMax);
        }
        return frame;
    }
}
