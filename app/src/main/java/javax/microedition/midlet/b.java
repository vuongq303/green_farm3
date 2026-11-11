package javax.microedition.midlet;

import android.view.ViewTreeObserver;

/* JADX INFO: compiled from: App.java */
/* JADX INFO: loaded from: classes.dex */
class b implements ViewTreeObserver.OnWindowFocusChangeListener {
    final /* synthetic */ App cnt;

    b(App app) {
        this.cnt = app;
    }

    @Override // android.view.ViewTreeObserver.OnWindowFocusChangeListener
    public void onWindowFocusChanged(boolean z) {
        this.cnt.cp(z);
    }
}
