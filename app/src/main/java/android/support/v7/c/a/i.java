package android.support.v7.c.a;

import android.view.View;
import android.view.ViewTreeObserver;
import java.util.Iterator;

/* JADX INFO: compiled from: CascadingMenuPopup.java */
/* JADX INFO: loaded from: classes.dex */
class i implements ViewTreeObserver.OnGlobalLayoutListener {
    final /* synthetic */ h pS;

    i(h hVar) {
        this.pS = hVar;
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public void onGlobalLayout() {
        if (this.pS.isShowing() && this.pS.pA.size() > 0 && !this.pS.pA.get(0).pX.isModal()) {
            View view = this.pS.pH;
            if (view == null || !view.isShown()) {
                this.pS.dismiss();
                return;
            }
            Iterator<m> it = this.pS.pA.iterator();
            while (it.hasNext()) {
                it.next().pX.show();
            }
        }
    }
}
