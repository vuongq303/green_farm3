package android.support.v7.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/* JADX INFO: compiled from: CardViewJellybeanMr1.java */
/* JADX INFO: loaded from: classes.dex */
class bk implements co {
    final /* synthetic */ bj vi;

    bk(bj bjVar) {
        this.vi = bjVar;
    }

    @Override // android.support.v7.widget.co
    public void a(Canvas canvas, RectF rectF, float f, Paint paint) {
        canvas.drawRoundRect(rectF, f, f, paint);
    }
}
