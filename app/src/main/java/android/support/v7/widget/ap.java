package android.support.v7.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RatingBar;

/* JADX INFO: compiled from: AppCompatRatingBar.java */
/* JADX INFO: loaded from: classes.dex */
public class ap extends RatingBar {
    private final an uf;

    public ap(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.ratingBarStyle);
    }

    public ap(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.uf = new an(this);
        this.uf.a(attributeSet, i);
    }

    @Override // android.widget.RatingBar, android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected synchronized void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        Bitmap bitmapEj = this.uf.ej();
        if (bitmapEj != null) {
            setMeasuredDimension(View.resolveSizeAndState(bitmapEj.getWidth() * getNumStars(), i, 0), getMeasuredHeight());
        }
    }
}
