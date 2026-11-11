package android.support.v7.widget;

/* JADX INFO: compiled from: ListPopupWindow.java */
/* JADX INFO: loaded from: classes.dex */
class cb implements Runnable {
    final /* synthetic */ by wx;

    cb(by byVar) {
        this.wx = byVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.wx.clearListSelection();
    }
}
