package android.support.v7.app;

import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;

/* JADX INFO: compiled from: AppCompatDelegateImplV9.java */
/* JADX INFO: loaded from: classes.dex */
class au extends android.support.v7.widget.bl {
    final /* synthetic */ aj lR;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public au(aj ajVar, Context context) {
        super(context);
        this.lR = ajVar;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return this.lR.dispatchKeyEvent(keyEvent) || super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() != 0 || !h((int) motionEvent.getX(), (int) motionEvent.getY())) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        this.lR.closePanel(0);
        return true;
    }

    @Override // android.view.View
    public void setBackgroundResource(int i) {
        setBackgroundDrawable(android.support.v7.a.a.b.a(getContext(), i));
    }

    private boolean h(int i, int i2) {
        return i < -5 || i2 < -5 || i > getWidth() + 5 || i2 > getHeight() + 5;
    }
}
