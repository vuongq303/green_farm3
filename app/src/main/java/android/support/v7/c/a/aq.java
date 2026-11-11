package android.support.v7.c.a;

import android.view.View;
import android.view.ViewTreeObserver;

/* JADX INFO: compiled from: StandardMenuPopup.java */
/* JADX INFO: loaded from: classes.dex */
class aq implements ViewTreeObserver.OnGlobalLayoutListener {
    final /* synthetic */ ap rv;

    aq(ap apVar) {
        this.rv = apVar;
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public void onGlobalLayout() {
        if (this.rv.isShowing() && !this.rv.rr.isModal()) {
            View view = this.rv.pH;
            if (view == null || !view.isShown()) {
                this.rv.dismiss();
            } else {
                this.rv.rr.show();
            }
        }
    }
}
