package android.support.v7.c.a;

import android.widget.PopupWindow;

/* JADX INFO: compiled from: MenuPopupHelper.java */
/* JADX INFO: loaded from: classes.dex */
class ah implements PopupWindow.OnDismissListener {
    final /* synthetic */ ag ro;

    ah(ag agVar) {
        this.ro = agVar;
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public void onDismiss() {
        this.ro.onDismiss();
    }
}
