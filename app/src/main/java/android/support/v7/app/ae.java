package android.support.v7.app;

import android.view.ActionMode;
import android.view.Window;

/* JADX INFO: compiled from: AppCompatDelegateImplV14.java */
/* JADX INFO: loaded from: classes.dex */
class ae extends z {
    final /* synthetic */ ad lk;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ae(ad adVar, Window.Callback callback) {
        super(adVar, callback);
        this.lk = adVar;
    }

    @Override // android.support.v7.c.n, android.view.Window.Callback
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return this.lk.bG() ? a(callback) : super.onWindowStartingActionMode(callback);
    }

    final ActionMode a(ActionMode.Callback callback) {
        android.support.v7.c.h hVar = new android.support.v7.c.h(this.lk.mContext, callback);
        android.support.v7.c.b bVarD = this.lk.d(hVar);
        if (bVarD != null) {
            return hVar.d(bVarD);
        }
        return null;
    }
}
