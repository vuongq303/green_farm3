package android.support.v7.app;

import android.app.UiModeManager;
import android.content.Context;
import android.view.Window;

/* JADX INFO: compiled from: AppCompatDelegateImplV23.java */
/* JADX INFO: loaded from: classes.dex */
class ah extends ad {
    private final UiModeManager lq;

    ah(Context context, Window window, v vVar) {
        super(context, window, vVar);
        this.lq = (UiModeManager) context.getSystemService("uimode");
    }

    @Override // android.support.v7.app.ad, android.support.v7.app.x
    Window.Callback a(Window.Callback callback) {
        return new ai(this, callback);
    }

    @Override // android.support.v7.app.ad
    int Q(int i) {
        if (i == 0 && this.lq.getNightMode() == 0) {
            return -1;
        }
        return super.Q(i);
    }
}
