package android.support.v7.widget;

import android.widget.AbsListView;

/* JADX INFO: compiled from: ListPopupWindow.java */
/* JADX INFO: loaded from: classes.dex */
class cd implements AbsListView.OnScrollListener {
    final /* synthetic */ by wx;

    cd(by byVar) {
        this.wx = byVar;
    }

    @Override // android.widget.AbsListView.OnScrollListener
    public void onScroll(AbsListView absListView, int i, int i2, int i3) {
    }

    @Override // android.widget.AbsListView.OnScrollListener
    public void onScrollStateChanged(AbsListView absListView, int i) {
        if (i == 1 && !this.wx.isInputMethodNotNeeded() && this.wx.ww.getContentView() != null) {
            this.wx.mHandler.removeCallbacks(this.wx.wq);
            this.wx.wq.run();
        }
    }
}
