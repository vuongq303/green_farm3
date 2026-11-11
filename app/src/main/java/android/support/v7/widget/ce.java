package android.support.v7.widget;

import android.view.MotionEvent;
import android.view.View;

/* JADX INFO: compiled from: ListPopupWindow.java */
/* JADX INFO: loaded from: classes.dex */
class ce implements View.OnTouchListener {
    final /* synthetic */ by wx;

    ce(by byVar) {
        this.wx = byVar;
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (action == 0 && this.wx.ww != null && this.wx.ww.isShowing() && x >= 0 && x < this.wx.ww.getWidth() && y >= 0 && y < this.wx.ww.getHeight()) {
            this.wx.mHandler.postDelayed(this.wx.wq, 250L);
            return false;
        }
        if (action == 1) {
            this.wx.mHandler.removeCallbacks(this.wx.wq);
            return false;
        }
        return false;
    }
}
