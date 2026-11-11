package android.support.v7.widget;

import android.view.View;

/* JADX INFO: compiled from: ListPopupWindow.java */
/* JADX INFO: loaded from: classes.dex */
class bz implements Runnable {
    final /* synthetic */ by wx;

    bz(by byVar) {
        this.wx = byVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        View anchorView = this.wx.getAnchorView();
        if (anchorView != null && anchorView.getWindowToken() != null) {
            this.wx.show();
        }
    }
}
