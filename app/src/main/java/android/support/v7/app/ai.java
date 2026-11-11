package android.support.v7.app;

import android.view.ActionMode;
import android.view.Window;

/* JADX INFO: compiled from: AppCompatDelegateImplV23.java */
/* JADX INFO: loaded from: classes.dex */
class ai extends ae {
    final /* synthetic */ ah lr;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ai(ah ahVar, Window.Callback callback) {
        super(ahVar, callback);
        this.lr = ahVar;
    }

    @Override // android.support.v7.c.n, android.view.Window.Callback
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int i) {
        if (this.lr.bG()) {
            switch (i) {
                case 0:
                    return a(callback);
            }
        }
        return super.onWindowStartingActionMode(callback, i);
    }

    @Override // android.support.v7.app.ae, android.support.v7.c.n, android.view.Window.Callback
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return null;
    }
}
