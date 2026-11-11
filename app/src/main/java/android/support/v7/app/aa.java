package android.support.v7.app;

import android.content.Context;
import android.view.Window;

/* JADX INFO: compiled from: AppCompatDelegateImplN.java */
/* JADX INFO: loaded from: classes.dex */
class aa extends ah {
    aa(Context context, Window window, v vVar) {
        super(context, window, vVar);
    }

    @Override // android.support.v7.app.ah, android.support.v7.app.ad, android.support.v7.app.x
    Window.Callback a(Window.Callback callback) {
        return new ab(this, callback);
    }
}
