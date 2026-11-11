package android.support.v7.widget;

import android.database.DataSetObserver;

/* JADX INFO: compiled from: ListPopupWindow.java */
/* JADX INFO: loaded from: classes.dex */
class cc extends DataSetObserver {
    final /* synthetic */ by wx;

    cc(by byVar) {
        this.wx = byVar;
    }

    @Override // android.database.DataSetObserver
    public void onChanged() {
        if (this.wx.isShowing()) {
            this.wx.show();
        }
    }

    @Override // android.database.DataSetObserver
    public void onInvalidated() {
        this.wx.dismiss();
    }
}
