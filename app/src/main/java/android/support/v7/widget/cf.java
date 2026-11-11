package android.support.v7.widget;

/* JADX INFO: compiled from: ListPopupWindow.java */
/* JADX INFO: loaded from: classes.dex */
class cf implements Runnable {
    final /* synthetic */ by wx;

    cf(by byVar) {
        this.wx = byVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.wx.vY != null && android.support.v4.view.aa.isAttachedToWindow(this.wx.vY) && this.wx.vY.getCount() > this.wx.vY.getChildCount() && this.wx.vY.getChildCount() <= this.wx.wi) {
            this.wx.ww.setInputMethodMode(2);
            this.wx.show();
        }
    }
}
